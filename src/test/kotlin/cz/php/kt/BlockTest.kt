package cz.php.kt

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec

class BlockTest : StringSpec({
    class StringNode(val name: String) : Node(mutableListOf()) {
        override fun asPhp(): String = name
    }

    class MockFor(children: MutableList<out Node> = mutableListOf()) : Block() {
        init {
            children.forEach {
                +it
            }
        }

        override fun renderHead(): String = "for()"
    }

    "A block should indent its children properly" {
        val expectedFlat = """
            |for()
            |{
            |    0
            |    1
            |}""".trimMargin()

        val strings = MutableList(2) {
            StringNode(it.toString())
        }

        val mockFor = MockFor(strings)

        mockFor.asPhp() shouldBe expectedFlat

        val expectedNested = """
            |for()
            |{
            |    for()
            |    {
            |        0
            |        1
            |    }
            |    1
            |}""".trimMargin()

        MockFor(mutableListOf(mockFor, StringNode("1"))).asPhp() shouldBe expectedNested

        val expectedEmpty = """for()"""
        MockFor().asPhp() shouldBe expectedEmpty
    }
})