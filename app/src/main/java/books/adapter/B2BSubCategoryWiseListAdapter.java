package books.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.throtel.grocery.R;
import com.throtel.grocery.utils.LocalData;

import books.activity.B2BProductDetailsActivity;
import books.api.RetrofitClient;
import books.models.DataResponse;
import books.models.ProductList;
import books.models.SubProductList;
import books.views.MyProgress;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class B2BSubCategoryWiseListAdapter extends RecyclerView.Adapter<B2BSubCategoryWiseListAdapter.ItemViewHolder> {

    List<String> lables;
    private Context context;
    private ArrayList<ProductList> rowItems;
    private StatusUpdateListener statusUpdateListener;
    private int selectedWeight = 0;

    public B2BSubCategoryWiseListAdapter(Context context, ArrayList<ProductList> rowItems, StatusUpdateListener statusUpdateListener) {
        this.context = context;
        this.rowItems = rowItems;
        this.statusUpdateListener = statusUpdateListener;
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_b2b_product_list_by_category, parent, false);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {

        final ProductList product = rowItems.get(position);

        holder.tvName.setText(product.getProductName());
        holder.tvAvailableStock.setVisibility(View.VISIBLE);

//
//        if (product.getSubProductList().size() > 0) {
//            SubProductList subProduct = new SubProductList();
//            subProduct = product.getSubProductList().get(0);
//
//            if (subProduct.getProductPricePerGramOrMl() != null) {
//                holder.tvPrice.setText(" ₹ " + subProduct.getProductPricePerGramOrMl() * 1000);
//            } else
//                holder.tvPrice.setText(" ₹ " + 0.0);
//
//            if (subProduct.getProductSellingPrice() != null) {
//                holder.tvSellingPrice.setText(Html.fromHtml("<strike> ₹ " + subProduct.getProductSellingPrice() + "</strike>"));
//            } else
//                holder.tvSellingPrice.setText(Html.fromHtml("<strike> ₹ " + 0.0 + "</strike>"));
//
//            if (subProduct.getDiscountAmount() != null) {
//                holder.tvDiscount.setText("Save : ₹" + subProduct.getDiscountAmount());
//            } else
//                holder.tvDiscount.setText("Save : ₹" + 0.0);
//            if (subProduct.getMinQuantityInKgOrLtr() != null) {
//                holder.tvMinQty.setText("Minimum Quantity : " + subProduct.getMinQuantityInKgOrLtr());
//            } else
//                holder.tvMinQty.setText("Minimum Quantity : " + 0.0);
//            holder.tvWeight.setText("1Kg / 1Ltr");
//
//
//            if (subProduct.getAvailableQuantityInKgOrLtr() != null) {
//                holder.tvAvailableStock.setText("Available Stock : " + subProduct.getAvailableQuantityInKgOrLtr());
//            } else
//                holder.tvAvailableStock.setText("Available Stock : " + 0.0);
//            String url = RetrofitClient.BASE_PRODUCT_IMAGE_URL + subProduct.getProductImage1();
//
//            Picasso.with(context)
//                    .load(url) //Load the image
//                    .fit()
//                    .error(R.drawable.no_preview)
//                    .into(holder.ivImage);
//
//
//        }


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //onItemClickListener.onItemClick(product);

                Intent intent = new Intent(context, B2BProductDetailsActivity.class);
                intent.putExtra("ProductDetails", product);
                intent.putExtra("SubproductId", selectedWeight);
                context.startActivity(intent);


            }
        });

        //ADD TO CART
        holder.rlAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Add Product to CArt
//                try {
//                    if (product >= 1) {
//                        //Add default selected Qty product
//                        if (product.getSubProductList().get(0).getAvailableQuantityInKgOrLtr() != null) {
//                            if (product.getSubProductList().get(0).getAvailableQuantityInKgOrLtr() > 0.0) {
//                                //Add default selected Qty product
//                                if (product.getSubProductList().get(0).getMinQuantityInKgOrLtr() != null) {
//                                    Double minqty = Double.valueOf(product.getSubProductList().get(0).getMinQuantityInKgOrLtr().toString());
//
//                                    String newValue = String.format("%.0f", minqty);
//                                    AddToCartProduct(product.getSubProductList().get(0), newValue, position);
//                                } else
//                                    AddToCartProduct(product.getSubProductList().get(0), "1", position);
//
//                            } else
//                                Toast.makeText(context, "Out Of Stock Product", Toast.LENGTH_LONG).show();
//                        } else
//                            Toast.makeText(context, "Out Of Stock Product", Toast.LENGTH_LONG).show();
//
//                    }
//                } catch (NullPointerException e) {
//                    e.printStackTrace();
//                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return rowItems.size();
    }

    //Add Product To Cart
    private void AddToCartProduct(final SubProductList product, String Qty, final int rowIndex) {
        MyProgress.start(context);

        LocalData localData = LocalData.getInstance(context);
        Log.d("BTAG", "PARAM IS...." + product.getProductId() + localData.getCustomerId() + Qty + "Bulk");
        Call<DataResponse> call = RetrofitClient.getRetrofitClient().
                addToCartProduct(product.getProductId().toString(), localData.getCustomerId(), Qty);
        call.enqueue(new Callback<DataResponse>() {
            @Override
            public void onResponse(Call<DataResponse> call, Response<DataResponse> response) {
                MyProgress.stop();
                if (response.body() != null) {
                    if (response.body().getStatus().equalsIgnoreCase("true")) {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        // rowItems.remove(product);
                        statusUpdateListener.onStatusUpdate(rowIndex, product);
                        notifyDataSetChanged();
                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else
                    Toast.makeText(context, context.getString(R.string.error_general), Toast.LENGTH_SHORT).show();

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
        void onStatusUpdate(int index, SubProductList product);
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {

        TextView tvName, tvPrice, tvSellingPrice, tvDiscount, tvWeight, tvMinQty;
        TextView tvAvailableStock;
        ImageView ivImage;
        LinearLayout rlAddToCart;


        public ItemViewHolder(View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tv_item_name);
            tvPrice = itemView.findViewById(R.id.tv_net_price);
            tvSellingPrice = itemView.findViewById(R.id.tv_sell_price);
            tvDiscount = itemView.findViewById(R.id.tv_save);
            tvWeight = itemView.findViewById(R.id.tv_item_weight);
            ivImage = itemView.findViewById(R.id.img);

            tvAvailableStock = itemView.findViewById(R.id.tv_available_stock);
            tvMinQty = itemView.findViewById(R.id.tv_min_qty);

            rlAddToCart = itemView.findViewById(R.id.rl_add_cart);

        }
    }


}
