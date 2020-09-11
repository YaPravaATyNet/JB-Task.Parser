package ast

import visitor.Visitor

class IntExpr(val num: Int, op: Char? = null) : Expr(op) {
    fun getValue(): Int {
        if (op == '-') {
            return -num
        }
        return num
    }

    override fun accept(v: Visitor) = v.visitIntExpr(this)

    override fun replaceChild(old: Expr, new: Expr) {}

    override fun text(): String {
        if (op != null) {
            return "$op$num"
        }
        return "$num"
    }

    override fun string(l: Int): String {
        var opStr = ""
        if (op != null) {
            opStr = "with unary operator '$op'"
        }
        return " ".repeat(l * 2) + "IntExpr($num) $opStr"
    }
}