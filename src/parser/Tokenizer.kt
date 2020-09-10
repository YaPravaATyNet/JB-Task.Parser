package parser

import exceptions.MissIdentifierException
import exceptions.UnexpectedTokenException
import exceptions.UnknownTokenException

class Tokenizer(val str: String) {
    companion object val tokens = setOf('+', '-', '(', ')')
    private var position = 0

    fun position(): Int {
        return position
    }

    fun parseSymbol(vararg symbols: Char): Char? {
        val symbol = checkPosition()
        if (symbol != null && symbols.contains(symbol)) {
            position++
            skipSpace()
            return symbol
        }
        return null
    }

    fun parseName(): String {
        val start = position
        var symbol = checkPosition()
        if (tokens.contains(symbol)) {
            throw UnexpectedTokenException(str, position)
        }
        while (symbol != null && !symbol.isWhitespace()) {
            position++
            symbol = checkPosition()
            if (tokens.contains(symbol)) {
                break
            }
        }
        val substring = str.substring(start, position)
        if (substring.isEmpty()) {
            throw MissIdentifierException(str, position)
        }
        skipSpace()
        return substring
    }

    fun checkPosition(): Char? {
        if (position >= str.length) {
            return null
        }
        val symbol = str[position]
        if (symbol.isDigit() || symbol.isLetter() || symbol.isWhitespace() || tokens.contains(symbol)) {
            return str[position]
        }
        throw UnknownTokenException(str, position)
    }

    private fun skipSpace() {
        while (position < str.length && str[position].isWhitespace()) {
            position++
        }
    }
}