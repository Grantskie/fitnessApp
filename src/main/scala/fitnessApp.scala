import scala.io.StdIn.readLine
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
		println("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n")
		printBorderHorz(1)
		printBorderVert(s"Welcome $uName")
		printBorderHorz(1)
		printBorderVert(2)
		printBorderVert("Create Rountine")
		printBorderVert("View Routines")
		printBorderVert("Quit")
		printBorderVert(2)
		printBorderHorz(1)
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