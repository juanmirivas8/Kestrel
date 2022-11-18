package model
open class User(var nickname: String, var password: String){
    var id: Int = -1
    var followed = mutableListOf<User>()
    var followers = mutableListOf<User>()
    var likes = mutableListOf<Post>()
    var posts = mutableListOf<Post>()
    var comments = mutableListOf<Comment>()

    operator fun plus(comment : Comment) {

        TODO("Add comment to user and db")
    }

    operator fun plus(post : Post) {

        TODO("Add post to user and db")
    }

    operator fun plus(like : Like) {
        TODO("Add like to user and db")
    }

    operator fun minus(like : Like) {
        TODO("Remove like from user and db")
    }

    operator fun minus(comment : Comment) {
        TODO("Remove comment from user and db")
    }

    operator fun minus(post : Post) {
        TODO("Remove post from user and db")
    }

}
