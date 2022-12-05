package views

import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.text.FontWeight
import tornadofx.*

class MyStyles : Stylesheet() {
    companion object{
        val loginLabel by cssclass()
        val mainLogin by cssclass()
    }

    init{
        mainLogin{
            backgroundColor += c("#B1FFF7")
            prefHeight = 600.px
            prefWidth = 600.px
            alignment = Pos.CENTER
        }

        loginLabel{
            fontSize = 30.px
            alignment = Pos.CENTER
            fontWeight = FontWeight.BOLD
        }

    }
}