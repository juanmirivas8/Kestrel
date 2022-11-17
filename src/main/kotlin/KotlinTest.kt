import utils.encryptSHA256

enum class Color(val initial : Char) {
    RED('R'), GREEN('G'), BLUE('B'), YELLOW('Y'), WHITE('W'), PURPLE('P');
}

fun main(args: Array<String>) {
    print(String.Companion.encryptSHA256("Hello Worl"))

    /*
    println("Secret key to guess")
    val key = String.readKey()
    var guessedKey : String
    do{
        println("Guess the key")
        guessedKey = String.readKey()
        infoKey(guessedKey, key)

    }while (guessedKey != key)
    println("You guessed the key")
     */
}

tailrec fun String.Companion.readKey(): String {
    println("Enter a key( until valid key is introducted ): ")
    val key = readLine()
    return if( key != null && isValidKey(key)) key else readKey()
}

 fun String.Companion.isValidKey(key: String): Boolean {
    return key.length == 4 && key.all { c -> c in Color.values().map { color: Color ->color.initial  } }
}

fun infoKey(guessedKey:String, key: String){
    var info = 0 to 0
    for ((i,c) in guessedKey.withIndex()){
        if(key.any{it==c}){
            info = info.first + 1 to info.second
            if(guessedKey.get(i) == key.get(i)){
                info = info.first to info.second + 1
            }
        }
    }
    println("Number of correct colors: ${info.first} - Number of correct positions: ${info.second}")
}
