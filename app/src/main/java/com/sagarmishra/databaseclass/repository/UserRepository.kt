package com.sagarmishra.databaseclass.repository

import com.sagarmishra.databaseclass.api.ApiRequest
import com.sagarmishra.databaseclass.api.ServiceBuilder
import com.sagarmishra.databaseclass.api.UserAPI
import com.sagarmishra.databaseclass.entity.User
import com.sagarmishra.databaseclass.response.ServerResponse

class UserRepository():ApiRequest() {
    var userApi = ServiceBuilder.buildService(UserAPI::class.java)

    suspend fun registerUser(user:User):ServerResponse
    {

            return apiRequest {
                 userApi.registerUser(user)
            }
    }

    suspend fun authenticateUser(un:String,pw:String):ServerResponse
    {
        return apiRequest {
            userApi.authenticateUser(un,pw)
        }
    }

    suspend fun retrieveUser():ServerResponse
    {
        return apiRequest {
            userApi.retrieveUser()
        }
    }
}
