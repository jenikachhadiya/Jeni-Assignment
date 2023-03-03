package com.example.app.model

import android.os.Parcel
import android.os.Parcelable

data class Food(

    var id:Int,
    var title:String,
    var desc:String,
    var image:String,
    var ratting:Float,
    var rattingtext:Float,
    var bullet:Int,
    var address:String,

    ):
    Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString()!!,
        parcel.readString()!!,
        parcel.readString()!!,

        parcel.readFloat()!!,
        parcel.readFloat(),
        parcel.readInt(),
        parcel.readString()!!
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(desc)
        parcel.writeString(image)
        parcel.writeFloat(ratting)

        parcel.writeFloat(rattingtext)
        parcel.writeInt(bullet)
        parcel.writeString(address)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Food> {
        override fun createFromParcel(parcel: Parcel): Food {
            return Food(parcel)
        }

        override fun newArray(size: Int): Array<Food?> {
            return arrayOfNulls(size)
        }
    }
}