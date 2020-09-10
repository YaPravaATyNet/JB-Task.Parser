import ast.*

interface Visitor {
    fun visitIntExpr(node: IntExpr)
    fun visitVarExpr(node: VarExpr)
    fun visitParenExpr(node: ParenExpr)
    fun visitBinaryExpr(node: BinaryExpr)
    fun visitTree(node: Tree)
}