package books.api;

import books.api.Api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static String BASE_GROUP_IMAGE_URL = "http://throtel.com/uploads/book-main-group/";
    private static final String BASE_URL = "http://throtel.com/book-customer-api/";
    private static final String BASE_IMAGE_URL = "http://throtel.com/";

    // public static final String BASE_CUSTOMER_IMAGE_URL = BASE_IMAGE_URL + "uploads/customer/";

    public static final String BASE_GROUP_WISE_IMAGE_URL = BASE_IMAGE_URL + "uploads/book-main-category/";
    public static final String BASE_PRODUCT_IMAGE_URL = BASE_IMAGE_URL + "uploads/book-product/";
    //public static final String BASE_MAIN_GROUP_IMAGE_URL = BASE_IMAGE_URL + "uploads/grocery-main-group/";
    //public static final String BASE_RETURN_IMAGE_URL = BASE_IMAGE_URL + "uploads/return-product/";
   // public static final String BASE_CATEGORY_IMAGE_URL = BASE_IMAGE_URL + "uploads/sub-category/";
    public static final String BASE_BANNER_IMAGE_URL = BASE_IMAGE_URL + "uploads/book-banner/";


    public static final String BASE_SUBSCRIPTION_PACK_IMAGE_URL = BASE_IMAGE_URL + "uploads/subscription-pack/";

    private static Retrofit retrofit;

    public static books.api.Api getRetrofitClient() {

        if (retrofit == null) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .addInterceptor(logging)
                    .build();


            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(Api.class);
    }
}
