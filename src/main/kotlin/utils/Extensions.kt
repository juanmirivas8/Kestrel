package utils

import javafx.scene.control.Alert
import javafx.scene.layout.BorderPane
import tornadofx.alert
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

fun <T> T.showPopUpError(title: String, header: String, content: String) =
    alert(Alert.AlertType.ERROR,title= title, header =  header, content =  content)

fun <T> T.showPopUpSuccess(title: String, header: String, content: String) =
    alert(Alert.AlertType.CONFIRMATION,title= title, header =  header, content =  content)

