package views


import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import tornadofx.*

class Login : View("My View") {

    override val root = vbox {
        addClass(MyStyles.mainLogin)
        title = "Login"

        lateinit var pf: PasswordField
        lateinit var uf: TextField
        label {
            addClass(MyStyles.loginLabel)
            text = "Kestrel"
        }
        form {
            fieldset(labelPosition = Orientation.HORIZONTAL) {
                field(orientation = Orientation.VERTICAL) {
                    label("Username")
                    uf=textfield()
                }
                field(orientation = Orientation.VERTICAL) {
                    label("Password")
                    pf = passwordfield()
                }
                hbox(alignment = Pos.CENTER, spacing = 10) {
                    button("Login") {
                        action {

                        }
                    }
                    button("Sign Up") {
                        action {

                        }
                    }
                }
            }
        }
    }
}
