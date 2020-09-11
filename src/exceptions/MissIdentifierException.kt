package exceptions

class MissIdentifierException(str: String, position: Int) :
    ParserException("Variable or number are expected at position: $position\n$str\n" + pointer(position))