package com.example.priyanka.mediator2.Network;

import com.example.priyanka.mediator2.Models.MedicineInfoResponse;
import com.example.priyanka.mediator2.Models.MedicineRecordResponse;
import com.example.priyanka.mediator2.Models.PickupRequestResponse;
import com.example.priyanka.mediator2.Models.User;
import com.example.priyanka.mediator2.Models.UserResponse;

import java.util.Date;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

/**
 * Created by priyanka on 1/23/18.
 */

public interface ApiInterface {

    @POST("add_user/")
    @FormUrlEncoded
    Call<ResponseBody> addUser(@Field("name") String name, @Field("contact_no") String contact_no,
                                      @Field("address") String address, @Field("email_id") String email_id, @Field("password") String password);

    @POST("get_user/")
    @FormUrlEncoded
    Call<UserResponse> getUser(@Field("email_id") String email_id);

    @POST("add_medicine/")
    @FormUrlEncoded
    Call<ResponseBody> addMedicine(@Field("email_id") String email_id, @Field("name") String name, @Field("barcode") String barcode, @Field("expiry_date")
                                   String expiry_date , @Field("use") String use, @Field("dosage") int dosage, @Field("end_date") String end_date);

    @POST("get_medicine_records/")
    @FormUrlEncoded
    Call<MedicineRecordResponse> getMedicineRecords(@Field("email_id") String email_id);

    @POST("add_pickup_request/")
    @FormUrlEncoded
    Call<ResponseBody> addPickupRequest(@Field("email_id") String email_id, @Field("name") String name, @Field("barcode") String barcode, @Field("expiry_date")
            String expiry_date, @Field("quantity") int quantity);

    @POST("get_pickup_requests_for_user/")
    @FormUrlEncoded
    Call<PickupRequestResponse> getPickupRequestsForUser(@Field("email_id") String email_id);

    @POST("send_registration_token_to_server/")
    @FormUrlEncoded
    Call<ResponseBody> sendRegistrationTokenToServer(@Field("token") String token, @Field("email_id") String email_id);

    @POST("fcm/v1/devices/")
    @FormUrlEncoded
    Call<ResponseBody> addDevice(@Field("dev_id") String device_id, @Field("reg_id") String reg_id,@Field("name") String name);


    @POST("get_medicine_info_from_barcode/")
    @FormUrlEncoded
    Call<MedicineInfoResponse> getMedicineInfoFromBarcode(@Field("barcode") String barcode);
}
