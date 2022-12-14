package model

import utils.Storageable
import utils.insideContext
import utils.manager
import java.time.LocalDateTime
import javax.persistence.*
import kotlin.jvm.Transient

@Entity
open class Comment(
    @Column
    open var content: String,
    @ManyToOne(fetch = FetchType.EAGER)
    open var user: User,
    @ManyToOne(fetch = FetchType.EAGER)
    open var post: Post,
    @Column
    open var date: LocalDateTime,
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override var id: Int? = null


):Storageable<Comment> {
    override fun merge() {
        content = buffer.content
        user = buffer.user
        post = buffer.post
        date = buffer.date
        id = buffer.id
    }

    fun create(): Boolean {
        return try{
            insideContext {
                buffer = this
                manager?.persist(buffer)
            }
            true
        }catch (e: Throwable){
            false
        }
    }

    fun delete() {
        insideContext {
            manager?.remove(buffer)
        }
    }


    @Transient
    override var buffer: Comment = this

    constructor(): this("",User(),Post(), LocalDateTime.now())
}