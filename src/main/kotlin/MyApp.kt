import tornadofx.*

class MyApp : App(Login::class) {
    fun main(args: Array<String>) {
        launch<MyApp>(args)
    }
}