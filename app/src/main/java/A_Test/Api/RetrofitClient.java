package A_Test.Api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {


    private static final String BASE_IMAGE_URL = "http://throtel.com/";

    public static final String BASE_URL_Test = "http://throtel.com/test-student-api/";
    public static final String TestSignUp = "http://throtel.com/test-student-api/signup";
//    public static final String BASE_BANNER_IMAGE_URL = BASE_IMAGE_URL + "uploads/test-banner/";
    public static final String BASE_BANNER_IMAGE_URL = BASE_IMAGE_URL + "test-student-api/get-banner-list-view/";
    public static final String Testdistric = "http://throtel.com/test-student-api/state-wise-district-list-view";
    public static final String TestTahsil = "http://throtel.com/test-student-api/district-wise-tahsil-list-view";


    private static Retrofit retrofit;

    public static ApiTest getRetrofitClient() {

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
                    .baseUrl(BASE_URL_Test)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }

        return retrofit.create(ApiTest.class);
    }
}
