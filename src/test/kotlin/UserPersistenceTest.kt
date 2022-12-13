import model.User
import kotlin.test.Test
import kotlin.test.assertFalse

class PersistenceTest {

    @Test
    fun createTest(){
        val u = User(username = "Paco","Paco")
        assert(u.create())

        val u2 = User("Paco","Paco")
        assertFalse(u2.create())
    }

    @Test
    fun followTest(){
        val u = User("Paco","Paco")
        u.create()
        val u2 = User("Paco2","Paco2")
        u2.create()
        u.follow(u2)
        u2.follow(u)
    }

    @Test
    fun unfollowTest(){
        val u = User("Paco","Paco")
        u.create()
        val u2 = User("Paco2","Paco2")
        u2.create()
        u.follow(u2)
        u2.follow(u)

        u.unfollow(u2)
        u2.unfollow(u)
    }

    @Test
    fun deleteTest(){
        val u = User("Paco","Paco")
        u.create()
        val u2 = User("Maria","Paco")
        u2.create()

        u.follow(u2)
        u2.update()
        println(u2.followers)

        u.delete()

        u2.update()
        println(u2.followers)
    }
}