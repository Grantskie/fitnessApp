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

    var routineInsert = "INSERT INTO routine (username, routineName, exercise, amount, day) VALUES "
    val dayArray = Array("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday")
    val routineList = List.fill(7)(ArrayBuffer[Tuple2[String,String]]())
	var exerciseStr = ""
	

    def displayRoutine(){
		Aesthetics.printBorderHorz(1)
		Aesthetics.printBorderVert(s"Routine: $routineName")
		Aesthetics.printBorderHorz(1)
		for(i<-0 to 6){
			routineList(i).foreach(arg=>exerciseStr += arg._1 + "->" + arg._2 + "\t")
			var dayColStr = (i+1)+") "+dayArray(i)
			println(Aesthetics.getDayCol(dayColStr)  + " " + exerciseStr)
			exerciseStr = ""
			Aesthetics.printBorderHorz(1)
		}
	}
//| 4) Wednesday |
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
		val tupleInput = Tuple2(exerciseInput, amountInput)
		routineList(dayNumber).append(tupleInput)
        routineInsert += s"(\'$username\', \'$routineName\', \'$exerciseInput\', \'$amountInput\', \'$day\'),"
    }

	def insertRoutine(stmt:Statement){
		routineInsert = routineInsert.substring(0,routineInsert.length()-1)
		println(routineInsert)
		stmt.executeUpdate(routineInsert)
	}

	def generateRoutineData(stmt:Statement){
		var selectQuery = s"SELECT day, exercise, amount FROM routine WHERE username = \'$username\' AND routineName = \'$routineName\'"
		val rs = stmt.executeQuery(selectQuery)
		var insertTuple = ("", "")
		while(rs.next()){
			rs.getString("day") match{
				case "Sunday" => {
					insertTuple = (rs.getString("exercise"), rs.getString("amount"))
					routineList(0).append(insertTuple)
				}
				case "Monday" => {
					insertTuple = (rs.getString("exercise"), rs.getString("amount"))
					routineList(1).append(insertTuple)
				}
				case "Tuesday" => {
					insertTuple = (rs.getString("exercise"), rs.getString("amount"))
					routineList(2).append(insertTuple)
				}
				case "Wednesday" => {
					insertTuple = (rs.getString("exercise"), rs.getString("amount"))
					routineList(3).append(insertTuple)
				}
				case "Thursday" => {
					insertTuple = (rs.getString("exercise"), rs.getString("amount"))
					routineList(4).append(insertTuple)
				}
				case "Friday" => {
					insertTuple = (rs.getString("exercise"), rs.getString("amount"))
					routineList(5).append(insertTuple)
				}
				case "Saturday" => {
					insertTuple = (rs.getString("exercise"), rs.getString("amount"))
					routineList(6).append(insertTuple)
				}
			}
		}
	}

	def getRoutineName(): String ={
		return routineName
	}

	def getExerciseSet(): Set[(String,String)]={
		var returnSet:Set[(String,String)] = Set()
		for(i<-0 to 6){
			routineList(i).foreach(arg=>returnSet = returnSet ++ (Set(arg)))
		}
		return returnSet
	}

	def getRoutineId(exercise:String, amount:String): Int = {
		var routineId = 0
		var selectId = s"SELECT routineId FROM routine WHERE username = \'$username\' AND routineName = \'$routineName\' AND exercise = \'$exercise\' AND amount = \'$amount\' ORDER BY routineId DESC"
		val rs = stmt.executeQuery(selectId)
		rs.next()
		routineId = rs.getInt("routineId")
		return routineId
	}

}