
import scala.io.StdIn.readLine
object fitnessApp {
	val borderLength = 56
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

	def main(args: Array[String]): Unit = {
		openingScreen()
		println("Input:")
		val test = readLine()
		println("test")
	}
}