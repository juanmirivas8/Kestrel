package model

import utils.*
import java.sql.SQLIntegrityConstraintViolationException
import java.util.logging.Logger
import javax.persistence.*
import javax.transaction.Transactional
import kotlin.jvm.Transient

@Entity
open class User(
    @Column(unique = true)
    open var username: String,
    @Column
    open var password: String,
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override var id : Int? = null
) : Storageable<User>{
    constructor() : this("", "")

    @ManyToMany(cascade = [CascadeType.PERSIST,CascadeType.MERGE], fetch = FetchType.LAZY)
    @JoinTable(name = "Follows", joinColumns = [JoinColumn(name = "follower")], inverseJoinColumns = [JoinColumn(name = "followed")])
    open var following = mutableSetOf<User>()
    @ManyToMany(mappedBy = "following", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    open var followers = mutableSetOf<User>()
    @OneToMany(mappedBy = "user", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    open var posts = mutableListOf<Post>()
    @Transient
    final override var buffer: User = this

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

    fun update() {
        insideContext {
            buffer.followers.size
            buffer.following.size
            buffer.posts.forEach { it.comments.size }
        }
    }

    override fun merge() {
        this.apply {
            username = buffer.username
            password = buffer.password
            id = buffer.id
            following = buffer.following
            followers = buffer.followers
            posts = buffer.posts
        }
    }

    fun follow(user: User){
        insideContext { buffer.apply {
            val followed = getManager().find(User::class.java, user.id)

            if(id == followed.id ||following.contains(followed)) return@apply
            following.add(followed)
            followed.followers.add(this)
        } }
    }

    fun unfollow(user: User){
        if (user.id == id || !following.contains(user)) return
        insideContext { buffer.apply {
            val followed = following.first{it.id == user.id}
            following.remove(followed)
            followed.followers.remove(this)
        } }
    }

    fun getFeed(): List<Post>{
        val u = mutableListOf<Post>()
        insideContext {
           buffer.following.forEach {
                it.posts.forEach{
                    it.comments.size
                    u.add(it)
                }
            }
            buffer.posts.forEach{
                it.comments.size
                u.add(it)
            }

        }
        return u
    }

    fun searchLike(text: String): List<User>{
        var u: List<User> = listOf()
        insideContext {
            u = manager?.createQuery("SELECT u FROM User u WHERE u.username LIKE :text", User::class.java)
                ?.setParameter("text", "%$text%")?.resultList ?: listOf()
        }
        return u
    }

    fun validate(): Boolean {
        var res = false
       insideContext {
            val q = manager?.createQuery("SELECT u FROM User u WHERE u.username = :id AND u.password = :pw", User::class.java)
            q?.setParameter("id", username)
            q?.setParameter("pw", password)

           res = try{
               buffer = q?.singleResult!!
               true
           }catch (e: Throwable){
               false
           }
        }
        return res
    }



    override fun toString(): String {
        return "User(username='$username', password='$password', id=$id)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false

        if (username != other.username) return false
        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        var result = username.hashCode()
        result = 31 * result + (id ?: 0)
        return result
    }



}