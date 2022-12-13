package views

import controllers.Controller
import javafx.geometry.Pos
import javafx.scene.control.TextArea
import model.Post
import tornadofx.*
import java.time.LocalDateTime

class MakePostView(var post: Post = Post()) : Fragment("My View") {
    private val controller = Controller
    override val root = vbox {
    style{
            prefHeight = 300.px
            prefWidth = 300.px
            alignment = Pos.CENTER
            spacing = 10.px
        }
        val txtarea: TextArea = textarea {
                style{
                    prefHeight = 200.px
                    prefWidth = 200.px
                    wrapText = true
                    text = post.content
                }
            }
        button {
            if(post.id == null){
                text = "Post"
                action{
                    post.apply {
                        content = txtarea.text
                        date = LocalDateTime.now()
                        user = controller.user
                        create()

                    }
                    post = Post()
                    txtarea.text = ""
                }
            }else{
                text = "Edit"
                action {
                    post.update(txtarea.text)
                    close()
                }
            }
        }
    }
}
