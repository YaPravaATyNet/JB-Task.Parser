package ast

import visitor.Visitor

class VarExpr(val name: String, op: Char?) : Expr(op) {
    override fun accept(v: Visitor) = v.visitVarExpr(this)

    override fun replaceChild(old: Expr, new: Expr) {}

    override fun text(): String {
        if (op != null) {
            return "$op$name"
        }
        return name
    }

    override fun print(l: Int) {
        print(" ".repeat(l * 2))
        var opStr = ""
        if (op != null) {
            opStr = "with unary operator '$op'"
        }
        println("VarExpr($name) $opStr")
    }
}