package ast

import Visitor

class BinaryExpr(op: Char, val left: Expr, val right: Expr): Expr(op) {
    override fun accept(v: Visitor) = v.visitBinaryExpr(this)
}