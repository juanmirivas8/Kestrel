package model

private const val INSERT = "INSERT INTO user (nickname, password) VALUES (?,?)"
private const val SELECTBYID = "SELECT id, nickname, password FROM user WHERE id = ?"
private const val SELECTBYNICKNAME = "SELECT id, nickname, password FROM user WHERE nickname = ?"
private const val SELECTVALIDATEUSER = "SELECT id, nickname, password FROM user WHERE nickname = ? AND password = ?"
private const val SELECTALLUSERS = "SELECT id, nickname, password FROM user"
private const val SELECTALLFOLLOWERS = "SELECT id, nickname, password FROM user WHERE id IN (SELECT user_id FROM follow WHERE follow_id = ?)"
private const val SELECTALLFOLLOWED = "SELECT id, nickname, password FROM user WHERE id IN (SELECT follow_id FROM follow WHERE user_id = ?)"
private const val INSERTFOLLOW = "INSERT INTO follow (user_id, follow_id) VALUES (?,?)"
private const val DELETEFOLLOW = "DELETE FROM follow WHERE user_id = ? AND follow_id = ?"

class UserDAO (user: User): User(nickname = user.nickname, password = user.password),CRUD {

    init {
        id = user.id
        followed = user.followed
        followers = user.followers
        likes = user.likes
        posts = user.posts
    }
    companion object{
        fun getUserById(id: Int): User {
            val params = listOf(id)
            val rs = executeQuery(SELECTBYID, params)
            return if (rs.next()) {
                return User(rs.getString("nickname"), rs.getString("password"))
            } else {
                User("","")
            }
        }

        fun getAllUsers(): List<User> {
            val rs = executeQuery(SELECTALLUSERS, listOf())
            val users = mutableListOf<User>()
            while (rs.next()) {
                users.add(User(rs.getString("nickname"), rs.getString("password")))
            }
            return users
        }
    }

    override fun create(): Boolean {
        val params = listOf(nickname, password)
        if(read(nickname)) return false
        this.id = executeUpdate(INSERT, params, true)
        return this.id != 0
    }

    override fun <String : Any> read(nickname:String): Boolean {
        val params = listOf(nickname)
        val rs = executeQuery(SELECTBYNICKNAME, params)
        if (rs.next()) {
            this.id = rs.getInt("id")
            this.nickname = rs.getString("nickname")
            this.password = rs.getString("password")
            return true
        }
        return false
    }

    fun <String : Any> validate(nickname:String , password:String): Boolean {
        val params = listOf(nickname,password)
        val rs = executeQuery(SELECTVALIDATEUSER, params)
        if (rs.next()) {
            this.id = rs.getInt("id")
            this.nickname = rs.getString("nickname")
            this.password = rs.getString("password")
            this.posts = PostDAO.getAllPostsOfUser(this.id)
            posts.forEach{
                it.user = this
            }
            getAllFollowers()
            getAllFollowed()
            return true
        }
        return false
    }

    fun getAllFollowers(){
        val params = listOf(id)
        val rs = executeQuery(SELECTALLFOLLOWERS, params)
        while (rs.next()) {
            followers.add(User(rs.getString("nickname"), rs.getString("password")))
        }
    }

    fun getAllFollowed(){
        val params = listOf(id)
        val rs = executeQuery(SELECTALLFOLLOWED, params)
        while (rs.next()) {
            followed.add(User(rs.getString("nickname"), rs.getString("password")))
        }
    }

    fun follow(user: User): Boolean {
        val params = listOf(id, user.id)
        if (executeUpdate(INSERTFOLLOW, params,true) != 0) {
            followed.add(user)
            return true
        }
        return false
    }

    fun unfollow(user: User): Boolean {
        val params = listOf(id, user.id)
        if (executeUpdate(DELETEFOLLOW, params,true) != 0) {
            followed.remove(user)
            return true
        }
        return false
    }

}