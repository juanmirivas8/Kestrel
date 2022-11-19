package model

import utils.DBCredentials
import utils.unmarshall
import java.sql.*

private val credentials = unmarshall("src/main/resources/dbCredentials.xml", DBCredentials::class.java)
private var connection: Connection? = null

interface CRUD {
    fun create(): Boolean = false
    fun <T:Any>read(id: T):Boolean = false
    fun update(): Boolean = false
    fun delete(): Boolean = false

}

private fun connect() {
    Class.forName("org.mariadb.jdbc.Driver")
    connection = DriverManager.getConnection("${credentials.host}${credentials.port}/${credentials.db}", credentials.user, credentials.password)
}

private fun disconnect() {
    connection?.close()
    connection = null
}

private fun prepareQuery(query: String, params : List<Any>): PreparedStatement {
    connection?: connect()
    val preparedStatement = connection?.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)
    for ((i, obj) in params.withIndex()) preparedStatement?.setObject(i+1,obj)
    return preparedStatement!!
}

fun executeQuery(query : String, params: List<Any>): ResultSet {
    val preparedStatement = prepareQuery(query, params)
    val rs = preparedStatement.executeQuery()
    disconnect()
    return rs
}

fun executeUpdate(query : String, params: List<Any>,insert : Boolean):Int{
    val preparedStatement = prepareQuery(query, params)
    var rs = preparedStatement.executeUpdate()
    val set = preparedStatement.generatedKeys
    if(insert && set != null && set.next()){
        rs = set.getInt(1)
    }
    disconnect()
    return rs
}