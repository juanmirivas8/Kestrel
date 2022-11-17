package utils

import jakarta.xml.bind.annotation.XmlAccessType
import jakarta.xml.bind.annotation.XmlAccessorType
import jakarta.xml.bind.annotation.XmlRootElement

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
data class DBCredentials(val db: String, val user:String, val password: String, val host: String, val port: String){
    constructor() : this("","","","","")
}
