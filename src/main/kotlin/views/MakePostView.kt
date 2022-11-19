package views

import controllers.Controller
import javafx.geometry.Pos
import javafx.scene.control.TextArea
import model.Post
import model.PostDAO
import tornadofx.*
import java.time.LocalDateTime

class MakePostView : Fragment("My View") {
    val controller = Controller
    override val root = vbox {
    style{
            prefHeight = 300.px
            prefWidth = 300.px
            alignment = Pos.CENTER
            spacing = 10.px
        }
        var txtarea: TextArea = textarea {
                style{
                    prefHeight = 200.px
                    prefWidth = 200.px
                    wrapText = true
                }
            }
        button {
            text = "Post"
            action {
                val p = PostDAO(Post(text = txtarea.text,user = controller.user, likes =  mutableListOf(), comments =  mutableListOf(),
                   date = LocalDateTime.now(),edited = false))
                txtarea.text = ""
                close()
                controller.observablePost.add(p)
            }
        }
    }
}
