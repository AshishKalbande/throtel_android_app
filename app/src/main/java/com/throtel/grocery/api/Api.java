package com.throtel.grocery.api;

import com.throtel.grocery.models.AddressListDataResponse;
import com.throtel.grocery.models.CMSProductListDataResponse;
import com.throtel.grocery.models.CategoryDataResponse;
import com.throtel.grocery.models.CityDataResponse;
import com.throtel.grocery.models.CoupneCodeDataResponse;
import com.throtel.grocery.models.CouponCodeDataResponse;
import com.throtel.grocery.models.CustomerOrderListDataResponse;
import com.throtel.grocery.models.DailySubscriptionListDataResponse;
import com.throtel.grocery.models.DashbordDataResponse;
import com.throtel.grocery.models.DataResponse;
import com.throtel.grocery.models.DeliveryChargeDataResponse;
import com.throtel.grocery.models.DeliveryDatesResponse;
import com.throtel.grocery.models.GroupListDataResponse;
import com.throtel.grocery.models.GroupWiseDataResponse;
import com.throtel.grocery.models.LoginDataResponse;
import com.throtel.grocery.models.MyMonthlyDetailsDataResponse;
import com.throtel.grocery.models.MySubscriptionDataResponse;
import com.throtel.grocery.models.MySubscriptionMonthlyDataResponse;
import com.throtel.grocery.models.OffersListResponse;
import com.throtel.grocery.models.OffersProductListResponse;
import com.throtel.grocery.models.OrderDataResponse;
import com.throtel.grocery.models.OrderDetailsDataResponse;
import com.throtel.grocery.models.OrderListDataResponse;
import com.throtel.grocery.models.OrderPaymentIdResponse;
import com.throtel.grocery.models.PackDetailsListDataResponse;
import com.throtel.grocery.models.PackListDataResponse;
import com.throtel.grocery.models.PincodeListDataResponse;
import com.throtel.grocery.models.ReasonListDataResponse;
import com.throtel.grocery.models.ReturnDetailsDataResponse;
import com.throtel.grocery.models.ReturnProductListDataResponse;
import com.throtel.grocery.models.SearchListDataResponse;
import com.throtel.grocery.models.SlotListDataResponse;
import com.throtel.grocery.models.StateListDataResponse;
import com.throtel.grocery.models.SubCategoryDataResponse;
import com.throtel.grocery.models.SubCategoryWiseProductDataResponse;
import com.throtel.grocery.models.SubscribeOrderPlaceDataResponse;
import com.throtel.grocery.models.SubscriptionOrderDetailDataResponse;
import com.throtel.grocery.models.TransactionDataResponse;
import com.throtel.grocery.models.VersionResponse;
import com.throtel.grocery.models.ViewCartDataResponse;
import com.throtel.grocery.models.WalletDetailDataResponse;
import com.throtel.grocery.models.WalletSummeryDataResponse;
import com.throtel.grocery.models.notifications.NotificationCountResponse;
import com.throtel.grocery.models.notifications.NotificationListResponse;

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

public interface  Api {
    String BaseURL = "http://throtel.com/grocery-customer-api/";



    @FormUrlEncoded
    @POST("signup")
    Call<DataResponse> getSignUp(
            @Field("name") String name,
            @Field("email") String email,
            @Field("phone") String phone,
            @Field("password") String password,
            @Field("password2") String password2,
            @Field("stateId") String stateId,
            @Field("districtId") String districtId,
            @Field("tahsilId") String tahsilId
    );


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
    Call<DataResponse> forgotGenerateOTP(
            @Field("phone") String phone
    );
    @FormUrlEncoded
    @POST("forgot-password-verify-otp")
    Call<DataResponse> verifyForgotOTP(
            @Field("phone") String phone,
            @Field("otp") String otp
    );
    @FormUrlEncoded
    @POST("forgot-password-change-password")
    Call<DataResponse> getChangePassword(
            @Field("phone") String phone,
            @Field("password1") String password1,
            @Field("password2") String password2
    );
    @FormUrlEncoded
    @POST("grocery-dashboard")
    Call<DashbordDataResponse> getDashBoardData(
            @Field("customerId") String customerId
    );

