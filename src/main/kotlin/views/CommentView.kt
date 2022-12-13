package views

import controllers.Controller
import javafx.geometry.Pos
import model.Comment
import model.Post
import tornadofx.*
import java.time.LocalDateTime

class CommentView(post: Post) : View("Comments") {
    private val controller = Controller
    private val oblist = post.comments.asObservable()
    override val root = scrollpane {

        vbox(alignment = Pos.CENTER) {
            style{
                spacing = 2.px
                prefWidth = 500.px
                prefHeight = 500.px
            }
            bindChildren(oblist){
                hbox {
                    label("${it.user.username} on ${it.date.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))}: ${it.content}")
                    if(post.user.id == controller.user.id){
                        button("Delete"){
                            action{
                                it.delete()
                            }
                        }
                    }
                }

            }

            val tf = textfield{}
            button("Comment"){
                action{
                    val comment = Comment(user = controller.user, post = post, content = tf.text, date = LocalDateTime.now())
                    tf.text = ""
                }
            }

        }
    }
}
