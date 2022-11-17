package views


import controllers.Controller
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import model.User
import model.UserDAO
import tornadofx.*
import utils.encryptSHA256
import utils.showPopUp

class Login : View("My View") {

    override val root = vbox {
        addClass(MyStyles.mainLogin)
        title = "Login"

        lateinit var pf: PasswordField
        lateinit var uf: TextField
        val controller : Controller = Controller
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
                            val user = User(uf.text, String.encryptSHA256(pf.text))
                            val userDAO = UserDAO(user)
                            when{
                                userDAO.validate(user.nickname, user.password)->{controller.user = userDAO}
                                uf.text.isNullOrBlank()|| pf.text.isNullOrBlank() -> {
                                    showPopUp("Error", "Empty fields", "Please fill all the fields")
                                }
                                else -> {
                                    showPopUp("Error", "Wrong credentials", "Please check your credentials")
                                }
                            }
                        }
                    }
                    button("Sign Up") {
                        action {
                           val user = User(uf.text, String.encryptSHA256(pf.text))
                            if(UserDAO(user).create()){

                            }else{

                            }
                        }
                    }
                }
            }
        }
    }
}
