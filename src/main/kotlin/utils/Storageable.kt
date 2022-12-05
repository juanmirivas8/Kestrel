package utils

import javax.persistence.EntityManager
import javax.persistence.Persistence


private val managerFactory = Persistence.createEntityManagerFactory("MariaDB")
private var manager : EntityManager? = null


interface Storageable<T> {

    fun create(): Boolean{
        return try{
            val m = getManager()
            m.transaction.begin()
            m.persist(this)
            closeManager()
            true
        }catch(e: Throwable){
            e.printStackTrace()
            false
        }
    }

    fun delete(): Boolean{
        return try{
            val m = getManager()
            m.transaction.begin()
            m.remove(this)
            closeManager()
            true
        }catch(e: Throwable){
            //TODO: Log
            false
        }
    }

    fun update(): Boolean {
        return try {
            val m = getManager()
            m.transaction.begin()
            m.merge(this)
            closeManager()
            true
        } catch (e: Throwable) {
            false
        }
    }

    fun <T> read(id: Int): T{
        val m = getManager()
        val obj = m.find(this::class.java, id)
        closeManager()
        return obj as T
    }

    private fun getManager() = manager?:managerFactory.createEntityManager()

    private fun closeManager(){
        manager?.close()
        manager = null
    }
}

/*   COOL STUFF
 inline fun <reified T,K:JPACRUD<T>> K.read(id: Int):T?{
    return try{
        val mf = Persistence.createEntityManagerFactory("MariaDB")
        val m = mf.createEntityManager()
        m.transaction.begin()
        val obj = m.find(T::class.java, id)
        m.close()
        mf.close()
        obj
    }catch (e: Throwable){
        null
    }

 }*/
