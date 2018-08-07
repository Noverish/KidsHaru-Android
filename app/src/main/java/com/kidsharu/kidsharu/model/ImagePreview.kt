package com.kidsharu.kidsharu.model

import android.os.Parcel
import android.os.Parcelable

open class ImagePreview(
        val imageId: Int,
        val url: String
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readInt(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(imageId)
        writeString(url)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ImagePreview> = object : Parcelable.Creator<ImagePreview> {
            override fun createFromParcel(source: Parcel): ImagePreview = ImagePreview(source)
            override fun newArray(size: Int): Array<ImagePreview?> = arrayOfNulls(size)
        }
    }
}