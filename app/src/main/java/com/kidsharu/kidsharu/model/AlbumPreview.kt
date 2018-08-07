package com.kidsharu.kidsharu.model

class AlbumPreview(
        val albumId: Int,
        val coverImgUrl: String,
        val title: String,
        val date: String
) {
    override fun toString(): String {
        return "AlbumPreview(albumId=$albumId, coverImgUrl='$coverImgUrl', title='$title', date='$date')"
    }
}