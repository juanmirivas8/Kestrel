package model

import utils.Storageable
import javax.persistence.*

@Entity
open class Post(
    @Column
    open var title:String,
    @Column
    open var content:String,
    @ManyToOne(cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    open var author:User,

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    open var id:Int? = null

):Storageable<Post> {

    constructor() : this("", "", User())

    override fun create(): Boolean {
        TODO("Not yet implemented")
    }

    override fun update(): Boolean {
        TODO("Not yet implemented")
    }

    override fun delete() {
        TODO("Not yet implemented")
    }

    override fun read(int: Int): Boolean {
        TODO("Not yet implemented")
    }
}