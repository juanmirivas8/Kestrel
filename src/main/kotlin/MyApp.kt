import tornadofx.*
import views.Home
import views.Login
import views.MyStyles

class MyApp : App(Login::class, MyStyles::class){
    fun main(args: Array<String>) {
        launch<MyApp>(args)
    }
}