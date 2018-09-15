package com.kidsharu.kidsharu.other

import java.security.MessageDigest

object CryptoUtil {
    fun sha256(s: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hash = digest.digest(s.toByteArray())
        val hexString = StringBuilder()
        for (h in hash) {
            val hex = Integer.toHexString(0xff and h.toInt())
            if (hex.length == 1) hexString.append('0')
            hexString.append(hex)
        }
        return hexString.toString()
    }
}