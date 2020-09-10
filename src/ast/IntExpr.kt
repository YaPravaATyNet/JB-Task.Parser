package ast

import Visitor

class IntExpr(val num: Int, op: Char?): Expr(op) {
    fun getValue(): Int {
        if (op == '-') {
            return -num
        }
        return num
    }

    override fun accept(v: Visitor) = v.visitIntExpr(this)

    override fun replaceChild(old: Expr, new: Expr){}

    override fun text(): String {
        if (op != null) {
            return "$op$num"
        }
        return "$num"
    }

    override fun print(l: Int) {
        print(" ".repeat(l * 2))
        var opStr = ""
        if (op != null) {
            opStr = "with unary operator '$op'"
        }
        println("IntExpr($num) $opStr")
    }
}