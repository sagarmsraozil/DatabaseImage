package com.sagarmishra.databaseclass.entity

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AuthenticateData(var username:String?=null, var password:String?=null):Parcelable {
    @PrimaryKey(autoGenerate = true)
    var loginId : Int = 0

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
        loginId = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeString(password)
        parcel.writeInt(loginId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AuthenticateData> {
        override fun createFromParcel(parcel: Parcel): AuthenticateData {
            return AuthenticateData(parcel)
        }

        override fun newArray(size: Int): Array<AuthenticateData?> {
            return arrayOfNulls(size)
        }
    }
}