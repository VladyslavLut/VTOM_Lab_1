import java.io.File

fun main(args: Array<String>) {
    val path = args[0]
    val code = File(path).readText()
    println(code)
    val scanner = Scanner()
    val tokens = scanner.scan(code)
    println(tokens)
}

class Scanner {

    private val regex = PATTERNS.joinToString("|") { "($it)" }.toRegex(RegexOption.MULTILINE)

    fun scan(sourceCode: String): List<String> {
        val groups = regex.findAll(sourceCode)
        val tokens = mutableListOf<String>()
        groups.forEach {
            tokens += mapToToken(it.value) ?: return@forEach
        }
        return tokens
    }

    private fun mapToToken(group: String): String? = when (group) {
        "+" -> "AddOp"
        "-" -> "SubOp"
        "*" -> "MulOp"
        "/" -> "DivOp"
        "%" -> "ModOp"
        "++" -> "INC"
        "--" -> "DEC"
        "=" -> "ASSGN"
        ">" -> "GT"
        "<" -> "LT"
        "==" -> "EQL"
        "!=" -> "NEQL"
        ">=" -> "GOET"
        "<=" -> "LOET"
        "(" -> "LP"
        ")" -> "RP"
        "{" -> "LB"
        "}" -> "RB"
        "[" -> "LS"
        "]" -> "RS"
        ";" -> "SC"
        ":" -> "CLN"
        "," -> "CM"
        "." -> "DOT"
        "",
        " " -> null
        else -> mapSequence(group)
    }

    private fun mapSequence(group: String): String = when {
        group.matches(NEW_LINE_REGEX) -> "NL"
        group.matches(NUMBER_REGEX) -> "NUM"
        group.matches(STRING_REGEX) -> "STR"
        group.matches(WORD_REGEX) -> mapKeyWord(group)
        else -> "ERROR"
    }

    private fun mapKeyWord(group: String): String = when (group) {
        "var" -> "DCLR"
        "if" -> "IF"
        "else" -> "ELSE"
        "for" -> "FOR"
        "while" -> "WHILE"
        "do" -> "DO"
        else -> "ID"
    }

    companion object {
        private const val NUMBER = "\\d+"
        private const val WORD = "\\w+"
        private const val NEW_LINE = "(\\r?\\n)+"
        private const val STRING = "\"(\\w*(\\r?\\n)*)+\""
        private val NUMBER_REGEX = NUMBER.toRegex()
        private val WORD_REGEX = WORD.toRegex()
        private val NEW_LINE_REGEX = NEW_LINE.toRegex()
        private val STRING_REGEX = STRING.toRegex()
        private val PATTERNS = listOf(
            NUMBER,
            STRING,
            WORD,
            NEW_LINE,
            "\\(",
            "\\)",
            "\\{",
            "\\}",
            "\\[",
            "\\]",
            "==",
            "!=",
            "=",
            ">=",
            "<=",
            "<",
            ">",
            "\\+\\+",
            "--",
            "\\+",
            "-",
            "/",
            "%",
            ";",
            ".",
            ",",
            ":"
        )
    }
}