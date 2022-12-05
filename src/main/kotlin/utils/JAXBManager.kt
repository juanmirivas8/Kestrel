package utils

import jakarta.xml.bind.JAXBContext
import jakarta.xml.bind.Marshaller
import java.io.FileReader
import java.io.FileWriter

fun <T> unmarshall(xml: String, clazz: Class<T>): T = JAXBContext.newInstance(clazz).
    createUnmarshaller().unmarshal(FileReader(xml)) as T

fun <T:Any> marshall(xml : String, obj :T){
   val m = JAXBContext.newInstance(obj::class.java).createMarshaller()
    m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true)
    m.marshal(obj, FileWriter(xml))
}

