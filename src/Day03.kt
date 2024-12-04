fun main() {
    val lines = readInput("Day03_test")

    "part 1" {

        val result1 = part1(
            listOf("xmul(2,4)%&mul[3,7]!@^do_not_mul(5,5)+mul(32,64]then(mul(11,8)mul(8,5))")
        )
        println(result1)

        check(result1 == 161)

        val result2 = part1(lines)
        println(result2)
        check(result2 == 156388521)
    }

    "part 2" {
        val result1 = part2(
            listOf("xmul(2,4)&mul[3,7]!^don't()_mul(5,5)+mul(32,64](mul(11,8)undo()?mul(8,5))")
        )
        println(result1)
        check(result1 == 48)

        val result2 = part2(lines)
        println(result2)
        check(result2 == 75920122)
    }
}


private fun part1(lines: List<String>): Int {
    return lines.sumOf { line ->
        val pairs = line.operationsFromRegex(Regex("""mul\(\d{1,3},\d{1,3}\)""")).mapNotNull { operation ->
            operation.doMultiplication()
        }
        pairs.sumOf { it }
    }
}

private fun part2(lines: List<String>): Int {
    var active = true
    return lines.sumOf { line ->
        val operations = line.operationsFromRegex(
            Regex("""mul\(\d{1,3},\d{1,3}\)|do\(\)|don't\(\)""")
        )
        val pairs = operations.mapNotNull { operation ->
            if (operation == "do()") {
                active = true
            }
            if (operation == "don't()") {
                active = false
            }
            if (operation.contains("mul") && active) {
                operation.doMultiplication()
            } else {
                null
            }
        }
        pairs.sumOf { it }
    }
}

private fun String.doMultiplication(): Int? {
    return try {
        val x = this.substringAfter("(").substringBefore(",").toInt()
        val y = this.substringBefore(")").substringAfter(",").toInt()
        x * y
    } catch (e: Exception) {
        null
    }
}

private fun String.operationsFromRegex(
    regex: Regex,
): List<String> {
    return regex
        .findAll(this)
        .map { it.value }
        .toList()
}

private fun parseStringClean(string: String): List<Int> {
    return string
        .split("mul(")
        .drop(1)
        .mapNotNull { segment ->
            val endIndex = segment.indexOf(")") + 1
            if (endIndex > 0) {
                val operationString = "mul(${segment.substring(0, endIndex)}"
                operationString.doMultiplication()
            } else {
                null
            }
        }
}

private fun parseString(string: String): List<Int> {
    var splitString = string
    val multiplications = mutableListOf<Int>()

    val operation = 4
    val comma = 1
    val indexOfSecondParenthesis = operation + comma
    val minDigits = 2
    val maxDigits = 6

    while (splitString.isNotEmpty()) {
        val mulIndex = splitString.indexOf("mul(")
        if (mulIndex < 0) {
            break
        }

        var operationString = splitString.substring(mulIndex)
        val indexOfSecond = operationString.indexOf(")")

        val isValidIndex = indexOfSecond in indexOfSecondParenthesis + minDigits..indexOfSecondParenthesis + maxDigits
        if (!isValidIndex) {
            splitString = splitString.substring(mulIndex + operation)
            continue
        }

        try {
            operationString = operationString.substring(0, indexOfSecond + 1)
            multiplications += operationString.doMultiplication() ?: throw Exception("")
        } catch (e: Exception) {
            println("false positive: $operationString")
        }
        splitString = try {
            val newString = splitString.substring(mulIndex + 7)
            if (newString.length < (operation + comma + minDigits)) {
                ""
            } else {
                newString
            }
        } catch (e: StringIndexOutOfBoundsException) {
            ""
        }
    }

    return multiplications
}
