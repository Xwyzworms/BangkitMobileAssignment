package com.example.sec2_latihanaplikasi11

import retrofit2.Call
import retrofit2.http.*

interface ApiInteface {

    @GET("detail/{id}")
    fun getRestaurant(@Path("id") id : String) : Call<Response>

    @FormUrlEncoded
    @Headers("Authorization: Token 123456")
    @POST("review")
    fun postReview(
        @Field("id") id: String,
        @Field("name") name: String,
        @Field("review") review: String
    ) : Call<PostReviewResponse>

}