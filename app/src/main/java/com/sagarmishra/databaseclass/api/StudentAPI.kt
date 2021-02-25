package com.sagarmishra.databaseclass.api

import com.sagarmishra.databaseclass.entity.Student
import com.sagarmishra.databaseclass.response.ServerResponse
import com.sagarmishra.databaseclass.response.StudentDataResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface StudentAPI {
    @POST("insert/student")
    suspend fun insertStudent(@Body student: Student): Response<ServerResponse>

    @GET("get/student")
    suspend fun retrieveStudents(@Header("Authorization") token: String): Response<StudentDataResponse>

    @DELETE("delete/{pid}")
    suspend fun deleteStudent(
        @Header("Authorization") token: String,
        @Path("pid") pid: String
    ): Response<ServerResponse>

    @Multipart
    @PUT("insertPicture/{id}")
    suspend fun uploadPhoto(
        @Path("id") id:String,
        @Part file:MultipartBody.Part
    ):Response<ServerResponse>



}