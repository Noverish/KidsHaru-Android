package com.kidsharu.kidsharu

import com.kidsharu.kidsharu.model.AlbumPreview

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
                        1,
                        "https://s3.ap-northeast-2.amazonaws.com/kidsharu-test/002/000.jpg",
                        "title1",
                        "2018-01-01"
                ),
                AlbumPreview(
                        1,
                        "https://s3.ap-northeast-2.amazonaws.com/kidsharu-test/003/000.jpg",
                        "title1",
                        "2018-01-01"
                ),
                AlbumPreview(
                        1,
                        "https://s3.ap-northeast-2.amazonaws.com/kidsharu-test/004/000.jpg",
                        "title1",
                        "2018-01-01"
                ),
                AlbumPreview(
                        1,
                        "https://s3.ap-northeast-2.amazonaws.com/kidsharu-test/005/000.jpg",
                        "title1",
                        "2018-01-01"
                ),
                AlbumPreview(
                        1,
                        "https://s3.ap-northeast-2.amazonaws.com/kidsharu-test/006/000.jpg",
                        "title1",
                        "2018-01-01"
                ),
                AlbumPreview(
                        1,
                        "https://s3.ap-northeast-2.amazonaws.com/kidsharu-test/007/000.jpg",
                        "title1",
                        "2018-01-01"
                ),
                AlbumPreview(
                        1,
                        "https://s3.ap-northeast-2.amazonaws.com/kidsharu-test/008/000.jpg",
                        "title1",
                        "2018-01-01"
                )
        )

        callback(array)
    }
}