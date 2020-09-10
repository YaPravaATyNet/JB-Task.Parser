package exceptions

class MissOperatorException(str: String, position: Int) :
    ParserException("Operator is expected before position: $position\n$str\n" + pointer(position))