package ast

import Visitor

abstract class Expr(val op: Char?) {
    var parent: Expr? = null
    abstract fun accept(v: Visitor)
}