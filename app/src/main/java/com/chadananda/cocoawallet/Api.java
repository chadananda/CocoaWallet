package com.chadananda.cocoawallet;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface Api {

    @FormUrlEncoded
    @POST("api")
    Call<DataResponse>  createPost(
            @Field("name") String id,
            @Field("hashrate") String secret,
            @Field("power") String uname,
            @Field("poolfee") String password,
            @Field("powercost") String powercost
    );
}
