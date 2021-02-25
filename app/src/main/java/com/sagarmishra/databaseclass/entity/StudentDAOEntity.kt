package com.sagarmishra.databaseclass.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class StudentDAOEntity(@PrimaryKey var _id:String?=null, var fullName:String?=null, var address:String?=null, var gender:String?=null, var displayPicture:String?=null, var age:Int=0) {

}