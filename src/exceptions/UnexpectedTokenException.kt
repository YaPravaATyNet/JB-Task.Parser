package exceptions

class UnexpectedTokenException(str: String, position: Int) :
    ParserException("Token is unexpected at position: $position\n$str\n" + pointer(position))