package com.chadananda.cocoawallet;

public interface RetrofitApi {



        // as we are making a post request to post a data
        // so we are annotating it with post
        // and along with that we are passing a parameter as users
        @POST("bookingDetails")
        //on below line we are creating a method to post our data.
        Call<DetailResponse> createPost(@Body DataModel dataModal);


}
