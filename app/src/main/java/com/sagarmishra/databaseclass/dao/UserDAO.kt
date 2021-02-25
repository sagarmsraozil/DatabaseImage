package com.sagarmishra.databaseclass.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.sagarmishra.databaseclass.entity.User

@Dao
interface UserDAO {
    @Insert
    suspend fun userInsert(user: User)

//    @Query("select * from  User as u where u.username = :un and u.password = :pw")
//    suspend fun authenticate(un:String,pw:String):User
}