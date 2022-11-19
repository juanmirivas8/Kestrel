package views

import model.Post
import tornadofx.*

class PostView(val post: Post) : Fragment("My View") {
    override val root = borderpane {
        style{
            prefHeight = 300.px
            prefWidth = 500.px
            borderColor += box(c("#B1FFF7"))
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
                    }
                    action{

                    }
                }
                button("Comment"){
                    action{

                    }
                }
                button("Edit"){

                }
                button("Delete"){

                }
            }
        }
    }
}
