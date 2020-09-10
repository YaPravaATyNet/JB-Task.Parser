package ast

import visitor.Visitor

class Tree(var root: Expr) : Expr(null) {
    override fun accept(v: Visitor) = v.visitTree(this)

    override fun replaceChild(old: Expr, new: Expr) {
        if (old != root)
            return
        root = new
        new.parent = this
    }

    override fun text() = root.text()

    override fun print(l: Int) {
        print(" ".repeat(l * 2))
        println("Tree")
        root.print(l + 1)
    }
}