package views

import controllers.Controller
import javafx.geometry.Pos
import model.UserDAO
import tornadofx.*

class SearchView : Fragment("Find People") {
    val controller = Controller
    override val root = vbox(alignment = Pos.CENTER) {
        fieldset {  }
        bindChildren( UserDAO.getAllUsers().toObservable()){
            FollowView(it).root
        }
    }
}
