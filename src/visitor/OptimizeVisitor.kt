package visitor

import ast.*
import visitor.Visitor
import kotlin.math.abs

class OptimizeVisitor : Visitor {
    override fun visitIntExpr(node: IntExpr) {}

    override fun visitVarExpr(node: VarExpr) {}

    override fun visitParenExpr(node: ParenExpr) {
        node.expr.accept(this)
        if (node.expr !is BinaryExpr || node.parent is Tree) {
            node.parent?.replaceChild(node, node.expr)
        }
    }

    override fun visitBinaryExpr(node: BinaryExpr) {
        node.left.accept(this)
        node.right.accept(this)

        if (node.op != '+' && node.op != '-') {
            return
        }

        val left = node.left
        val right = node.right
        if (left is IntExpr && right is IntExpr) {
            val res = if (node.op == '+')
                left.getValue() + right.getValue()
            else
                left.getValue() - right.getValue()
            val newOp = if (res < 0) '-' else null
            val newExpr = IntExpr(abs(res), newOp)
            node.parent?.replaceChild(node, newExpr)
            return
        }

        val newExpr = when {
            (left is IntExpr && left.getValue() == 0) -> right
            (right is IntExpr && right.getValue() == 0) -> left
            else -> return
        }
        node.parent?.replaceChild(node, newExpr)
    }

    override fun visitTree(node: Tree) {
        node.root.accept(this)
    }
}