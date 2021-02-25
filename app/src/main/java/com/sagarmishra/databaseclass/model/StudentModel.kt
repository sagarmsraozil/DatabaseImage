package com.sagarmishra.databaseclass.model

import android.os.Parcel
import android.os.Parcelable

data class StudentModel(var studentName:String?, var address:String?, var age:Int?, var dp:String?, var gender:String?):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(studentName)
        parcel.writeString(address)
        parcel.writeValue(age)
        parcel.writeString(dp)
        parcel.writeString(gender)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StudentModel> {
        override fun createFromParcel(parcel: Parcel): StudentModel {
            return StudentModel(parcel)
        }

        override fun newArray(size: Int): Array<StudentModel?> {
            return arrayOfNulls(size)
        }
    }

}