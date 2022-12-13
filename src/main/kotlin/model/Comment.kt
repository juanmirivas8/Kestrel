package model

import utils.Storageable
import java.time.LocalDateTime
import javax.persistence.*
import kotlin.jvm.Transient

@Entity
open class Comment(
    @Column
    open var content: String,
    @ManyToOne(fetch = FetchType.LAZY)
    open var user: User,
    @ManyToOne(fetch = FetchType.LAZY)
    open var post: Post,
    @Column
    open var date: java.time.LocalDateTime,
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override var id: Int? = null


):Storageable<Comment> {
    override fun merge() {
        TODO("Not yet implemented")
    }

    fun delete() {
        TODO("Not yet implemented")
    }

    @Transient
    override var buffer: Comment = this

    constructor(): this("",User(),Post(), LocalDateTime.now())
}