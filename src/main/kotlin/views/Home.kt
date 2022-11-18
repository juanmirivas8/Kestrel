package views

import controllers.Controller
import javafx.geometry.Orientation
import model.User
import tornadofx.*

class Home : View("Home") {
    override val root = borderpane {
        val controller : Controller = Controller
        top{
            hbox {

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
    }
}
