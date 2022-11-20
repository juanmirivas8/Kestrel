package views

import controllers.Controller
import javafx.collections.ObservableList
import javafx.geometry.Pos
import model.User
import model.UserDAO
import tornadofx.*

class SearchView : Fragment("Find People") {
    private val controller = Controller
    private val ob = mutableListOf<User>().toObservable()

    override val root = vbox(alignment = Pos.CENTER) {
        val tf = textfield { promptText = "Search people" }
        scrollpane {
            vbox {
                bindChildren(ob){
                    FollowView(it).root
                }
                tf.textProperty().addListener { _, _, newValue ->
                    ob.clear()
                    ob.addAll(UserDAO.searchUser(newValue).filter { it.nickname != controller.user.nickname })
                    ob.remove(controller.user)
                }
            }
        }
    }
}
