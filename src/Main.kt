import parser.Parser
import visitor.OptimizeVisitor
import java.lang.Exception

fun main() {
    goodExample("3 + a")
    goodExample("-1 + a - 0")
    goodExample("  1-2   + (-c)")
    goodExample("2 - (5 + 8) + (-2) - (a + 1)")
    goodExample("(a - a + b - c) + 1")
    goodExample("(a + (((b + c) + (-(d + e)))))")
    badExample("3 * a")
    badExample("1 + -2")
    badExample("(3 + 2")
    badExample("a +")
    badExample("2 3")
}

fun goodExample(string: String) {
    println("<====================================================================================================================>")
    println("Original expression: $string")
    val tree = Parser().parse(string)
    println("Expression from AST: ${tree.text()}")
    println(tree.string())
    tree.accept(OptimizeVisitor())
    println()
    println("Expression after optimization: ${tree.text()}")
    println(tree.string())
    println()
}

fun badExample(string: String) {
    println("<====================================================================================================================>")
    println("Original expression: $string")
    try {
        Parser().parse(string)
    } catch (e: Exception) {
        println("Error: ${e.message}")
    }
    println()
}