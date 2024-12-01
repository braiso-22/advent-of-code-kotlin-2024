import kotlin.math.abs

fun main() {
    val testList1 = listOf(3, 4, 2, 1, 3, 3)
    val testList2 = listOf(4, 3, 5, 3, 9, 3)

    val lines = readInput("Day01_test")
    val (input1, input2) = lines.map { line ->
        val (first, second) = line.split("   ").map(String::toInt)
        first to second
    }.unzip()

    "part 1" {
        val result1 = part1(testList1, testList2)
        check(result1 == 11)

        val result2 = part1(input1, input2)
        check(result2 == 3508942)
    }

    "part 2" {
        val result1 = part2(testList1, testList2)
        check(result1 == 31)

        val result2 = part2(input1, input2)
        check(result2 == 26593248)
    }
}

private fun part1(locationIds1: List<Int>, locationIds2: List<Int>): Int {
    val locations1Ordered = locationIds1.sorted()
    val locations2Ordered = locationIds2.sorted()

    val zippedValues = locations1Ordered.zip(locations2Ordered)
    return zippedValues.sumOf { abs(it.first - it.second) }
}

private fun part2(locationIds1: List<Int>, locationIds2: List<Int>): Int {
    return locationIds1.sumOf { location ->
        val frequencyInSecondList = locationIds2.count { location == it }
        location * frequencyInSecondList
    }
}