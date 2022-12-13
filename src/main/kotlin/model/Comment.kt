package model

import utils.Storageable
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
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override var id: Int? = null


):Storageable<Comment> {
    override fun merge() {
        TODO("Not yet implemented")
    }

    @Transient
    override var buffer: Comment = this

    constructor(): this("",User(),Post(),null)
}