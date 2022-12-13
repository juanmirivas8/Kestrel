package model

import utils.Storageable
import utils.insideContext
import utils.manager
import java.time.LocalDateTime
import javax.persistence.*
import kotlin.jvm.Transient

@Entity
open class Post(
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    open var user: User = User(),
    @Column
    open var content: String = "",
    @Column
    open var date : LocalDateTime = LocalDateTime.now(),
    @Column
    open var edited : Boolean = false,
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override var id: Int? = null

) : Storageable<Post> {

    @OneToMany(mappedBy = "post", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    open var comments = mutableListOf<Comment>()
    @Transient
    final override var buffer: Post = this
    constructor() : this(content = "")

    override fun merge() {
        user = buffer.user
        content = buffer.content
        id = buffer.id
        date = buffer.date
        edited = buffer.edited
    }

    fun update(text : String){
        insideContext {
            buffer.content = text
            buffer.edited = true
            buffer.date = LocalDateTime.now()
        }
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
}