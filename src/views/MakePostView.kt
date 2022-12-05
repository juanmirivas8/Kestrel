package views

import controllers.Controller
import javafx.geometry.Pos
import javafx.scene.control.TextArea
import model.Post
import model.PostDAO
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
        var txtarea: TextArea = textarea {
                style{
                    prefHeight = 200.px
                    prefWidth = 200.px
                    wrapText = true
                    text = post.text
                }
            }
        button {
            if(post.id == 0){
                text = "Post"
                action{
                    val p = Post(text = txtarea.text,user = controller.user, likes =  mutableListOf(), comments =  mutableListOf(),
                        date = LocalDateTime.now(),edited = false)
                    controller.user += p
                    txtarea.text = ""
                }
            }else{
                text = "Edit"
                action {
                    post.text = txtarea.text
                    post.edited = true
                    post.date = LocalDateTime.now()
                    PostDAO(post).update()
                    controller.user.posts.remove(post)
                    controller.user.posts.add(post)
                    close()
                }
            }
        }
    }
}
