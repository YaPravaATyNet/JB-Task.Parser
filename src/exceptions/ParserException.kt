package exceptions

open class ParserException(message: String): Exception(message)

fun pointer(position: Int) = " ".repeat(position) + "^"