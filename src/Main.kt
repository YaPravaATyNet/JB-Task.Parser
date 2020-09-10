fun main() {
    val parser = Parser()
    val a = parser.parse("3 + 1")
    val b = parser.parse("-1 + 3 - (2 + a)+(23+(-dsds))")
    val c = parser.parse("1+-1")
    val z = ""
}