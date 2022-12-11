
import model.User
import org.junit.jupiter.api.assertDoesNotThrow
import utils.getLogger
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse


class PersistenceTest {
    private val l = getLogger()
    @Test
    fun createReadTest() {
        assertDoesNotThrow {
            val u = User("test", "test")
            u.create()

            val u2 = User()
            u2.read(u.id!!)

            println(u)
            println(u2)
            assertEquals(u, u2)
        }
    }

    @Test
    fun createTwoTimes(){
        assertDoesNotThrow {
            val u = User("test", "test")
            val u2 = User("test", "test")
            assert(u.create())
            assertFalse(u2.create())
        }
    }

    @Test
    fun deleteTest(){
        val u = User("test", "test")
        u.create()
        println(u)
        val id = u.id!!
        u.delete()
        assertFalse(User().read(id))
    }

    @Test
    fun readTest(){
        val u = User("test", "test")
        u.create()

        val u2 = User()
        assert(u2.read(u.id!!))
        assertEquals(u2,u)

    }

    @Test
    fun updateTest(){
        val joao = User("joao", "123")
        joao.create()

        val fakeJoao = User()
        fakeJoao.read(joao.id!!)

        println(joao.following)
        println(fakeJoao.following)

        val maria = User("maria", "123")
        maria.create()
        joao.follow(maria)

        println(joao.following)
        println(fakeJoao.following)
    }

}
