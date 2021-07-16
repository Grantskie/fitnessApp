import scala.io.StdIn.readLine
import scala.io.StdIn.readInt
import java.sql.Connection
import java.sql.DriverManager
import java.sql.SQLException
import java.sql.Statement;
import java.sql.ResultSet;

object fitnessApp {
	val borderLength = 56

	def getConnect() :Connection= {
		val url = "jdbc:mysql://localhost:3306/fitnessapp"
 		val driver = "com.mysql.cj.jdbc.Driver"
  		val username = "scalaFitnessApp"
  		val password = "ScalaFitness1!"
		Class.forName(driver)
  		var connection:Connection = DriverManager.getConnection(url, username, password)
		
		return connection
	}

	def printBorderVert(borderCount: Int){
		for(i<-1 to borderCount)
		println("|                                                      |")
	}
	def printBorderVert(borderStr: String){
		val halfBorderLength = (borderLength - borderStr.length()) / 2
		var newStr = "|"
		for(i<-2 to halfBorderLength) newStr += " "
		newStr += borderStr
		for(i<-2 to halfBorderLength) newStr += " "
		newStr+="|"
		println(newStr)
	}
	def printBorderHorz(borderCount: Int){
		for(i<-1 to borderCount)
		println("--------------------------------------------------------")
	}
	def openingScreen(){
		printBorderHorz(1)
		printBorderVert("Welcome to the Fitness App! ")
		printBorderHorz(1)
		printBorderVert(1)
		printBorderVert("1) Sign Up")
		printBorderVert("2) Login  ")
		printBorderVert(1)
		printBorderHorz(1)
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
			printBorderHorz(1)
			printBorderVert("Lets get you signed up!")
			printBorderHorz(1)
			printBorderVert(">Enter a username<")
			printBorderHorz(1)
			var userName = readLine()
			while(signUpCheck(userName, stmt) == false){ 
				printBorderHorz(1)
				printBorderVert("!UH OH!")
				printBorderVert("That username has been taken")
				printBorderVert("please try another")
				printBorderHorz(1)
				printBorderVert(">Enter a username<")
				printBorderHorz(1)
				userName = readLine()
			}
			printBorderHorz(1)
			printBorderVert(">Enter a password<")
			printBorderHorz(1)
			val password = readLine()
			val inputString = s"INSERT INTO login(username, password) VALUES (\'$userName\', \'$password\')"
			print(inputString)
			stmt.executeUpdate(inputString)
			return userName
	}
	def login(stmt:Statement): String ={
			printBorderHorz(1)
			printBorderVert("Go ahead and login")
			printBorderHorz(1)
			printBorderVert(">Enter your username<")
			printBorderHorz(1)
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
				printBorderHorz(1)
				printBorderVert(">Enter your password<")
				printBorderHorz(1)
				var password = readLine()
				while((pwdTest == password) == false){
					printBorderHorz(1)
					printBorderVert(">Wrong password try again<")
					printBorderHorz(1)
					password = readLine()
				}
			}
			return userName
	}
	def welcomeScreen(stmt:Statement, uName:String){
		var input = -1
		while(input != 0){
			println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n")
			printBorderHorz(1)
			printBorderVert(s"Welcome $uName")
			printBorderHorz(1)
			printBorderVert(2)
			printBorderVert("1) Create Routine")
			printBorderVert("2) View Routines  ")
			printBorderVert("0) Quit           ")
			printBorderVert(2)
			printBorderHorz(1)
			print(">Input<\n")
			input = readInt()
			input match {
				case 0 => {
					printBorderHorz(1)
					printBorderVert("Goodbye")
					printBorderHorz(1)		
				}
				case 1 => createRoutine(uName, stmt)
				case 2 => print("viewRoutines()")
				case _ => print("Try again")
			}
		}
	}
	def createRoutine(uName:String, stmt:Statement){
		var inputStr = ""
		var userInput = ""
		while(userInput != "<"){
			printBorderHorz(1)
			printBorderVert("Lets build a routine!")
			printBorderHorz(1)
			printBorderVert(2)
			printBorderVert("First, enter a name for your new routine")
			printBorderVert(2)
			printBorderHorz(1)
			val routineName = readLine(">Input<")
			displayRoutine(uName, stmt, routineName)
			userInput = readLine()
		}
		
	}
	def displayRoutine(uName:String, stmt:Statement, routineName:String){
		var selectQuery = s"SELECT * FROM routine WHERE username = \'$uName\' AND routineName = \'$routineName\'"
		println(selectQuery)
		val rs = stmt.executeQuery(selectQuery)
		if (rs.next() == false){
			printBorderHorz(1)
			printBorderVert(s"Routine: $routineName")
			printBorderHorz(1)
			println("|Sunday   |")
			printBorderHorz(1)
			println("|Monday   |")
			printBorderHorz(1)
			println("|Tuesday  |")
			printBorderHorz(1)
			println("|Wednesday|")
			printBorderHorz(1)
			println("|Thursday |")
			printBorderHorz(1)
			println("|Friday   |")
			printBorderHorz(1)
			println("|Saturday |")
			printBorderHorz(1)
		}
		else{
			while(rs.next()){
				print(rs.getString("exercise"))
				print(rs.getString("amount"))
				print(rs.getString("day"))
			}
		}
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