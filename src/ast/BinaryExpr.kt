package ast

import visitor.Visitor

class BinaryExpr(op: Char, var left: Expr, var right: Expr) : Expr(op) {

    override fun accept(v: Visitor) = v.visitBinaryExpr(this)

    override fun replaceChild(old: Expr, new: Expr) {
        if (left != old && right != old) {
            return
        }
        new.parent = this
        old.parent = null
        if (left == old) {
            left = new
            return
        }
        if (right == old) {
            if (new is BinaryExpr) {
                val paren = ParenExpr(new)
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

    override fun string(l: Int): String {
        return " ".repeat(l * 2) + "BinaryExpr with operator '$op'\n" +
                left.string(l + 1) + "\n" + right.string(l + 1)
    }
}