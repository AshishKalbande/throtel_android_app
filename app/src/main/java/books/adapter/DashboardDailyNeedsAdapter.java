package books.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.throtel.grocery.R;
import com.throtel.grocery.utils.LocalData;

import books.activity.HomeBookActivity;
import books.activity.ProductDetailsActivity;
import books.api.RetrofitClient;
import books.models.DataResponse;
import books.models.ProductList;
import books.views.MyProgress;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardDailyNeedsAdapter extends RecyclerView.Adapter<DashboardDailyNeedsAdapter.ItemViewHolder> {

    List<String> lables;
    private Context context;
    private ArrayList<ProductList> rowItems;
    private OnItemClickListener onItemClickListener;
    private int selectedWeight = 0;
    private int selectedWeightNew = 0;

    public DashboardDailyNeedsAdapter(Context context, ArrayList<ProductList> rowItems, OnItemClickListener onItemClickListener) {
        this.context = context;
        this.rowItems = rowItems;
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_dailyneeds_list, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {

        final ProductList product = rowItems.get(position);

        holder.tvName.setText(product.getProductName());

        //=====Select Product Weight======
        holder.tvWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (product.getProductUnit().length() > 1) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Select Product Weight");
                    lables = new ArrayList<>();
                    //Iterator<ProductList> iterator = product.getProductUnit().iterator();
//                    while (iterator.hasNext()) {
                    lables.add(product.getProductUnit());
//                    }
                    ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(context,
                            R.layout.layout_spinner_item, lables);
                    builder.setAdapter(dataAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(context, "You have selected " + lables.get(which), Toast.LENGTH_LONG).show();
                            selectedWeightNew = which;

                            holder.tvPrice.setText(product.getProductSellingPrice().toString());
                            holder.tvSellingPrice.setText(Html.fromHtml("<strike> ₹ " + product.getProductNetPrice().toString() + "</strike>"));
                            holder.tvDiscount.setText("Save : ₹ " + product.getDiscountPercentage().toString()+"%");
                            holder.tvWeight.setText(product.getProductUnit());

                            String url = RetrofitClient.BASE_PRODUCT_IMAGE_URL + product.getProductImage1();

                            Picasso.with(context)
                                    .load(url) //Load the image
                                    .fit()
                                    .error(R.drawable.no_preview)
                                    .into(holder.ivImage);
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
//
                }
            }
        });

        if (product.getProductUnit().length() > 0) {
            //SubProductList subProduct = new SubProductList();
           // ProductList = product.getSubProductList().get(0);

            holder.tvPrice.setText(" ₹ " + product.getProductSellingPrice().toString());
            holder.tvSellingPrice.setText(Html.fromHtml("<strike> ₹ " + product.getProductNetPrice().toString() + "</strike>"));
            holder.tvDiscount.setText("Save : ₹ " + product.getDiscountPercentage().toString()+"%");
            holder.tvWeight.setText(product.getProductUnit());

            String url = RetrofitClient.BASE_PRODUCT_IMAGE_URL + product.getProductImage1();

            Picasso.with(context)
                    .load(url) //Load the image
                    .fit()
                    .error(R.drawable.no_preview)
                    .into(holder.ivImage);
        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onItemClickListener.onItemClick(product);
                // SubProductList assignWeight=product.getSubProductList().get(position);
                if (product.getProductUnit().length() > 0) {
                    //selectedWeight = lables.indexOf(lables.get(position));
                    selectedWeight = selectedWeightNew;
                } else {
                    selectedWeight = 0;
                }


                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("ProductDetails", product);
                //intent.putExtra("SubproductId", selectedWeight);

                context.startActivity(intent);


            }
        });

        holder.tvAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // if (product.size() == 1) {

                  //  if (product.getSubProductList().get(0).getAvailableQuantity() > 0) {
                        //Add default selected Qty product
                        AddToCartProduct(product, 1);
                   // } else
                    //    Toast.makeText(context, "Out Of Stock Product", Toast.LENGTH_LONG).show();
               // }
//                else
//                    {
//                    //Add Selected Qty from popup and add to cart
//                    if (product.getSubProductList().get(selectedWeightNew).getAvailableQuantity() > 0) {
//                        //Add default selected Qty product
//                        AddToCartProduct(product.getSubProductList().get(selectedWeightNew), 1);
//                    } else
//                        Toast.makeText(context, "Out Of Stock Product", Toast.LENGTH_LONG).show();
//
//                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    //Add Product To Cart
    private void AddToCartProduct(final ProductList product, int Qty) {
        MyProgress.start(context);
        LocalData localData = LocalData.getInstance(context);
        Call<DataResponse> call = RetrofitClient.getRetrofitClient().
                addToCartProduct(product.getProductId().toString(), localData.getCustomerId(), String.valueOf(Qty));
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                MyProgress.stop();
                if (response.body() != null && response.body().getStatus().equalsIgnoreCase("true")) {
                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    // rowItems.remove(product);
                    ((HomeBookActivity) context).resetGraph(context);
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

    public interface OnItemClickListener {
        void onItemClick(ProductList product);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvPrice, tvSellingPrice, tvDiscount, tvWeight;
        ImageView ivImage;
        TextView tvAddToCart;


        public ItemViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvPrice = itemView.findViewById(R.id.tv_price);
            tvSellingPrice = itemView.findViewById(R.id.tv_sell_price);
            tvDiscount = itemView.findViewById(R.id.tv_save);
            tvWeight = itemView.findViewById(R.id.tv_item_weight);
            ivImage = itemView.findViewById(R.id.img);

            tvAddToCart = itemView.findViewById(R.id.tv_add_to_cart);

        }
    }
}
