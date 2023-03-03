package com.example.new_task.entity

import android.os.Parcel
import android.os.Parcelable

data class ShopList(
    var id:Int,
    var img:String,
    var shopName:String,
    var shopDetails: String,
    var rating: Float,
    var address:String,
    var ratNum:String,
    var price:Float,
    var counter:Int=0
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readFloat().toFloat(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readFloat().toFloat()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(img)
        parcel.writeString(shopName)
        parcel.writeString(shopDetails)
        parcel.writeFloat(rating)
        parcel.writeString(address)
        parcel.writeString(ratNum)
        parcel.writeFloat(price)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ShopList> {
        override fun createFromParcel(parcel: Parcel): ShopList {
            return ShopList(parcel)
        }

        override fun newArray(size: Int): Array<ShopList?> {
            return arrayOfNulls(size)
        }
    }
}
