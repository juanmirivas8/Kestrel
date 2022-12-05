package model

import utils.Storageable
import java.io.Serializable
import javax.persistence.*

@Entity
open class User(
    @Column(unique = true)
    val username: String,
    @Column
    val password: String,
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id : Int? = null
) : Storageable<User>,Serializable {
    constructor() : this("", "")

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is User) return false

        if (username != other.username) return false

        return true
    }

    override fun hashCode(): Int {
        return username.hashCode()
    }


}