    @FormUrlEncoded
    @POST("grocery-main-group-list-view")
    Call<GroupListDataResponse> getGroupListData(
            @Field("customerId") String customerId
    );

    @FormUrlEncoded
    @POST("grocery-main-group-wise-category-list-view")
    Call<GroupWiseDataResponse> getGroupWiseListData(
            @Field("customerId") String customerId,
            @Field("mainGroupId") String mainGroupId
    );

    @FormUrlEncoded
    @POST("grocery-category-wise-sub-category-list-view")
    Call<SubCategoryDataResponse> getSubCategoryListData(
            @Field("customerId") String customerId,
            @Field("mainCategoryId") String mainCategoryId,
            @Field("pageNumber") String pageNumber
    );

    @FormUrlEncoded
    @POST("grocery-sub-category-wise-product-list-view")
    Call<SubCategoryWiseProductDataResponse> getSubCategoryWiseListData(
            @Query("limit") int limit,
            @Field("customerId") String customerId,
            @Field("subCategoryId") String subCategoryId,
            @Field("pageNumber") String pageNumber,
            @Field("mainCategoryId") String mainCategoryId
          //  @Field("productType") String productType
    );

    @FormUrlEncoded
    @POST("add-grocery-product-to-cart-view")
    Call<DataResponse> addToCartProduct(
            @Field("productId") String productId,
            @Field("customerId") String customerId,
            @Field("quantity") String quantity
           // @Field("productType") String productType
    );

    @FormUrlEncoded
    @POST("search-grocery-product-view")
    Call<SearchListDataResponse> getSearchProductListData(
            @Field("customerId") String customerId,
            @Field("searchData") String searchData,
            @Field("pageNumber") String pageNumber
            //@Field("productType") String productType
    );
    @FormUrlEncoded
    @POST("grocery-customer-cart-list-view")
    Call<ViewCartDataResponse> getViewCartList(
            @Field("customerId") String customerId
            //@Field("productType") String productType
    );

    @FormUrlEncoded
    @POST("change-grocery-address-status-view")
    Call<DataResponse> getStatusChange(
            @Field("customerId") String customerId,
            @Field("addressId") String addressId,
            @Field("status") String status
    );
    @FormUrlEncoded
    @POST("grocery-customer-address-list-view")
    Call<AddressListDataResponse> getAddressList(
            @Field("customerId") String customerId
    );

    @FormUrlEncoded
    @POST("grocery-customer-order-product-list-view")
    Call<CustomerOrderListDataResponse> getCustomerOrdersListData(
            @Field("customerId") String customerId,
            @Field("orderId") String orderId
    );
    @FormUrlEncoded
    @POST("change-grocery-customer-notification-status-unread-to-read")
    Call<DataResponse> readNotifications(
            @Field("customerId") String customerId,
            @Field("notificationFor") String notificationFor
    );
    @FormUrlEncoded
    @POST("apply-grocery-coupon")
    Call<DataResponse> getCoupneId(
            @Field("couponId") String couponId,
            @Field("customerId") String customerId
    );
    @FormUrlEncoded
    @POST("check-grocery-coupon-availability-and-get-price")
    Call<CouponCodeDataResponse> getCouponApplyData(
            @Field("couponName") String couponName,
            @Field("customerId") String customerId
    );
    @FormUrlEncoded
    @POST("place-grocery-order")
    Call<OrderDataResponse> getOrderDetails(
            @Field("customerId") String customerId,
            @Field("addressId") String addressId,
            @Field("orderDeliverDate") String orderDeliverDate,
            @Field("orderDeliverStartTime") String orderDeliverStartTime,
            @Field("orderDeliverEndTime") String orderDeliverEndTime,
            @Field("netPrice") String netPrice,
            @Field("coupanAmount") String coupanAmount,
            @Field("totalPriceAfterExcludingCoupanAmount") String totalPriceAfterExcludingCoupanAmount,
           // @Field("deliveryAmount") String deliveryAmount,
            //@Field("totalPriceAfterIncludingDeliveryAmount") String totalPriceAfterIncludingDeliveryAmount,
            @Field("serviceCharge") String serviceCharge,
            @Field("totalPriceAfterIncludingServiceCharge") String totalPriceAfterIncludingServiceCharge
            //@Field("orderType") String orderType
    );




