package com.kidsharu.kidsharu

import com.kidsharu.kidsharu.model.AlbumDetail
import com.kidsharu.kidsharu.model.AlbumPreview
import com.kidsharu.kidsharu.model.ImagePreview

object DummyDatabaseClient {
    fun getTeacherAlbumList(teacherId: Int,
                            page: Int,
                            callback: (Array<AlbumPreview>) -> Unit) {
        val array = arrayOf(
                AlbumPreview(
                        1,
                        "https://s3.ap-northeast-2.amazonaws.com/kidsharu-test/001/000.jpg",
                        "title1",
                        "2018-01-01"
                ),
                AlbumPreview(
                        2,
                        "https://s3.ap-northeast-2.amazonaws.com/kidsharu-test/002/000.jpg",
                        "title1",
                        "2018-01-01"
                ),
                AlbumPreview(
                        3,
                        "https://s3.ap-northeast-2.amazonaws.com/kidsharu-test/003/000.jpg",
                        "title1",
                        "2018-01-01"
                ),
                AlbumPreview(
                        4,
                        "https://s3.ap-northeast-2.amazonaws.com/kidsharu-test/004/000.jpg",
                        "title1",
                        "2018-01-01"
                ),
                AlbumPreview(
                        5,
                        "https://s3.ap-northeast-2.amazonaws.com/kidsharu-test/005/000.jpg",
                        "title1",
                        "2018-01-01"
                ),
                AlbumPreview(
                        6,
                        "https://s3.ap-northeast-2.amazonaws.com/kidsharu-test/006/000.jpg",
                        "title1",
                        "2018-01-01"
                ),
                AlbumPreview(
                        7,
                        "https://s3.ap-northeast-2.amazonaws.com/kidsharu-test/007/000.jpg",
                        "title1",
                        "2018-01-01"
                ),
                AlbumPreview(
                        8,
                        "https://s3.ap-northeast-2.amazonaws.com/kidsharu-test/008/000.jpg",
                        "title1",
                        "2018-01-01"
                )
        )

        callback(array)
    }

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