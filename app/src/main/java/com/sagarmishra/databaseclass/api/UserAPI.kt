package com.sagarmishra.databaseclass.api

import com.sagarmishra.databaseclass.entity.User
import com.sagarmishra.databaseclass.response.ServerResponse
import retrofit2.Response
import retrofit2.http.*

interface UserAPI {
    @POST("insert/user")
    suspend fun registerUser(@Body user: User):Response<ServerResponse>

    @FormUrlEncoded
    @POST("login/user")
    suspend fun authenticateUser(
        @Field("un") un:String? = null,
        @Field("pw") pw:String? =null):Response<ServerResponse>


    @POST("retrieveUsers")
    suspend fun retrieveUser():Response<ServerResponse>

}