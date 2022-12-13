package views


import controllers.Controller
import javafx.geometry.Orientation
import javafx.geometry.Pos
import javafx.scene.control.PasswordField
import javafx.scene.control.TextField
import model.User
import tornadofx.*
import utils.encryptSHA256
import utils.showPopUpError
import utils.showPopUpSuccess

class Login : View("Login") {

    override val root = vbox {
        addClass(MyStyles.mainLogin)
        title = "Login"

        lateinit var pf: PasswordField
        lateinit var uf: TextField
        val controller = Controller
        imageview("logo.png") {
            fitHeight = 300.0
            fitWidth = 400.0
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

                            when{
                                uf.text.isEmpty()||pf.text.isEmpty() -> showPopUpError("Error", "Incomplete fields", "Please fill all the fields")
                                user.validate() -> {
                                    showPopUpSuccess("Success", "Login successful", "Welcome ${user.username}")
                                    controller.user = user
                                    replaceWith<Home>( ViewTransition.Slide(0.3.seconds, ViewTransition.Direction.LEFT))
                                }
                                else -> {
                                    showPopUpError("Error", "Login failed", "Wrong username or password")
                                    uf.clear()
                                    pf.clear()
                                }
                            }
                        }
                    }
                    button("Sign Up") {
                        action {
                           val user = User(uf.text, String.encryptSHA256(pf.text))
                            if(user.create()){
                                showPopUpSuccess("Success", "User created", "User created successfully")
                                uf.text = ""
                                pf.text = ""
                            }else{
                                showPopUpError("Error", "User already exists", "Please try again")
                                uf.text = ""
                                pf.text = ""
                            }
                        }
                    }
                }
            }
        }
    }
}
