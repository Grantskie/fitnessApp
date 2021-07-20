package classes

import scala.collection.mutable.ArrayBuffer
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement
import java.sql.ResultSet

class User(username: String, stmt:Statement){

    val routines = 1
    val routineArray = generateRoutines()

    def generateRoutines(): Array[String] = {
        val routineBufArray = ArrayBuffer[String]()
        val selectStr = s"SELECT DISTINCT routineName FROM routine WHERE username = \'$username\'"
        val rs:ResultSet = stmt.executeQuery(selectStr)
        while(rs.next()){
            routineBufArray.append(rs.getString("routineName"))
        }
        val routineArrayRes:Array[String] = new Array[String](routineBufArray.size)
        for(i<-0 to (routineBufArray.size - 1)){
            routineArrayRes(i) = routineBufArray(i)
        }
        return routineArrayRes
    }

    def getRoutines(): Array[String] = {
        return routineArray
    }
}