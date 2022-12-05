import model.User
import javax.persistence.Persistence
import kotlin.test.Test

class PersistenceTest {
    @Test
    fun test() {
        val u = User("test", "test")
        val managerFactory = Persistence.createEntityManagerFactory("MariaDB")
        val manager = managerFactory.createEntityManager()
        manager.transaction.begin()
        manager.persist(u)
        manager.transaction.commit()
        manager.close()
    }
}
