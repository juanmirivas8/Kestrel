package model

private const val SELECTALLPOSTSBYUSERID = "SELECT id, content, user_id, date, edited FROM post WHERE user_id = ?"
private const val INSERT = "INSERT INTO post (content, user_id, date) VALUES (?,?,?)"
class PostDAO(post: Post) :Post(),CRUD{
    init{
        id = post.id
        text = post.text
        user = post.user
        likes = post.likes
        comments = post.comments
        date = post.date
        edited = post.edited
    }
    companion object {
        fun getAllPostsOfUser(id: Int): MutableList<Post> {
            val params = listOf(id)
            val rs = executeQuery(SELECTALLPOSTSBYUSERID, params)
            val posts = mutableListOf<Post>()
            while (rs.next()) {
                val post = Post(rs.getInt("id"), rs.getString("content"), UserDAO(User("", "")),
                    mutableListOf(), mutableListOf(), rs.getTimestamp("date").toLocalDateTime(), rs.getBoolean("edited"))
                posts.add(post)
            }
            return posts
        }
    }

    override fun create(): Boolean {
        val params = listOf(text, user.id, date)
        this.id = executeUpdate(INSERT, params, true)
        user.posts.add(this)
        return this.id != 0
    }


}