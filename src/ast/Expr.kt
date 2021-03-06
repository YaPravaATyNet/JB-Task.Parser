package ast

import visitor.Visitor

abstract class Expr(var op: Char?) {
    var parent: Expr? = null
    abstract fun accept(v: Visitor)
    abstract fun replaceChild(old: Expr, new: Expr)
    abstract fun text(): String
    abstract fun string(l: Int = 0): String
}