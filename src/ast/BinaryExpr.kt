package ast

import Visitor

class BinaryExpr(op: Char, var left: Expr, var right: Expr) : Expr(op) {
    override fun accept(v: Visitor) = v.visitBinaryExpr(this)

    override fun replaceChild(old: Expr, new: Expr) {
        new.parent = this
        if (left == old) {
            left = new
            return
        }
        if (right == old) {
            if (new is BinaryExpr) {
                val paren = ParenExpr(new, null)
                new.parent = paren
                paren.parent = this
                right = paren
                return
            }
            right = new
            if (new.op == '-') {
                when (op) {
                    '-' -> op = '+'
                    '+' -> op = '-'
                }
            }
            new.op = null
        }
    }

    override fun text(): String {
        return left.text() + " $op " + right.text()
    }

    override fun print(l: Int) {
        print(" ".repeat(l * 2))
        println("BinaryExpr with operator '$op'")
        left.print(l + 1)
        right.print(l + 1)
    }
}