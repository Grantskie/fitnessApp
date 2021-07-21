import scala.io.StdIn.readLine
import scala.io.StdIn.readInt
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement
import java.sql.ResultSet
import classes.User
import classes.Routine
import classes.Aesthetics
import scala.collection.mutable.ArrayBuffer

object fitnessApp {
	

	def getConnect() :Connection= {
		val url = "jdbc:mysql://localhost:3306/fitnessapp"
 		val driver = "com.mysql.cj.jdbc.Driver"
  		val username = "scalaFitnessApp"
  		val password = "ScalaFitness1!"
		Class.forName(driver)
  		var connection:Connection = DriverManager.getConnection(url, username, password)
		
		return connection
	}

	
	
	
	def openingScreen(){
		Aesthetics.printBorderHorz(1)
		Aesthetics.printBorderVert("Welcome to the Fitness App! ")
		Aesthetics.printBorderHorz(1)
		Aesthetics.printBorderVert(1)
		Aesthetics.printBorderVert("1) Sign Up")
		Aesthetics.printBorderVert("2) Login  ")
		Aesthetics.printBorderVert(1)
		Aesthetics.printBorderHorz(1)
	}
	def signUpCheck(checkStr:String, stmt:Statement):Boolean={
		var checkBool = true
		val inputQuery = s"SELECT username FROM login WHERE username = \'$checkStr\'"
		val rs = stmt.executeQuery(inputQuery)
		while(rs.next()){
			checkBool = false
		}
		return checkBool
	}
	def signUp(stmt:Statement): String ={
			Aesthetics.printBorderHorz(1)
			Aesthetics.printBorderVert("Lets get you signed up!")
			Aesthetics.printBorderHorz(1)
			Aesthetics.printBorderVert(">Enter a username<")
			Aesthetics.printBorderHorz(1)
			var userName = readLine()
			while(signUpCheck(userName, stmt) == false){ 
				Aesthetics.printBorderHorz(1)
				Aesthetics.printBorderVert("!UH OH!")
				Aesthetics.printBorderVert("That username has been taken")
				Aesthetics.printBorderVert("please try another")
				Aesthetics.printBorderHorz(1)
				Aesthetics.printBorderVert(">Enter a username<")
				Aesthetics.printBorderHorz(1)
				userName = readLine()
			}
			Aesthetics.printBorderHorz(1)
			Aesthetics.printBorderVert(">Enter a password<")
			Aesthetics.printBorderHorz(1)
			val password = readLine()
			val inputString = s"INSERT INTO login(username, password) VALUES (\'$userName\', \'$password\')"
			print(inputString)
			stmt.executeUpdate(inputString)
			return userName
	}
	def login(stmt:Statement): String ={
			Aesthetics.printBorderHorz(1)
			Aesthetics.printBorderVert("Go ahead and login")
			Aesthetics.printBorderHorz(1)
			Aesthetics.printBorderVert(">Enter your username<")
			Aesthetics.printBorderHorz(1)
			var userName = readLine()
			val inputQuery = s"SELECT username, password FROM login WHERE username = \'$userName\'"
			val rs = stmt.executeQuery(inputQuery)
			var checkBool = false
			var pwdTest = ""
			while(rs.next()){checkBool = true
				pwdTest = rs.getString("password")
			}
			if (checkBool == false){
				println(">That username doesnt exist<\n>please create an account<")
				userName = signUp(stmt)
			}
			else{
				Aesthetics.printBorderHorz(1)
				Aesthetics.printBorderVert(">Enter your password<")
				Aesthetics.printBorderHorz(1)
				var password = readLine()
				while((pwdTest == password) == false){
					Aesthetics.printBorderHorz(1)
					Aesthetics.printBorderVert(">Wrong password try again<")
					Aesthetics.printBorderHorz(1)
					password = readLine()
				}
			}
			return userName
	}
	def welcomeScreen(stmt:Statement, uName:String){
		var input = -1
		while(input != 0){
			//println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n")
			Aesthetics.printBorderHorz(1)
			Aesthetics.printBorderVert(s"Welcome $uName")
			Aesthetics.printBorderHorz(1)
			Aesthetics.printBorderVert(2)
			Aesthetics.printBorderVert("1) Create Routine")
			Aesthetics.printBorderVert("2) View Routines  ")
			Aesthetics.printBorderVert("0) Quit           ")
			Aesthetics.printBorderVert(2)
			Aesthetics.printBorderHorz(1)
			print(">Input<\n")
			input = readInt()
			input match {
				case 0 => {
					Aesthetics.printBorderHorz(1)
					Aesthetics.printBorderVert("Goodbye")
					Aesthetics.printBorderHorz(1)		
				}
				case 1 => createRoutine(uName, stmt)
				case 2 => viewRoutines(uName, stmt)
				case _ => print("Try again")
			}
		}
	}
	def createRoutine(uName:String, stmt:Statement){
		var inputStr = ""
		var userInput = ""
		var break = true
		while(userInput != "<" && userInput != ">"){
			Aesthetics.printBorderHorz(1)
			Aesthetics.printBorderVert("Lets build a routine!")
			Aesthetics.printBorderHorz(1)
			Aesthetics.printBorderVert(2)
			Aesthetics.printBorderVert("First, enter a name for your new routine")
			Aesthetics.printBorderVert(2)
			Aesthetics.printBorderHorz(1)
			val routineName = readLine(">Input<")
			val newRoutine = new Routine(uName, routineName, stmt)
			while(userInput != "<" && userInput != ">"){
				break = true
				newRoutine.displayRoutine()
				Aesthetics.printBorderVert("Select a day")
				Aesthetics.printBorderVert("< Go Back                 Save >")
				Aesthetics.printBorderHorz(1)
				println(">Input<")
				userInput = readLine()
				while(userInput != "<" && break && userInput != ">"){
					try{
						userInput.toInt
					}
					catch{case e: Exception => userInput = "0"}
					if(1 > userInput.toInt  || userInput.toInt > 7){
						println("Enter 1-7 or < to go back")
						userInput = readLine()
					}
					else{
						newRoutine.addDay(userInput)
						break = false
					}
				}
			}
			if(userInput == ">"){
				newRoutine.insertRoutine(stmt)
			}
		}
		
	}
	def viewRoutines(uName:String, stmt:Statement){
		val user = new User(uName, stmt)
		val routineArray:Array[String] = user.getRoutines()
		var userInput = ""
		do{
			Aesthetics.printHeader(s"$uName's routines")
			for(i<-0 to (routineArray.size - 1)){
				Aesthetics.printBorderVert((i + 1) +") " + routineArray(i))
				Aesthetics.printBorderHorz(1)
			}
			userInput = readLine(">Input<")
			try{
				userInput.toInt
			}
			catch{case e: Exception => userInput = "0"}
			if(1 > userInput.toInt  || userInput.toInt > routineArray.size){
				println("Enter 1-7 or < to go back")
				userInput = readLine()
			}
			else{
				val newRoutine = new Routine(uName, routineArray(userInput.toInt - 1), stmt)
				newRoutine.generateRoutineData(stmt)
				newRoutine.displayRoutine()
				Aesthetics.printBorderVert("< back                              data >")
				Aesthetics.printBorderHorz(1)
				userInput = readLine(">Input<")
				if(userInput == ">") viewExercises(newRoutine, uName, stmt)
			}
		}while(userInput != "<")
	}
	def viewExercises(routine:Routine, username:String, stmt:Statement){
			val exerciseArray = routine.getExerciseSet().toArray
			Aesthetics.printHeader("Routine: " + routine.getRoutineName())
			for(i<-1 to exerciseArray.size){
				Aesthetics.printBorderVert(i + ") " + exerciseArray(i-1)._1 + "->" + exerciseArray(i-1)._2)
				Aesthetics.printBorderHorz(1)
			}
			var userInput = readLine(">Input<")
			val exercise = exerciseArray(userInput.toInt-1)._1
			val amount = exerciseArray(userInput.toInt-1)._2
			val routineId = routine.getRoutineId(exercise, amount)
			do{
				Aesthetics.printHeader("Input or view data")
				Aesthetics.printBorderVert("1) Input")
				Aesthetics.printHeader("2) View")
				Aesthetics.printBorderVert("< back                                   ")
				Aesthetics.printBorderHorz(1)
				userInput = readLine(">Input<")
				if(userInput == "1") enterData(stmt, username, routine, exercise, amount, routineId)
				else if(userInput == "2") viewData(routineId, stmt, exercise, amount, routine)
				else if(userInput != "<"){
					Aesthetics.printHeader("Enter 1, 2, or <")
					userInput = readLine(">Input<")
				}
			}while(userInput != "<")
	}

