package A_Test.Api;

import A_Test.model.BannerListDataResponse;
import A_Test.model.BatchDataResponse;
import A_Test.model.CityDataResponse;
import A_Test.model.LoginDataResponse;
import A_Test.model.PincodeListDataResponse;
import A_Test.model.StateListDataResponse;
import A_Test.model.UpdateUserTestResponse;
import books.models.DataResponse;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface ApiTest {
    String BaseURL = "http://throtel.com/book-customer-api/";


//    @FormUrlEncoded
//    @POST("signup")
//    Call<DataResponse> getSignUpTest(
//            @Field("name") String name,
//            @Field("email") String email,
//            @Field("phone") String phone,
//            @Field("schoolName") String schoolName,
//            @Field("className") String className,
//            @Field("password") String password,
//            @Field("password2") String password2,
//            @Field("stateId") String stateId,
//            @Field("districtId") String districtId,
//            @Field("tahsilId") String tahsilId
//    );
    @FormUrlEncoded
    @POST("signup")
    Call<A_Test.model.DataResponse> getSignUpTest(
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("schoolName") String schoolName,
            @Field("className") String className,
            @Field("password") String password,
            @Field("password2") String password2,
            @Field("stateId") String stateId,
            @Field("districtId") String districtId,
            @Field("tahsilId") String tahsilId,
            @Field("batchId") String batchId
    );

    @POST("login")
    Call<LoginDataResponse> login(@Query("phone") String phone, @Query("password") String password
            , @Query("token") String token
    );

    @GET("get-test-batch-list-view")
    Call<BatchDataResponse> getBatchList();

    @GET("state-list-view")
    Call<StateListDataResponse> getStateList();


    @FormUrlEncoded
    @POST("state-wise-district-list-view")
    Call<CityDataResponse> getDistrictList(
            @Field("stateId") String stateId
    );


    @FormUrlEncoded
    @POST("district-wise-tahsil-list-view")
    Call<PincodeListDataResponse> getTahsilList(
            @Field("districtId") String districtId
    );

    @FormUrlEncoded
    @POST("verify-otp")
    Call<DataResponse> verifyOTP(
            @Field("phone") String phone,
            @Field("otp") String otp
    );
    @FormUrlEncoded
    @POST("forgot-password-generate-otp")
    Call<A_Test.model.DataResponse> forgotGenerateOTP(
            @Field("phone") String phone
    );
    @FormUrlEncoded
    @POST("forgot-password-verify-otp")
    Call<A_Test.model.DataResponse> verifyForgotOTP(
            @Field("phone") String phone,
            @Field("otp") String otp
    );
    @FormUrlEncoded
    @POST("forgot-password-change-password")
    Call<A_Test.model.DataResponse> getChangePassword(
            @Field("phone") String phone,
            @Field("password1") String password1,
            @Field("password2") String password2
    );

//    @FormUrlEncoded
//    @POST("update-test-user-profile-view")
//    Call<UpdateUserTestResponse> getUpdateProfileTest(
//            @Field("id") Integer id,
//            @Field("name") String name,
//            @Field("email") String email,
//            @Field("phone") String phone,
//            @Field("profileImage") String profileImage,
//            @Field("stateId") Integer stateId,
//            @Field("districtId") Integer districtId,
//            @Field("tahsilId") Integer tahsilId,
//            @Field("schoolName") String schoolName,
//            @Field("className") String className
//    );
@Multipart
    @POST("update-test-user-profile-view")
    Call<UpdateUserTestResponse> getUpdateProfileTest(
    @Part("id") RequestBody id,
    @Part("name") RequestBody name,
    @Part("email") RequestBody email,
    @Part("phone") RequestBody phone,
    @Part MultipartBody.Part profileImage,
    @Part("stateId") RequestBody stateId,
    @Part("districtId") RequestBody districtId,
    @Part("tahsilId") RequestBody tahsilId,
    @Part("schoolName") RequestBody schoolName,
    @Part("className") RequestBody className
    );


    @GET("get-banner-list-view")
    Call<BannerListDataResponse> getBannerData(
//            @Field("customerId") String customerId
    );
}