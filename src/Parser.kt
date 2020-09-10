import ast.*
import java.lang.Exception

class Parser {
    private val tokens = setOf('+', '-', '(', ')')
    private lateinit var str: String
    private var position = 0

    fun parse(string: String): Expr {
        str = string
        position = 0
        return parseBinaryExpr()
    }

    fun parseBinaryExpr(): Expr {
        var left = parseUnaryExpr()
        while (true) {
            val op = parseSymbol('+', '-') ?: return left
            val right = parseParenExpr()
            val expr = BinaryExpr(op, left, right)
            left.parent = expr
            right.parent = expr
            left = expr
        }
    }

    fun parseUnaryExpr(): Expr {
        val op = parseSymbol('+', '-')
        return parseParenExpr(op)
    }

    fun parseParenExpr(op: Char? = null): Expr {
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
        return parseAtomExpr()
    }

    fun parseAtomExpr(op: Char? = null): Expr {
        val token = parseName()
        val num = token.toIntOrNull() ?: return VarExpr(token, op)
        return IntExpr(num, op)
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
            throw Exception("unexpected token")
        }
        while (symbol != null && !symbol.isWhitespace()) {
            if (symbol.isDigit() || symbol.isLetter())  {
                position++
                symbol = checkPosition()
                continue
            }
            if (tokens.contains(symbol)) {
                break
            }
            throw Exception("unknown token")
        }
        val substring = str.substring(start, position)
        skipSpace()
        return substring
    }

    fun skipSpace() {
        while (position < str.length && str[position].isWhitespace()) {
            position++
        }
    }

    fun checkPosition(): Char? {
        if (position >= str.length) {
            return null
        }
        return str[position]
    }
}