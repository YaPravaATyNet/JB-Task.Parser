package parser

import ast.*
import exceptions.*

class Parser {

    private lateinit var tokenizer: Tokenizer

    fun parse(string: String): Tree {
        tokenizer = Tokenizer(string)
        val expr = parseBinaryExpr()
        if (!tokenizer.isEOF()) {
            throw UnexpectedTokenException(tokenizer.str, tokenizer.position())
        }
        val tree = Tree(expr)
        expr.parent = tree
        return tree
    }

    private fun parseBinaryExpr(): Expr {
        var left = parseUnaryExpr()
        while (true) {
            val op = tokenizer.parseToken(Token.PLUS, Token.MINUS)
                ?: if (tokenizer.isEOF() || tokenizer.nextToken() == Token.CLOSE_PAREN)
                    return left
                else
                    throw MissOperatorException(tokenizer.str, tokenizer.position())

            val right = parseParenExpr()
            val expr = BinaryExpr(op[0], left, right)
            left.parent = expr
            right.parent = expr
            left = expr
        }
    }

    private fun parseUnaryExpr(): Expr {
        val op = tokenizer.parseToken(Token.PLUS, Token.MINUS)
        return parseParenExpr(op?.get(0))
    }

    private fun parseParenExpr(op: Char? = null): Expr {
        if (tokenizer.parseToken(Token.OPEN_PAREN) != null) {
            val expr = parseBinaryExpr()
            if (tokenizer.parseToken(Token.CLOSE_PAREN) != null) {
                val parenExpr = ParenExpr(expr, op)
                expr.parent = parenExpr
                return parenExpr
            } else {
                throw MissCloseParenException(tokenizer.str, tokenizer.position())
            }
        }
        return parseAtomExpr(op)
    }

    private fun parseAtomExpr(op: Char? = null): Expr {
        val token = tokenizer.parseToken(Token.WORD) ?: if (tokenizer.isEOF())
            throw MissIdentifierException(tokenizer.str, tokenizer.position())
        else
            throw UnexpectedTokenException(tokenizer.str, tokenizer.position())

        val num = token.toIntOrNull() ?: return VarExpr(token, op)
        return IntExpr(num, op)
    }
}