package parser

import ast.*
import exceptions.*

class Parser {

    private lateinit var tokenizer: Tokenizer

    fun parse(string: String): Tree {
        tokenizer = Tokenizer(string)
        val expr = parseBinaryExpr()
        val tree = Tree(expr)
        expr.parent = tree
        return tree
    }

    private fun parseBinaryExpr(): Expr {
        var left = parseUnaryExpr()
        while (true) {
            val op = tokenizer.parseSymbol('+', '-') ?:
                if (tokenizer.checkPosition() == null || tokenizer.checkPosition() == ')')
                    return left
                else
                    throw MissOperatorException(tokenizer.str, tokenizer.position())

            val right = parseParenExpr()
            val expr = BinaryExpr(op, left, right)
            left.parent = expr
            right.parent = expr
            left = expr
        }
    }

    private fun parseUnaryExpr(): Expr {
        val op = tokenizer.parseSymbol('+', '-')
        return parseParenExpr(op)
    }

    private fun parseParenExpr(op: Char? = null): Expr {
        if (tokenizer.parseSymbol('(') != null) {
            val expr = parseBinaryExpr()
            if (tokenizer.parseSymbol(')') != null) {
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
        val token = tokenizer.parseName()
        val num = token.toIntOrNull() ?: return VarExpr(token, op)
        return IntExpr(num, op)
    }
}