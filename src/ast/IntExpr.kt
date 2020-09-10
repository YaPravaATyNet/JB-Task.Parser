package ast

import Visitor

class IntExpr(val num: Int, op: Char?): Expr(op) {
    override fun accept(v: Visitor) = v.visitIntExpr(this)
    fun getValue(): Int {
        if (op == '-') {
            return -num
        }
        return num
    }
}