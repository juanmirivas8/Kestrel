package views

import controllers.Controller
import model.Post
import tornadofx.*

class PostView(private val post: Post) : Fragment("My View") {
    private val controller : Controller = Controller
    override val root = borderpane {
        style{
            borderColor += box(c("#B1FFF7"))
        }

        top = hbox {
            style{
                spacing = 10.px
            }
            label(post.user.username)
            if(post.edited){
                label("edited it on: ${post.date.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))}")
            }else{
                label("posted it on: ${post.date.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))}")
            }
        }
        center{
            label(post.content){
                style{
                    wrapText = true
                }
            }
        }
        bottom{
             hbox{
                button("Comments: ${post.comments.size}"){
                    action{
                        CommentView(post).openModal()
                    }
                }
                if(post.user.id == controller.user.id){
                    button("Edit"){
                        action{
                            MakePostView(post).openModal()
                        }
                    }
                    button("Delete"){
                        action{
                            post.delete()
                        }
                    }
                }
            }
        }
    }
}
