package views

import controllers.Controller
import javafx.geometry.Pos
import model.Comment
import model.CommentDAO
import model.Post
import tornadofx.*

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
                    label("${it.user.nickname} on ${it.date.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))}: ${it.text}")
                    if(post.user.id == controller.user.id){
                        button("Delete"){
                            action{
                                CommentDAO(it).delete()
                                oblist.remove(it)
                            }
                        }
                    }
                }

            }

            val tf = textfield{}
            button("Comment"){
                action{
                    val comment = Comment(text= tf.text, user = controller.user, post = post)
                    val c = CommentDAO(comment)
                    c.create()
                    oblist.add(c)
                    tf.text = ""
                }
            }

        }
    }
}
