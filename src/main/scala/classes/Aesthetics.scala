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
			val additionalSpace = borderStr.length % 2
		    for(i<-2 to halfBorderLength) newStr += " "
		    newStr += borderStr
		    for(i<-2 to halfBorderLength) newStr += " "
			if(additionalSpace == 1) newStr+=" "
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
	
	def getDayCol(dayStr:String): String = {
		val max = 12
		val diff = max - dayStr.length()
		var resStr = "| " + dayStr
		for(i<-1 to diff) resStr += " "
		resStr += "|"
		return resStr
	}
}