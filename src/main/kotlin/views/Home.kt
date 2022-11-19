package views

import controllers.Controller
import javafx.application.Platform
import javafx.geometry.Pos
import javafx.geometry.Side
import javafx.scene.control.Label
import javafx.scene.control.Menu
import javafx.scene.layout.VBox
import javafx.scene.text.FontWeight

import model.User
import tornadofx.*
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate

class Home : View("Home") {
    private val controller : Controller = Controller
    private lateinit var myPosts :VBox
    private lateinit var signout : Menu
    init{
        Timer().scheduleAtFixedRate(0, 1000) {
            Platform.runLater {
                controller.refresh()
                signout.text = controller.user.nickname
                myPosts.bindChildren(controller.user.posts.toObservable()){
                    PostView(it).root
                }
            }
        }
    }
    override val root = borderpane {
        prefHeight = 800.0
        prefWidth = 800.0
        top{
            hbox(alignment = Pos.CENTER_RIGHT) {
                menubar{
                   signout=menu(controller.user.nickname){
                        item("Log Out").action{
                            replaceWith<Login>(ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.DOWN))
                            resetEverything()
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
                        myPosts = vbox{}
                   }

               }
               item("Liked Posts"){

               }
               item("Commented Posts"){

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

                item ("Make Post"){
                    addChildIfPossible(MakePostView().root)
                }

                item("Find People"){}
            }
        }
    }

    private fun resetEverything() {
        controller.user = User("","")
        myPosts.children.clear()
    }

}
