package utils

import java.io.Serializable
import javax.persistence.EntityManager
import javax.persistence.Persistence


private val managerFactory = Persistence.createEntityManagerFactory("MariaDB")
var manager : EntityManager? = null

fun <T:Storageable<T>> T.closeManager() {
    manager?.close()
    manager = null
}
fun <T:Storageable<T>> T.getManager(): EntityManager{
    manager = manager?:managerFactory.createEntityManager()
    return manager!!
}

fun <T:Storageable<T>> T.insideContext(exec : () ->Unit){
    try{
        getManager().transaction.begin()
        if (id != null) buffer = getManager().find(buffer.javaClass, id)
        exec()
        getManager().flush()
        getManager().transaction.commit()
        closeManager()
        merge()
    }catch (e: Throwable){
        manager?.transaction?.rollback()
        closeManager()
        throw e
    }
}
interface Storageable<T>: Serializable{
    fun merge()

    var buffer : T

    var id : Int?
}
