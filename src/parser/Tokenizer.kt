package parser

import exceptions.UnknownTokenException

class Tokenizer(val str: String) {
    companion object {
        private val operators = setOf('+', '-', '(', ')')
    }
    private var position = 0
    private var word = ""

    fun position(): Int {
        return position
    }

    fun parseToken(vararg tokens: Token): String? {
        val token = nextToken()
        if (!tokens.contains(token)) {
            return null
        }
        if (token == Token.WORD) {
            position += word.length
            skipSpace()
            return word
        }
        val char = if (checkPosition() != null) checkPosition() else return null
        position++
        skipSpace()
        return char.toString()
    }

    fun nextToken(): Token? {
        val symbol =  checkPosition()
        return when {
            symbol == null -> null
            symbol == '+' -> Token.PLUS
            symbol == '-' -> Token.MINUS
            symbol == '(' -> Token.OPEN_PAREN
            symbol == ')' -> Token.CLOSE_PAREN
            symbol.isLetter() || symbol.isDigit() -> parseWord()
            else -> throw UnknownTokenException(str, position)
        }
    }

    private fun parseWord(): Token {
        val start = position
        var end = position
        var symbol = checkPosition()
        while (symbol != null && !symbol.isWhitespace() && !operators.contains(symbol)) {
            end++
            symbol = checkPosition(end)
        }
        word = str.substring(start, end)
        skipSpace()
        return Token.WORD
    }

    private fun checkPosition(): Char? {
        return checkPosition(position)
    }

    private fun checkPosition(position: Int): Char? {
        if (position >= str.length) {
            return null
        }
        val symbol = str[position]
        if (symbol.isDigit() || symbol.isLetter() || symbol.isWhitespace() || operators.contains(symbol)) {
            return str[position]
        }
        throw UnknownTokenException(str, position)
    }

    private fun skipSpace() {
        while (!isEOF() && str[position].isWhitespace()) {
            position++
        }
    }

    fun isEOF(): Boolean = position >= str.length
}