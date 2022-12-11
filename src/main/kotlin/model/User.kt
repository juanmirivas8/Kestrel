package model

import utils.Storageable
import utils.closeManager
import utils.getManager
import java.util.logging.Logger
import javax.persistence.*
import javax.transaction.Transactional

@Entity
open class User(
    @Column(unique = true)
    open var username: String,
    @Column
    open var password: String,
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id : Int? = null
) : Storageable<User>{
    constructor() : this("", "")

    @ManyToMany(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    @JoinTable(name = "Follows", joinColumns = [JoinColumn(name = "follower")], inverseJoinColumns = [JoinColumn(name = "followed")])
    open var following = mutableSetOf<User>()
    @ManyToMany(mappedBy = "following", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    open var followers = mutableSetOf<User>()
    override fun create(): Boolean {
        return try{
            val manager = getManager()
            manager.transaction.begin()
            manager.persist(this)
            manager.flush()
            manager.transaction.commit()
            closeManager()
            true
        }catch (e:Throwable){
            false
        }
    }

    override fun update(): Boolean {
        return try{
            val manager = getManager()
            manager.transaction.begin()
           val u = manager.merge(this)
            manager.persist(u)
            manager.flush()
            manager.transaction.commit()
            closeManager()
            true
        }catch (e:Throwable){
            e.printStackTrace()
            false
        }
    }

    override fun delete() {
        val manager = getManager()
        manager.transaction.begin()
        val u = manager.find(User::class.java, id)
        manager.remove(u)
        manager.flush()
        manager.transaction.commit()
        closeManager()
        id = null
    }

    override fun read(int :Int): Boolean {
        this.apply {
           val u = getManager().find(User::class.java, int)
            u?.let {
                username = u.username
                password = u.password
                id = u.id
                following = u.following
                followers = u.followers
            }
            closeManager()
            return u != null
        }
    }

    fun readUser(int :Int): User {
        this.apply {
            val u = getManager().find(User::class.java, int)
            closeManager()
            return u
        }
    }

    fun follow(user: User): Boolean {
        if (user.id == id || following.contains(user)) return false
        following.add(user)
        user.followers.add(this)
        return update()
    }

    fun unfollow(user: User){
        if (user.id == id || !following.contains(user)) return
        val b1 = user.followers.remove(this)
        val b = following.remove(user)
        update()
        user.update()
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