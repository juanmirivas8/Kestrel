package model

private const val SELECTALLCOMMENTEDPOSTBYUSER = "SELECT id, content, user_id, date, edited FROM post WHERE id IN (SELECT post_id FROM comment WHERE comment.user_id = ?)"
private const val SELECTALLPOSTSBYUSERID = "SELECT id, content, user_id, date, edited FROM post WHERE user_id = ?"
private const val INSERT = "INSERT INTO post (content, user_id, date) VALUES (?,?,?)"
private const val UPDATE = "UPDATE post SET content = ?, edited = ? WHERE id = ?"
private const val DELETE = "DELETE FROM post WHERE id = ?"
private const val GETFEED = "SELECT id, content, user_id, date, edited FROM post WHERE user_id IN (SELECT follow_id FROM follow WHERE user_id = ?) ORDER BY date DESC"
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
                post.comments = CommentDAO.getAllCommentsOfPost(post.id)
                post.comments.forEach { it.post = post }
                posts.add(post)
            }
            return posts
        }

        fun getAllCommentedPostsOfUser(id: Int): MutableList<Post> {
            val params = listOf(id)
            val rs = executeQuery(SELECTALLCOMMENTEDPOSTBYUSER, params)
            val posts = mutableListOf<Post>()
            while (rs.next()) {
                val post = Post(rs.getInt("id"), rs.getString("content"), UserDAO.getUserById(rs.getInt("user_id")),
                    mutableListOf(), mutableListOf(), rs.getTimestamp("date").toLocalDateTime(), rs.getBoolean("edited"))
                post.comments = CommentDAO.getAllCommentsOfPost(post.id)
                post.comments.forEach { it.post = post }
                posts.add(post)
            }
            return posts
        }

        fun getFeedOfUser(id: Int): MutableList<Post> {
            val params = listOf(id)
            val rs = executeQuery(GETFEED, params)
            val posts = mutableListOf<Post>()
            while (rs.next()) {
                val post = Post(rs.getInt("id"), rs.getString("content"), UserDAO.getUserById(rs.getInt("user_id")),
                    mutableListOf(), mutableListOf(), rs.getTimestamp("date").toLocalDateTime(), rs.getBoolean("edited"))
                post.comments = CommentDAO.getAllCommentsOfPost(post.id)
                post.comments.forEach { it.post = post }
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

    override fun update(): Boolean {
        val params = listOf(text, edited, id)
        return executeUpdate(UPDATE, params,false) != 0
    }

    override fun delete(): Boolean {
        val params = listOf(id)
        return executeUpdate(DELETE, params,false) != 0
    }

}