package utils

import javafx.scene.control.Alert
import tornadofx.alert
import java.security.MessageDigest
import java.util.logging.LogManager
import java.util.logging.Logger

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

fun <T> T.getLogger() : Logger{
    val configFile = object {}.javaClass.getResource("/logging.properties")
    LogManager.getLogManager().readConfiguration(configFile.openStream())
    return Logger.getLogger(Logger.GLOBAL_LOGGER_NAME)
}

