package com.testing.bestcommerce.api;

import com.testing.bestcommerce.model.ClsAddProduct;
import com.testing.bestcommerce.model.ClsCheckShop;
import com.testing.bestcommerce.model.ClsCheckout;
import com.testing.bestcommerce.model.ClsDelivery;
import com.testing.bestcommerce.model.ClsDeliveryForSeller;
import com.testing.bestcommerce.model.ClsHistory;
import com.testing.bestcommerce.model.ClsHistoryOrder;
import com.testing.bestcommerce.model.ClsInboxDetail;
import com.testing.bestcommerce.model.ClsKonfirmasiPenerimaan;
import com.testing.bestcommerce.model.ClsKonfirmasiPengiriman;
import com.testing.bestcommerce.model.ClsLogin;
import com.testing.bestcommerce.model.ClsOpenShop;
import com.testing.bestcommerce.model.ClsProduct;
import com.testing.bestcommerce.model.ClsRegister;
import com.testing.bestcommerce.orm.tbl_new_cart;
import com.testing.bestcommerce.orm.tbl_pengiriman;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface MobileService {
    @FormUrlEncoded
    @POST("login")
    Call<ClsLogin> getToken(@Field("username") String username, @Field("password") String password);

    @FormUrlEncoded
    @POST("openshop/insert")
    Call<ClsOpenShop> openShop(@Field("user_id") int user_id,
                               @Field("nama_toko") String nama_toko,
                               @Field("alamat") String alamat);

    @FormUrlEncoded
    @POST("product/insert")
    Call<ClsAddProduct> addProduct(@Field("user_id") int user_id,
                                   @Field("nama_produk") String nama_produk,
                                   @Field("jenis_produk") String jenis_produk,
                                   @Field("deskripsi_produk") String deskripsi_produk,
                                   @Field("harga") float harga,
                                   @Field("stok") int stok);

    @GET("get/product/byseller/{input}")
    Call <ClsProduct> getProduct(@Path("input") int input);

    @GET("get/seller/{input}")
    Call <ClsCheckShop> checkShop(@Path("input") int input);

    @GET("get/all/product")
    Call <ClsProduct> getAllProducts();

    @GET("get/product/{input}")
    Call <ClsProduct> getProductByID(@Path("input") int input);

    @Headers({"Content-Type: application/json"})
    @POST("newcheckout/byuser")
    Call <ClsCheckout> checkOut(@Body List<tbl_new_cart> request);

    @Headers({"Content-Type: application/json"})
    @POST("submit/delivery")
    Call <ClsDelivery> delivery(@Body List<tbl_pengiriman> request);

    @GET("get/delivery/seller/{input}")
    Call <ClsDeliveryForSeller> getDeliveryForSeller(@Path("input") int input);

    @GET("get/detail/delivery/seller/{input}")
    Call <ClsInboxDetail> getDeliveryInboxDetail(@Path("input") int input);

    @FormUrlEncoded
    @POST("konfirmasi/pengiriman")
    Call<ClsKonfirmasiPengiriman> confirmDelivery(@Field("id") int id,
                                                  @Field("kurir") String kurir,
                                                  @Field("id_user") int id_user);

    @FormUrlEncoded
    @POST("konfirmasi/penerimaan")
    Call<ClsKonfirmasiPenerimaan> confirmAcceptance(@Field("id") int id,
                                                    @Field("id_user") int id_user);

    @GET("get/history/{input}")
    Call <ClsHistory> getHistory(@Path("input") int input);

    @GET("get/detail/history/{input}")
    Call <ClsHistoryOrder> getHistoryOrder(@Path("input") int input);

    @FormUrlEncoded
    @POST("register/user")
    Call<ClsRegister> register(@Field("first_name") String first_name,
                               @Field("last_name") String last_name,
                               @Field("email") String email,
                               @Field("username") String username,
                               @Field("password") String password,
                               @Field("phone") String phone
                               );
}
