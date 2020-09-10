package ast

import Visitor

class ParenExpr(val expr: Expr, op: Char?): Expr(op) {
    override fun accept(v: Visitor) = v.visitParenExpr(this)
}