package exceptions

class MissCloseParenException(str: String, position: Int) :
    ParserException("Close parenthesis is expected at position: $position\n$str\n" + pointer(position))