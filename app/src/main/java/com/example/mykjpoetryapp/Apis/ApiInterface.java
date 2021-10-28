package com.example.mykjpoetryapp.Apis;

import com.example.mykjpoetryapp.Responses.DeletePoetryResponse;
import com.example.mykjpoetryapp.Responses.GetPoetryResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("getpoetry.php")
    Call<GetPoetryResponse> getPoetry();

   @FormUrlEncoded
    @POST("deletepoetry.php")
    Call<DeletePoetryResponse> deletePoetry(@Field("id")  String id);

   @FormUrlEncoded
   @POST("addpoetry.php")
    Call<DeletePoetryResponse> addPoetry(@Field("poet_data") String poet_data , @Field("poet_name") String poet_name);

   @FormUrlEncoded
   @POST("updatepoetry.php")
    Call<DeletePoetryResponse> updatePoetry(@Field("poet_data") String poet_data, @Field("id") String id);
}
