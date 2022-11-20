package model

private const val INSERT = "INSERT INTO comment (user_id, post_id, content) VALUES (?, ?, ?)"
private const val DELETE = "DELETE FROM comment WHERE id = ?"
private const val SELECTALLCOMMENTSBYPOSTID = "SELECT id, user_id, post_id, content,date FROM comment WHERE post_id = ?"
class CommentDAO(comment:Comment) : Comment(),CRUD{

    init{
        id = comment.id
        user = comment.user
        post = comment.post
        text = comment.text
        date = comment.date
    }
    companion object{
        fun getAllCommentsOfPost(id: Int): MutableList<Comment> {
            val params = listOf(id)
            val rs = executeQuery(SELECTALLCOMMENTSBYPOSTID, params)
            val comments = mutableListOf<Comment>()
            while (rs.next()) {
                val comment = Comment(rs.getInt("id"),UserDAO.getUserById(rs.getInt("user_id")),
                    Post(), rs.getString("content"),
                    rs.getTimestamp("date").toLocalDateTime())
                comments.add(comment)
            }
            return comments
        }
    }
    override fun create():Boolean{
        val params = listOf(user.id, post.id, text)
        this.id = executeUpdate(INSERT, params, true)
        return this.id != 0
    }

    override fun delete():Boolean{
        val params = listOf(id)
        return executeUpdate(DELETE, params,false) != 0
    }


}