	def enterData(stmt:Statement, username:String, routine:Routine, exercise:String, amount:String, routineId:Int){
			Aesthetics.printHeader("Enter data type for " + exercise + "->" + amount)
			val dataType = readLine(">Input<")
			Aesthetics.printHeader("Enter " + dataType + " for " + exercise + "->" + amount)
			val data = readLine(">Input<")
			Aesthetics.printHeader("Enter date for " + exercise + "->" + amount)
			val date = readLine(">Input<")
			var insertDataPoint = s"INSERT INTO datapoint (data, date, type, routineId) VALUES (\'$data\', STR_TO_DATE(\'$date\', \'%m-%d-%Y\'), \'$dataType\', $routineId)"
			stmt.executeUpdate(insertDataPoint)		
	}
	def viewData(routineId:Int, stmt:Statement, exercise:String, amount:String, routine:Routine){
		val selectStr = s"SELECT data, date, type FROM datapoint WHERE routineId = \'$routineId\' ORDER BY date ASC"
		val rs = stmt.executeQuery(selectStr)
		val dataArray = ArrayBuffer[String]()
		val dateArray = ArrayBuffer[String]()
		val dataTypeArray = ArrayBuffer[String]()
		while(rs.next()){
			dataArray.append(rs.getString("data"))
			dateArray.append(rs.getString("date").dropRight(9))
			dataTypeArray.append(rs.getString("type"))
		}
		if(dataArray.isEmpty == false){
			Aesthetics.printHeader(s"Data for $exercise->$amount")
			for(i<-0 to (dataArray.size - 1)){
				Aesthetics.printBorderVert(dataArray(i) + "     " + dateArray(i) + "    " + dataTypeArray(i))
				Aesthetics.printBorderHorz(1)
			}
			println(dataArray.max)
		}
		else Aesthetics.printHeader("No data")
	}

	def main(args: Array[String]): Unit = {

		val conn = getConnect()
		val stmt = conn.createStatement()
		var uName = ""
				
		openingScreen()
		println(">Input<")
		val firstInput = readLine()

		if(firstInput=="1"){uName = signUp(stmt)}
		else uName = login(stmt)

		welcomeScreen(stmt, uName)
	}
}