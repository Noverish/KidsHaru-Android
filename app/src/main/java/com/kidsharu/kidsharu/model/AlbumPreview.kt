package com.kidsharu.kidsharu.model

import android.os.Parcel
import android.os.Parcelable
import org.json.JSONObject

open class AlbumPreview(
        val albumId: Int,
        val coverImgUrl: String,
        val title: String,
        val content: String,
        val date: String
) : Parcelable {
    override fun toString(): String {
        return "AlbumPreview(albumId=$albumId, coverImgUrl='$coverImgUrl', title='$title', content='$content', date='$date')"
    }

    constructor(json: JSONObject) : this(
            json.getInt("album_id"),
            json.getString("cover_img"),
            json.getString("title"),
            json.getString("content"),
            json.getString("date")
    )

    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(albumId)
        writeString(coverImgUrl)
        writeString(title)
        writeString(content)
        writeString(date)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<AlbumPreview> = object : Parcelable.Creator<AlbumPreview> {
            override fun createFromParcel(source: Parcel): AlbumPreview = AlbumPreview(source)
            override fun newArray(size: Int): Array<AlbumPreview?> = arrayOfNulls(size)
        }
    }
}