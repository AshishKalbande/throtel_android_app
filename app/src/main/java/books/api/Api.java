package books.api;

import books.models.AddressListDataResponse;
import books.models.CityDataResponse;
import books.models.CouponCodeDataResponse;
import books.models.CustomerOrderListDataResponse;
import books.models.DashbordDataResponse;
import books.models.DataResponse;
import books.models.DeliveryDatesResponse;
import books.models.GroupListDataResponse;
import books.models.GroupWiseDataResponse;
import books.models.LoginDataResponse;
import books.models.OrderDataResponse;
import books.models.OrderDetailsDataResponse;
import books.models.OrderListDataResponse;
import books.models.PincodeListDataResponse;
import books.models.SearchListDataResponse;
import books.models.SlotListDataResponse;
import books.models.StateListDataResponse;
import books.models.SubCategoryDataResponse;
import books.models.SubCategoryWiseProductDataResponse;
import books.models.TransactionDataResponse;
import books.models.ViewCartDataResponse;
import books.models.notifications.NotificationCountResponse;
import books.models.notifications.NotificationListResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    String BaseURL = "http://throtel.com/book-customer-api/";


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
    @POST("book-dashboard")
    Call<DashbordDataResponse> getDashBoardData(
            @Field("customerId") String customerId
    );

    @FormUrlEncoded
    @POST("book-main-category-list-view")
    Call<GroupListDataResponse> getGroupListData(
            @Field("customerId") String customerId
    );



    @FormUrlEncoded
    @POST("book-category-wise-sub-category-list-view")
    Call<SubCategoryDataResponse> getSubCategoryListData(
            @Field("customerId") String customerId,
            @Field("mainCategoryId") String mainCategoryId

    );

    @FormUrlEncoded
    @POST("book-sub-category-wise-product-list-view")
    Call<SubCategoryWiseProductDataResponse> getSubCategoryWiseListData(
            @Query("limit") int limit,
            @Field("customerId") String customerId,
            @Field("pageNumber") String pageNumber,
            @Field("mainCategoryId") String mainCategoryId,
            @Field("subCategoryId") String subCategoryId
    );

    @FormUrlEncoded
    @POST("get-book-product-detail")
    Call<GroupWiseDataResponse> getGroupWiseListData(
            @Field("customerId") String customerId,
            @Field("productId") String productId
    );

    @FormUrlEncoded
    @POST("add-book-product-to-cart-view")
    Call<DataResponse> addToCartProduct(
            @Field("productId") String productId,
            @Field("customerId") String customerId,
            @Field("quantity") String quantity
            // @Field("productType") String productType
    );

    @FormUrlEncoded
    @POST("search-book-product-view")
    Call<SearchListDataResponse> getSearchProductListData(
            @Field("customerId") String customerId,
            @Field("searchData") String searchData
           // @Field("pageNumber") String pageNumber
            //@Field("productType") String productType
    );
    @FormUrlEncoded
    @POST("book-customer-cart-list-view")
    Call<ViewCartDataResponse> getViewCartList(
            @Field("customerId") String customerId
            //@Field("productType") String productType
    );

    @FormUrlEncoded
    @POST("change-book-address-status-view")
    Call<DataResponse> getStatusChange(
            @Field("customerId") String customerId,
            @Field("addressId") String addressId,
            @Field("status") String status
    );
    @FormUrlEncoded
    @POST("book-customer-address-list-view")
    Call<AddressListDataResponse> getAddressList(
            @Field("customerId") String customerId
    );

    @FormUrlEncoded
    @POST("book-customer-order-product-list-view")
    Call<CustomerOrderListDataResponse> getCustomerOrdersListData(
            @Field("customerId") String customerId,
            @Field("orderId") String orderId
    );
    @FormUrlEncoded
    @POST("change-book-customer-notification-status-unread-to-read")
    Call<DataResponse> readNotifications(
            @Field("customerId") String customerId
           // @Field("notificationFor") String notificationFor
    );
    @FormUrlEncoded
    @POST("apply-book-coupon")
    Call<DataResponse> getCoupneId(
            @Field("couponId") String couponId,
            @Field("customerId") String customerId
    );
    @FormUrlEncoded
    @POST("check-book-coupon-availability-and-get-price")
    Call<CouponCodeDataResponse> getCouponApplyData(
            @Field("couponName") String couponName,
            @Field("customerId") String customerId
    );
    @FormUrlEncoded
    @POST("place-book-order")
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
    @POST("book-customer-order-list-view")
    Call<OrderListDataResponse> getOrdersListData(
            @Field("customerId") String customerId
    );






    @FormUrlEncoded
    @POST("book-customer-order-detail-view")
    Call<OrderDetailsDataResponse> getOrderDetails(
            @Field("customerId") String customerId,
            @Field("orderId") String orderId
    );

    @FormUrlEncoded
    @POST("get-book-customer-notification-list")
    Call<NotificationListResponse> getNotificationList(
            @Field("customerId") String customerId

    );
    @FormUrlEncoded
    @POST("get-book-customer-unread-notification-count")
    Call<NotificationCountResponse> getNotificationCount(
            @Field("customerId") String customerId

    );

    @FormUrlEncoded
    @POST("book-add-address-view")
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
    @POST("update-book-address-view")
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
    @POST("book-delivery-slot-list-view")
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
    @POST("get-book-delivery-date-list-view")
    Call<DeliveryDatesResponse> getDeliveryDatesList(
            @Field("customerId") String customerId
    );

    @GET("transaction-charge-detail-view")
    Call<TransactionDataResponse> getTransactionData();

    @FormUrlEncoded
    @POST("book-order-payment")
    Call<DataResponse> getOrderPayment(
            @Field("customerId") String customerId,
            @Field("paymentId") String paymentId,
            @Field("orderId") String orderId,
            @Field("paymentMethod") String paymentMethod,
            @Field("paymentStatus") String paymentStatus
    );
    @FormUrlEncoded
    @POST("chanage-book-quantity-view")
    Call<DataResponse> updateToCartProduct(
            @Field("customerId") String customerId,
            @Field("cartId") String cartId,
            @Field("quantity") String quantity
    );

    @FormUrlEncoded
    @POST("delete-book-product-from-cart-view")
    Call<DataResponse> deleteCartProduct(
            @Field("cartId") String cartId,
            @Field("customerId") String customerId
    );
    @POST("login/")
    Call<LoginDataResponse> login(@Query("phone") String phone, @Query("password") String password, @Query("token") String token);

    @FormUrlEncoded
    @POST("get-book-customer-notification-list")
    Call<NotificationListResponse> getNotificationLis(
            @Field("customerId") String customerId,
            @Field("notificationFor") String notificationFor
    );

}