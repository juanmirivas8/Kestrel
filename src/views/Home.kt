package views

import controllers.Controller
import javafx.application.Platform
import javafx.geometry.Pos
import javafx.geometry.Side
import javafx.scene.control.Label
import javafx.scene.control.Menu
import javafx.scene.layout.VBox
import javafx.scene.text.FontWeight
import model.PostDAO

import model.User
import model.UserDAO
import tornadofx.*
import java.util.*
import kotlin.concurrent.scheduleAtFixedRate

class Home : View("Home") {
    private val controller : Controller = Controller
    private lateinit var myPosts :VBox
    private lateinit var myfollowers :VBox
    private lateinit var myfollowed :VBox
    private lateinit var signout : Menu
    private lateinit var myCommented :VBox
    private lateinit var feed :VBox
    init{
        Timer().scheduleAtFixedRate(0, 1000) {
            Platform.runLater {
                signout.text = controller.user.nickname
                myPosts.bindChildren(controller.user.posts.toObservable()){
                    PostView(it).root
                }
                myfollowed.bindChildren(controller.user.followed.toObservable()){
                    FollowView(it).root
                }
                myfollowers.bindChildren(controller.user.followers.toObservable()){
                    FollowView(it).root
                }
                myCommented.bindChildren(controller.user.comments.toObservable()){
                    PostView(it).root
                }
                feed.bindChildren(PostDAO.getFeedOfUser(controller.user.id).toObservable()){
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
               item("Feed"){
                   scrollpane {
                       isFitToWidth = true
                       feed = vbox{}
                   }
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
                   scrollpane {
                       isFitToWidth = true
                       myCommented = vbox{}
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
                    scrollpane {
                        isFitToWidth = true
                       myfollowers = vbox()
                    }

                }
                item("Following"){
                    scrollpane {
                        isFitToWidth = true
                        myfollowed = vbox()
                        }
                    }

                item ("Make Post"){
                    addChildIfPossible(MakePostView().root)
                }

                item("Find People"){
                    addChildIfPossible(SearchView().root)
                }
            }
        }
    }

    private fun resetEverything() {
        controller.user = User("","")
        myPosts.children.clear()
        myfollowers.children.clear()
        myfollowed.children.clear()
        myCommented.children.clear()
    }

}