    @FormUrlEncoded
    @POST("grocery-customer-order-list-view")
    Call<OrderListDataResponse> getOrdersListData(
            @Field("customerId") String customerId
    );






    @FormUrlEncoded
    @POST("grocery-customer-order-detail-view")
    Call<OrderDetailsDataResponse> getOrderDetails(
            @Field("customerId") String customerId,
            @Field("orderId") String orderId
    );

    @FormUrlEncoded
    @POST("get-grocery-customer-notification-list")
    Call<NotificationListResponse> getNotificationList(
            @Field("customerId") String customerId,
            @Field("notificationFor") String notificationFor
    );
    @FormUrlEncoded
    @POST("get-grocery-customer-unread-notification-count")
    Call<NotificationCountResponse> getNotificationCount(
            @Field("customerId") String customerId,
            @Field("notificationFor") String notificationFor
    );

    @FormUrlEncoded
    @POST("grocery-add-address-view")
    Call<DataResponse> addNewAddress(
            @Field("customerId") String customerId,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("flatOrHouseNumber") String flatOrHouseNumber,
            @Field("streetOrSocietyName") String streetOrSocietyName,
            @Field("landmark") String landmark,
            @Field("addressType") String addressType

    );

    @FormUrlEncoded
    @POST("update-grocery-address-view")
    Call<DataResponse> getUpdateAddress(
            @Field("id") String id,
            @Field("customerId") String customerId,
            @Field("name") String name,
            @Field("phone") String phone,
            @Field("flatOrHouseNumber") String flatOrHouseNumber,
            @Field("streetOrSocietyName") String streetOrSocietyName,
            @Field("landmark") String landmark,
            @Field("addressType") String addressType

    );



    @FormUrlEncoded
    @POST("grocery-delivery-slot-list-view")
    Call<SlotListDataResponse> getSlotList(
            @Field("customerId") String customerId
    );

//    @FormUrlEncoded
//    @POST("check-delivery-charge")
//    Call<DeliveryChargeDataResponse> getDeliveryCharge(
//            @Field("pincodeNumber") String pincodeNumber,
//            @Field("customerId") String customerId
//    );


    @FormUrlEncoded
    @POST("get-grocery-delivery-date-list-view")
    Call<DeliveryDatesResponse> getDeliveryDatesList(
            @Field("customerId") String customerId
    );

    @GET("transaction-charge-detail-view")
    Call<TransactionDataResponse> getTransactionData();

    @FormUrlEncoded
    @POST("grocery-order-payment")
    Call<DataResponse> getOrderPayment(
            @Field("customerId") String customerId,
            @Field("paymentId") String paymentId,
            @Field("orderId") String orderId,
            @Field("paymentMethod") String paymentMethod,
            @Field("paymentStatus") String paymentStatus
    );
    @FormUrlEncoded
    @POST("chanage-grocery-quantity-view")
    Call<DataResponse> updateToCartProduct(
            @Field("customerId") String customerId,
            @Field("cartId") String cartId,
            @Field("quantity") String quantity
    );

    @FormUrlEncoded
    @POST("delete-grocery-product-from-cart-view")
    Call<DataResponse> deleteCartProduct(
            @Field("cartId") String cartId,
            @Field("customerId") String customerId
    );
    @POST("login/")
    Call<LoginDataResponse> login(@Query("phone") String phone, @Query("password") String password, @Query("token") String token);

}