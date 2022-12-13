package views

import controllers.Controller
import javafx.geometry.Pos
import javafx.scene.control.TextArea
import model.Post
import tornadofx.*
import java.time.LocalDateTime

class MakePostView(post: Post = Post()) : Fragment("My View") {
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
            if(post.id == 0){
                text = "Post"
                action{
                    val p = Post(content = txtarea.text,user = controller.user,
                        date = LocalDateTime.now(),edited = false)
                    controller.user.posts.add(p)
                    txtarea.text = ""
                }
            }else{
                text = "Edit"
                action {
                    post.content = txtarea.text
                    post.edited = true
                    post.date = LocalDateTime.now()
                    controller.user.posts.remove(post)
                    controller.user.posts.add(post)
                    close()
                }
            }
        }
    }
}
