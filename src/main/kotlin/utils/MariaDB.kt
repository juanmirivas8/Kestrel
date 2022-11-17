package utils

import java.sql.Connection
import java.sql.DriverManager
import java.sql.PreparedStatement
import java.sql.ResultSet


private val credentials = unmarshall("src/main/resources/dbCredentials.xml",DBCredentials::class.java)
private var connection: Connection? = null

private fun connect() {
    Class.forName("org.mariadb.jdbc.Driver")
    connection = DriverManager.getConnection("${credentials.host}${credentials.port}/${credentials.db}", credentials.user, credentials.password)
}

private fun disconnect() {
    connection?.close()
}

private fun prepareQuery(query: String, params : List<Any>): PreparedStatement{
    connection?: connect()
    val preparedStatement = connection?.prepareStatement(query)
    for ((i, obj) in params.withIndex()) preparedStatement?.setObject(i,obj)
    return preparedStatement!!
}

fun executeQuery(query : String, params: List<Any>):ResultSet{
    val preparedStatement = prepareQuery(query, params)
    val rs = preparedStatement.executeQuery()
    disconnect()
    return rs
}


