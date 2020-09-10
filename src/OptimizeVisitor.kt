import ast.*

class OptimizeVisitor: Visitor {
    override fun visitIntExpr(node: IntExpr) {}

    override fun visitVarExpr(node: VarExpr) {}

    override fun visitParenExpr(node: ParenExpr) {
        node.expr.accept(this)
    }

    override fun visitBinaryExpr(node: BinaryExpr) {
        node.left.accept(this)
        node.right.accept(this)
    }
}