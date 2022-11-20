package views

import controllers.Controller
import model.Post
import model.PostDAO
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
            label(post.user.nickname)
            if(post.edited){
                label("edited it on: ${post.date.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))}")
            }else{
                label("posted it on: ${post.date.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"))}")
            }
        }
        center{
            label(post.text){
                style{
                    wrapText = true
                }
            }
        }
        bottom{
             hbox{
                button{
                    style{
                        shape = "M23.6,0c-3.4,0-6.3,2.7-7.6,5.6C14.7,2.7,11.8,0,8.4,0C3.8,0,0,3.8,0,8.4c0,9.4,9.5,11.9,16,21.26.1-9.3,16-12.1,16-21.2C32,3.8,28.2,0,23.6,0z"
                        prefWidth = 30.px
                        text = post.likes.size.toString()
                    }
                    action{

                    }
                }
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
                            controller.user -= post
                        }
                    }
                }
            }
        }
    }
}
