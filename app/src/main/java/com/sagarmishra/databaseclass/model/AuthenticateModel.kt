package com.sagarmishra.databaseclass.model

import android.os.Parcel
import android.os.Parcelable

data class AuthenticateModel(val username:String?,val password:String?):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(username)
        parcel.writeString(password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AuthenticateModel> {
        override fun createFromParcel(parcel: Parcel): AuthenticateModel {
            return AuthenticateModel(parcel)
        }

        override fun newArray(size: Int): Array<AuthenticateModel?> {
            return arrayOfNulls(size)
        }
    }
}