package com.sagarmishra.databaseclass.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sagarmishra.databaseclass.entity.AuthenticateData

@Dao
interface AuthenticateDAO {
    @Insert
    suspend fun insertData(userData : AuthenticateData)

    @Query("select * from AuthenticateData")
    suspend fun retrieveData():List<AuthenticateData>

    @Delete
    suspend fun deleteData(userData:AuthenticateData)
}