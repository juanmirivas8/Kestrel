package views

import controllers.Controller
import javafx.application.Platform
import javafx.geometry.Pos
import javafx.geometry.Side
import javafx.scene.text.FontWeight

import model.User
import tornadofx.*
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate

class Home : View("Home") {
    private val controller : Controller = Controller
    init{
            Timer().scheduleAtFixedRate(0, 1000) {
                Platform.runLater { controller.refresh() }
            }
    }
    override val root = borderpane {
        prefHeight = 800.0
        prefWidth = 800.0
        top{
            hbox(alignment = Pos.CENTER) {
                label("Home") {
                    style {
                        fontSize = 30.px
                        fontWeight = FontWeight.BOLD
                    }
                }
                button { text = "Post"
                    action{
                        MakePostView().openModal()
                    }
                }
                menubar{
                    menu(controller.user.nickname){
                        item("Log Out").action{
                            replaceWith<Login>(ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.DOWN))
                            controller.user = User("","")
                        }
                    }
                }
            }
        }
        center{
           drawer{
               style{
                   prefWidth = 700.px
               }
               item("My Posts", expanded = true){
                   scrollpane {
                        isFitToWidth = true
                        content= vbox{
                           bindChildren(controller.observablePost){
                               PostView(it).root
                           }
                       }
                   }

               }
               item("Liked Posts"){

               }
               item("Commented Posts"){
                   vbox{
                       label("Groups")
                   }
               }
           }
        }

        right{
            drawer(side = Side.RIGHT){
                style{
                    prefWidth = 300.px
                }
                item("Followers", expanded = true){
                    vbox{
                        children.bind(controller.user.followers.asObservable()){
                            hbox{
                                label { text = it.nickname }
                                button("Follow"){
                                    action{

                                    }
                                }
                            }
                        }
                    }
                }
                item("Following"){
                    vbox{
                        children.bind(controller.user.followed.asObservable()){
                            hbox{
                                label { text = it.nickname }
                                button("Follow"){
                                    action{

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
