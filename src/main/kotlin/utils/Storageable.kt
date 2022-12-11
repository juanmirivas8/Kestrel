package utils

import java.io.Serializable
import javax.persistence.EntityManager
import javax.persistence.Persistence


private val managerFactory = Persistence.createEntityManagerFactory("MariaDB")
private var manager : EntityManager? = null

fun <T:Storageable<T>> T.closeManager() {
    manager?.close()
    manager = null
}
fun <T:Storageable<T>> T.getManager()  = manager?:managerFactory.createEntityManager()

interface Storageable<T>: Serializable{
    fun create():Boolean
    fun update():Boolean
    fun delete()
    fun read(int: Int):Boolean
}
