package com.kidsharu.kidsharu.model

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

open class Picture(
        val albumId: Int,
        val pictureId: Int,
        val pictureUrl: String,
        val status: String
) : Parcelable {
    constructor(json: JSONObject) : this(
            json.getInt("album_id"),
            json.getInt("picture_id"),
            json.getString("picture_url"),
            json.getString("status")
    )

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readInt(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(albumId)
        writeInt(pictureId)
        writeString(pictureUrl)
        writeString(status)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Picture> = object : Parcelable.Creator<Picture> {
            override fun createFromParcel(source: Parcel): Picture = Picture(source)
            override fun newArray(size: Int): Array<Picture?> = arrayOfNulls(size)
        }
    }
}