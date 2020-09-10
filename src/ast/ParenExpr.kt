package ast

import Visitor

class ParenExpr(var expr: Expr, op: Char?): Expr(op) {
    override fun accept(v: Visitor) = v.visitParenExpr(this)

    override fun replaceChild(old: Expr, new: Expr) {
        if (old != expr) {
            return
        }
        new.parent = this
        expr = new
    }

    override fun text(): String {
        if (op != null) {
            return "$op(${expr.text()})"
        }
        return "(${expr.text()})"
    }

    override fun print(l: Int) {
        print(" ".repeat(l * 2))
        var opStr = ""
        if (op != null) {
            opStr = "with unary operator '$op'"
        }
        println("ParenExpr $opStr")
        expr.print(l + 1)
    }
}