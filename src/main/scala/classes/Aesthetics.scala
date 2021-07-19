package classes

object Aesthetics{

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

    def printHeader(headStr:String){
        printBorderHorz(1)
        printBorderVert(headStr)
        printBorderHorz(1)
    }
}