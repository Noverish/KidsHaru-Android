package com.kidsharu.kidsharu.other

import com.kidsharu.kidsharu.model.Album
import com.squareup.otto.Bus
import com.squareup.otto.ThreadEnforcer

object EventBus {
    var main: Bus = Bus(ThreadEnforcer.MAIN)
}

class AlbumAddEvent(val album: Album)
class AlbumModifyEvent(val album: Album)