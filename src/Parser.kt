import ast.*
import java.lang.Exception

class Parser {
    private val tokens = setOf('+', '-', '(', ')')
    private lateinit var str: String
    private var position = 0

    fun parse(string: String): Tree {
        str = string
        position = 0
        val expr = parseBinaryExpr()
        val tree = Tree(expr)
        expr.parent = tree
        return tree
    }

    private fun parseBinaryExpr(): Expr {
        var left = parseUnaryExpr()
        while (true) {
            val op = parseSymbol('+', '-') ?: return left
            val right = parseParenExpr()
            if (right.text() == "") {
                throw Exception("Argument expected")
            }
            val expr = BinaryExpr(op, left, right)
            left.parent = expr
            right.parent = expr
            left = expr
        }
    }

    private fun parseUnaryExpr(): Expr {
        val op = parseSymbol('+', '-')
        return parseParenExpr(op)
    }

    private fun parseParenExpr(op: Char? = null): Expr {
        if (parseSymbol('(') != null) {
            val expr = parseBinaryExpr()
            if (parseSymbol(')') != null) {
                val parenExpr = ParenExpr(expr, op)
                expr.parent = parenExpr
                return parenExpr
            } else {
                throw Exception("No close parenthesis")
            }
        }
        return parseAtomExpr(op)
    }

    private fun parseAtomExpr(op: Char? = null): Expr {
        val token = parseName()
        val num = token.toIntOrNull() ?: return VarExpr(token, op)
        return IntExpr(num, op)
    }

    private fun parseSymbol(vararg symbols: Char): Char? {
        val symbol = checkPosition()
        if (symbol != null && symbols.contains(symbol)) {
            position++
            skipSpace()
            return symbol
        }
        return null
    }

    private fun parseName(): String {
        val start = position
        var symbol = checkPosition()
        if (tokens.contains(symbol)) {
            throw Exception("Unexpected token")
        }
        while (symbol != null && !symbol.isWhitespace()) {
            position++
            symbol = checkPosition()
            if (tokens.contains(symbol)) {
                break
            }
        }
        val substring = str.substring(start, position)
        skipSpace()
        return substring
    }

    private fun skipSpace() {
        while (position < str.length && str[position].isWhitespace()) {
            position++
        }
    }

    private fun checkPosition(): Char? {
        if (position >= str.length) {
            return null
        }
        val symbol = str[position]
        if (symbol.isDigit() || symbol.isLetter() || symbol.isWhitespace() || tokens.contains(symbol)) {
            return str[position]
        }
        throw Exception("Unknown token")
    }
}