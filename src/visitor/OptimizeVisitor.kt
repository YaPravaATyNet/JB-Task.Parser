package visitor

import ast.*
import kotlin.math.abs

class OptimizeVisitor : Visitor {
    override fun visitIntExpr(node: IntExpr) {
        if (node.getValue() == 0) {
            node.op = null
            return
        }
        reducePlus(node)
    }

    override fun visitVarExpr(node: VarExpr) {
        reducePlus(node)
    }

    override fun visitParenExpr(node: ParenExpr) {
        node.expr.accept(this)
        reducePlus(node)
        if (node.parent is Tree || node.expr !is BinaryExpr) {
            if (node.op == '-') {
                if (node.expr.op == '-') {
                    node.expr.op = null
                } else {
                    node.expr.op = '-'
                }
            }
            if (node.expr is IntExpr && (node.expr as IntExpr).getValue() == 0) {
                node.expr.op = null
            }
            node.parent?.replaceChild(node, node.expr)
            return
        }
        val parent = node.parent
        if (parent is BinaryExpr && parent.left == node && node.op != '-') {
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

        if (left is VarExpr && right is VarExpr && left.name == right.name && node.op == '-' &&
                left.op != '-' && right.op != '-') {
            node.parent?.replaceChild(node, IntExpr(0))
            return
        }

        val newExpr = when {
            (left is IntExpr && left.getValue() == 0) -> {
                if (node.op == '-') {
                    right.op = '-'
                }
                right
            }
            (right is IntExpr && right.getValue() == 0) -> left
            else -> return
        }
        node.parent?.replaceChild(node, newExpr)
    }

    override fun visitTree(node: Tree) {
        node.root.accept(this)
    }

    private fun reducePlus(node: Expr) {
        if (node.op == '+') {
            node.op = null
        }
    }
}