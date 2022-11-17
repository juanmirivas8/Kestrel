package utils

import java.security.MessageDigest

fun String.Companion.encryptSHA256(text: String): String {
   val md :MessageDigest = MessageDigest.getInstance("SHA-256")
    md.update(text.toByteArray())
    return StringBuilder().apply {
        md.digest().forEach {
            append(String.format("%02x", it))
        }
    }.toString()
}