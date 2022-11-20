package model

import java.time.LocalDateTime

open class Post(var id:Int = 0,var text:String = "",var user:User = User("",""),
           var likes:MutableList<User> = mutableListOf(),var comments:MutableList<Comment> = mutableListOf(),
        var date:LocalDateTime = LocalDateTime.now(),var edited:Boolean = false){

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Post) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id
    }
}







