package ast

import Visitor

class VarExpr(val name: String, op: Char?): Expr(op) {
    override fun accept(v: Visitor) = v.visitVarExpr(this)
}