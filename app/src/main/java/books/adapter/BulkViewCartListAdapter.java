package books.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.throtel.grocery.R;
import com.throtel.grocery.utils.LocalData;

import java.util.ArrayList;
import java.util.List;

import books.api.RetrofitClient;
import books.models.CartProductList;
import books.models.DataResponse;
import books.views.MyProgress;
import me.himanshusoni.quantityview.QuantityView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BulkViewCartListAdapter extends RecyclerView.Adapter<BulkViewCartListAdapter.ItemViewHolder> {

    List<String> lables;
    private Context context;
    private ArrayList<CartProductList> rowItems;
    private int selectedWeight = 0;
    private int selectedWeightNew = 0;
    private Double TotalPrice = 0.0;
    private StatusUpdateListener statusUpdateListener;

    public BulkViewCartListAdapter(Context context, ArrayList<CartProductList> rowItems, StatusUpdateListener statusUpdateListener) {
        this.context = context;
        this.rowItems = rowItems;
        this.statusUpdateListener = statusUpdateListener;

    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_view_cart_list, parent, false);
        return new BulkViewCartListAdapter.ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {

        final CartProductList product = rowItems.get(position);

        holder.tvName.setText(product.getProductName());

        holder.quantityView.setQuantity(product.getSelectedQuantity());


        //=====Select Product Weight======


       // holder.tvPrice.setText(" ₹ " + product.getProductPricePerGramOrMl() * 1000);
        holder.tvSellingPrice.setText(Html.fromHtml("<strike> ₹ " + (product.getProductSellingPrice() * 1000) + "</strike>"));
        holder.tvDiscount.setText("Save:₹ " + product.getDiscountAmount());
        holder.tvWeight.setText("");

        holder.tvSellingPrice.setVisibility(View.GONE);
        holder.tvDiscount.setVisibility(View.GONE);
        holder.view1.setVisibility(View.GONE);
        holder.view2.setVisibility(View.GONE);

        String url = RetrofitClient.BASE_PRODUCT_IMAGE_URL + product.getProductImage1();

        Picasso.with(context)
                .load(url) //Load the image
                .fit()
                .error(R.drawable.no_preview)
                .into(holder.ivImage);


        holder.quantityView.setOnQuantityChangeListener(new QuantityView.OnQuantityChangeListener() {
            @Override
            public void onQuantityChanged(final int oldQuantity, int newQuantity, boolean programmatically) {


                if (newQuantity == 0 && oldQuantity > 0) {
                    //Delete Product When Qty is 0
                    new AlertDialog.Builder(context)
                            .setMessage("Minimum Quantity should be 1 in cart")
                            .setCancelable(false)
                            .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  DeleteCartProduct(product, position);
                                    dialog.dismiss();
                                    holder.quantityView.setQuantity(oldQuantity);

                                }
                            })

                            .show();


                } else {
                    //Update Quntity of Product
                    if (newQuantity <= product.getAvailableQuantity()) {

                        if (newQuantity >= product.getAvailableQuantity()) {
                            UpdateCartProduct(product, newQuantity, position);
                            holder.quantityView.setQuantity(newQuantity);
                        } else {
                            holder.quantityView.setQuantity(oldQuantity);
                          //  Toast.makeText(context, "Please select product quantity minimum " + product.getMinQuantityInKgOrLtr(), Toast.LENGTH_SHORT).show();
                        }


                    } else {
                        holder.quantityView.setQuantity(oldQuantity);
                        Toast.makeText(context, "No More product stock available", Toast.LENGTH_SHORT).show();
                    }


                }


            }

            @Override
            public void onLimitReached() {

            }
        });

        holder.ivImgageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AlertDialog.Builder(context)
                        .setMessage("Are you sure you want to Delete Product?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                DeleteCartProduct(product, position);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                        .show();


            }
        });


    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    //Add Product To Cart
    private void UpdateCartProduct(final CartProductList product, final int Qty, final int rowIndex) {
        MyProgress.start(context);
        LocalData localData = LocalData.getInstance(context);
        Call<DataResponse> call = RetrofitClient.getRetrofitClient().
                updateToCartProduct(localData.getCustomerId(), product.getCartId().toString(), String.valueOf(Qty));
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                MyProgress.stop();
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    product.setSelectedQuantity(Qty);
                    rowItems.set(rowIndex, product);
                    statusUpdateListener.onStatusUpdate(rowIndex, product);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d("Sub Product List", t.getMessage());
                Toast.makeText(context, context.getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Delete Product from Cart
    private void DeleteCartProduct(final CartProductList product, final int rowIndex) {
        MyProgress.start(context);
        LocalData localData = LocalData.getInstance(context);
        Call<DataResponse> call = RetrofitClient.getRetrofitClient().
                deleteCartProduct(String.valueOf(product.getCartId()), localData.getCustomerId());
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                MyProgress.stop();
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    rowItems.remove(rowIndex);
                    statusUpdateListener.onStatusUpdate(rowIndex, product);
                    notifyDataSetChanged();
                } else {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<DataResponse> call, Throwable t) {
                MyProgress.stop();
                Log.d("Sub Product List", t.getMessage());
                Toast.makeText(context, context.getString(R.string.error_general), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public interface StatusUpdateListener {
        void onStatusUpdate(int index, CartProductList product);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvPrice, tvSellingPrice, tvDiscount, tvWeight;
        ImageView ivImage, ivImgageDelete;
        QuantityView quantityView;
        View view1, view2;


        public ItemViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvPrice = itemView.findViewById(R.id.tv_net_price);
            tvSellingPrice = itemView.findViewById(R.id.tv_sell_price);
            tvDiscount = itemView.findViewById(R.id.tv_save);
            tvWeight = itemView.findViewById(R.id.tv_item_weight);
            ivImage = itemView.findViewById(R.id.img);

            ivImgageDelete = itemView.findViewById(R.id.img_delete);

            quantityView = itemView.findViewById(R.id.quantityView);

            view1 = itemView.findViewById(R.id.view1);
            view2 = itemView.findViewById(R.id.view2);

        }
    }

}
