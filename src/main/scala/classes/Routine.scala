package classes

import scala.collection.mutable.ArrayBuffer
import scala.io.StdIn.readLine
import scala.io.StdIn.readInt
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement
import java.sql.ResultSet


class Routine(username: String, routineName: String, stmt:Statement){

    var routineInsert = "INSERT INTO routine (\'username\', \'routineName\', \'exercise\', \'amount\', \'day\') VALUES "
    val dayArray = Array("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
    val routineArray = new ArrayBuffer[ArrayBuffer[String]]

    def displayRoutine(){
		var selectQuery = s"SELECT * FROM routine WHERE username = \'$username\' AND routineName = \'$routineName\'"
		println(selectQuery)
		val rs = stmt.executeQuery(selectQuery)
		if (rs.next() == false){
			Aesthetics.printBorderHorz(1)
			Aesthetics.printBorderVert(s"Routine: $routineName")
			Aesthetics.printBorderHorz(1)
			println("| 1) Sunday   |")
			Aesthetics.printBorderHorz(1)
			println("| 2) Monday   |")
			Aesthetics.printBorderHorz(1)
			println("| 3) Tuesday  |")
			Aesthetics.printBorderHorz(1)
			println("| 4) Wednesday|")
			Aesthetics.printBorderHorz(1)
			println("| 5) Thursday |")
			Aesthetics.printBorderHorz(1)
			println("| 6) Friday   |")
			Aesthetics.printBorderHorz(1)
			println("| 7) Saturday |")
			Aesthetics.printBorderHorz(1)
		}
		else{
			do{
				print(rs.getString("exercise"))
				print(rs.getString("amount"))
				print(rs.getString("day"))
			}while(rs.next())
		}
	}

    def addDay(dayNum:String){
        val dayNumber = dayNum.toInt - 1
        val day = dayArray(dayNumber)
        Aesthetics.printHeader(day)
        Aesthetics.printBorderVert("Enter exercise")
        Aesthetics.printBorderHorz(1)
        println(">Input<")
        val exerciseInput = readLine()
        Aesthetics.printHeader("Enter amount (Distance, duration, sets/reps)")
        println(">Input<")
        val amountInput = readLine()
        

        
        routineInsert += s"(\'$username\', \'$routineName\', \'$exerciseInput\', \'$amountInput\', \'$day\'),"
        println(routineInsert)
        
    }
}