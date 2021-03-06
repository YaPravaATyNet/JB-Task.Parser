package ast

import visitor.Visitor

class ParenExpr(var expr: Expr, op: Char? = null) : Expr(op) {
    override fun accept(v: Visitor) = v.visitParenExpr(this)

    override fun replaceChild(old: Expr, new: Expr) {
        if (old != expr) {
            return
        }
        old.parent = null
        new.parent = this
        expr = new
    }

    override fun text(): String {
        if (op != null) {
            return "$op(${expr.text()})"
        }
        return "(${expr.text()})"
    }

    override fun string(l: Int): String {
        var opStr = ""
        if (op != null) {
            opStr = "with unary operator '$op'"
        }
        return " ".repeat(l * 2) + "ParenExpr $opStr\n" + expr.string(l + 1)
    }
}