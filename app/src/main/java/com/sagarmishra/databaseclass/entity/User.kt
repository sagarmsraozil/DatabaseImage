package com.sagarmishra.databaseclass.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey


data class User(
    val _id: String? = null,
    var first_name: String? = null,
    var last_name: String? = null,
    var username: String? = null,
    var email: String? = null,
    var password: String? = null
)


// ----------For ROOM DB ------------------
//@Entity
//data class User(var first_name:String? = null,var last_name:String? = null,var username:String? = null,var password:String? = null,var email:String? = null):Parcelable {
//    @PrimaryKey(autoGenerate = true)
//    var id : Int = 0
//
//    constructor(parcel: Parcel) : this(
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString(),
//        parcel.readString()
//    ) {
//        id = parcel.readInt()
//    }
//
//    override fun writeToParcel(parcel: Parcel, flags: Int) {
//        parcel.writeString(first_name)
//        parcel.writeString(last_name)
//        parcel.writeString(username)
//        parcel.writeString(password)
//        parcel.writeString(email)
//        parcel.writeInt(id)
//    }
//
//    override fun describeContents(): Int {
//        return 0
//    }
//
//    companion object CREATOR : Parcelable.Creator<User> {
//        override fun createFromParcel(parcel: Parcel): User {
//            return User(parcel)
//        }
//
//        override fun newArray(size: Int): Array<User?> {
//            return arrayOfNulls(size)
//        }
//    }
//}