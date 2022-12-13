package views

import controllers.Controller
import model.User
import tornadofx.*

class FollowView(var user: User) : Fragment("My View") {

    private var controller = Controller
    init {
        controller = Controller
        controller.user.update()
    }
    override val root = hbox {
        style{
            spacing = 10.px
        }
        button {
            text = if(controller.user.following.contains(user)) {
                "Unfollow"
            } else {
                "Follow"
            }
            action {
                if(controller.user.following.contains(user)) {
                    controller.user.unfollow(user)
                    text = "Follow"
                } else {
                    controller.user.follow(user)
                    text = "Unfollow"
                }
            }
        }
        label{
            text = user.username
        }
    }
}
