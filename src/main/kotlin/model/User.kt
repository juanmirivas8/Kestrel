package model
open class User(var nickname: String, var password: String){
    var id: Int = -1
    var followed = mutableListOf<User>()
    var followers = mutableListOf<User>()
    var likes = mutableListOf<Post>()
    var posts = mutableListOf<Post>()
    var comments = mutableListOf<Post>()


    operator fun plusAssign(post : Post) {
       PostDAO(post).create()
    }

    operator fun minusAssign(post : Post) {
        if(PostDAO(post).delete()) posts.remove(post)
    }
}
