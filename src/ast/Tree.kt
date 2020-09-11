package ast

import visitor.Visitor

class Tree(var root: Expr) : Expr(null) {
    override fun accept(v: Visitor) = v.visitTree(this)

    override fun replaceChild(old: Expr, new: Expr) {
        if (old != root)
            return
        old.parent = null
        new.parent = this
        root = new
    }

    override fun text() = root.text()

    override fun string(l: Int): String {
        return " ".repeat(l * 2) + "Tree\n" + root.string(l + 1)
    }
}