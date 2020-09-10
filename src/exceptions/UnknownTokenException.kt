package exceptions

class UnknownTokenException(str: String, position: Int):
        ParserException("Token is unknown at position: $position\n$str\n"+ pointer(position))