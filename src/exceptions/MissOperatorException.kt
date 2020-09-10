package exceptions

class MissOperatorException(str: String, position: Int) :
    ParserException("Operator is expected at position: $position\n$str\n" + pointer(position))