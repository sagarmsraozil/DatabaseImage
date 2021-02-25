package com.sagarmishra.databaseclass.repository

import com.sagarmishra.databaseclass.api.ApiRequest
import com.sagarmishra.databaseclass.api.ServiceBuilder
import com.sagarmishra.databaseclass.api.StudentAPI
import com.sagarmishra.databaseclass.entity.Student
import com.sagarmishra.databaseclass.response.ServerResponse
import com.sagarmishra.databaseclass.response.StudentDataResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody

class StudentRepository() : ApiRequest() {
    val studentAPI = ServiceBuilder.buildService(StudentAPI::class.java)

    suspend fun insertStudent(student: Student): ServerResponse {
        return apiRequest {
            studentAPI.insertStudent(student)
        }
    }

    suspend fun retrieveStudents(): StudentDataResponse {
        return apiRequest {
            studentAPI.retrieveStudents(ServiceBuilder.token!!)
        }
    }

    suspend fun deleteStudent(pid: String): ServerResponse {
        return apiRequest {
            studentAPI.deleteStudent(ServiceBuilder.token!!, pid)
        }
    }

    suspend fun uploadImage(
        id:String,
        body: MultipartBody.Part
    ): ServerResponse {
        return apiRequest {
            studentAPI.uploadPhoto(id,body)
        }
    }
}