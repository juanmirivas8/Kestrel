package views

import controllers.Controller
import model.User
import model.UserDAO
import tornadofx.*

class FollowView(val user: User) : Fragment("My View") {
    private val controller = Controller
    override val root = hbox {
        style{
            spacing = 10.px
        }
        button {
            text = if(controller.user.followed.contains(user)) {
                "Unfollow"
            } else {
                "Follow"
            }
            action {
                val u = UserDAO(controller.user)
                if(controller.user.followed.contains(user)) {
                    u.unfollow(user)
                    text = "Follow"
                } else {
                    u.follow(user)
                    text = "Unfollow"
                }
            }
        }
        label{
            text = user.nickname
        }
    }
}
