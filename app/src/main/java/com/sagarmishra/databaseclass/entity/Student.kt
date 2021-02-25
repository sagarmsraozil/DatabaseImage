package com.sagarmishra.databaseclass.entity

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class Student(
    @PrimaryKey
    var _id:String="",
    var fullName:String?=null,
    var address:String?=null,
    var gender:String?=null,
    var displayPicture:String?=null,
    var age:Int = 0
){

}

