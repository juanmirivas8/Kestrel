package controllers

import javafx.collections.ObservableList
import model.Post
import model.User
import tornadofx.toObservable

object Controller {
    var user: User = User("","")
    var observablePost : ObservableList<Post> = user.posts.toObservable()

    fun refresh(){
        user.posts.forEach {
            if (!observablePost.contains(it)) {
                observablePost.add(it)
            }
        }
    }
}
