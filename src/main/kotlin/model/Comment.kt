package model

import java.time.LocalDateTime

open class Comment(var id:Int = 0, var user: User = User("",""), var post: Post = Post(), var text:String = "",
                   var date: LocalDateTime = LocalDateTime.now())

