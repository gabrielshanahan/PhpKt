package cz.php.kt.statements

import cz.php.kt.toPhpStr

/**
 * Represents the top-level node of any PHP code. Other classes define the PHP DSL by adding extension methods to this
 * object, which correspond to the code they generate.
 */
class Php : Statement() {
    /**
     * Generates the basic "<?php" tag before a list of Nodes.
     */
    override fun toPhpStr(): String = "<?php\n\n${children.toPhpStr()}"
}

/**
 * DSL function to create a piece of code prepended with "<?php". The rest of the code is generated in [exec].
 */
inline fun php(exec: Php.() -> Unit) = Php().apply(exec)
