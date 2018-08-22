package com.kidsharu.kidsharu

import com.kidsharu.kidsharu.model.AlbumDetail
import com.kidsharu.kidsharu.model.AlbumPreview
import com.kidsharu.kidsharu.model.ImagePreview

object DummyDatabaseClient {
    fun getAlbumDetail(albumId: Int,
                       callback: (AlbumDetail) -> Unit) {
        val imageNum = when(albumId) {
            1 -> 20
            2 -> 25
            3 -> 13
            4 -> 25
            5 -> 19
            6 -> 39
            7 -> 8
            8 -> 3
            else -> 0
        }

        callback(
                AlbumDetail(
                        1,
                        "https://s3.ap-northeast-2.amazonaws.com/kidsharu-test/001/000.jpg",
                        "title1",
                        "",
                        "2018-01-01",
                        14,
                        Array<ImagePreview>(imageNum) { i ->
                            val tmp1 = String.format("%03d", albumId)
                            val tmp2 = String.format("%03d", i)

                            ImagePreview(
                                    i,
                                    "https://s3.ap-northeast-2.amazonaws.com/kidsharu-test/$tmp1/$tmp2.jpg"
                            )
                        }

                )
        )
    }
}