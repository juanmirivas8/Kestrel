package model

import utils.Storageable
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
    }
}