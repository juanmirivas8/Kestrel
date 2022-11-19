package views

import model.User
import tornadofx.*

class FollowView(val user: User) : Fragment("My View") {
    override val root = hbox {
        label{user.nickname}

        button("Follow") {
            action {

            }
        }
    }
}
