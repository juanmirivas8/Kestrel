package model

private const val INSERT = "INSERT INTO user (nickname, password) VALUES (?,?)"
private const val SELECTBYID = "SELECT id, nickname, password FROM user WHERE id = ?"
private const val SELECTBYNICKNAME = "SELECT id, nickname, password FROM user WHERE nickname = ?"
private const val SELECTVALIDATEUSER = "SELECT id, nickname, password FROM user WHERE nickname = ? AND password = ?"
class UserDAO (user: User): User(nickname = user.nickname, password = user.password),CRUD {

    init {
        id = user.id
        followed = user.followed
        followers = user.followers
        likes = user.likes
        posts = user.posts
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
            return true
        }
        return false
    }



}