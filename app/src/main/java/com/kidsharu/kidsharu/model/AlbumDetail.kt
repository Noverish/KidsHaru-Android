package com.kidsharu.kidsharu.model

import android.os.Parcel
import android.os.Parcelable

class AlbumDetail(
        albumId: Int,
        coverImgUrl: String,
        title: String,
        date: String,
        val childrenNum: Int,
        val imagePreviews: Array<ImagePreview>
) : AlbumPreview(albumId, coverImgUrl, title, date), Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readInt(),
            // TODO Remove if statement
            if (true) {
                val tmp = ArrayList<ImagePreview>()
                source.readTypedList(tmp, ImagePreview.CREATOR)
                tmp.toTypedArray()
            } else {
                emptyArray<ImagePreview>()
            }
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) {
        super.writeToParcel(dest, flags)
        dest.writeInt(childrenNum)
        dest.writeTypedList(imagePreviews.toList())
        dest.writeTypedArray(imagePreviews, 0)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<AlbumDetail> = object : Parcelable.Creator<AlbumDetail> {
            override fun createFromParcel(source: Parcel): AlbumDetail = AlbumDetail(source)
            override fun newArray(size: Int): Array<AlbumDetail?> = arrayOfNulls(size)
        }
    }
}
