import kotlin.math.abs

typealias Levels = List<Int>

fun Levels.areSave(): Boolean {
    if (this != this.sorted() && this != this.sortedDescending()) {
        return false
    }
    return this.zipWithNext { a, b ->
        abs(a - b) in 1..3
    }.all { inRange -> inRange }
}

data class Report(val levels: Levels) {
    fun isSave(): Boolean {
        return levels.areSave()
    }

    fun isSaveWithTolerance(): Boolean {
        if (levels.areSave()) {
            return true
        }
        val anySave = this.levels.indices.any { index ->
            val modifiedLevels = this.levels.toMutableList().apply { removeAt(index) }
            modifiedLevels.areSave()
        }
        return anySave
    }
}

fun main() {
    val r1 = Report(listOf(7, 6, 4, 2, 1))
    val r2 = Report(listOf(1, 2, 7, 8, 9))
    val r3 = Report(listOf(9, 7, 6, 2, 1))
    val r4 = Report(listOf(1, 3, 2, 4, 5))
    val r5 = Report(listOf(8, 6, 4, 4, 1))
    val r6 = Report(listOf(1, 3, 6, 7, 9))

    val lines = readInput("Day02_test")
    val inputReports: List<Report> = lines.map { line ->
        val reportItems = Report(line.split(" ").map(String::toInt))
        reportItems
    }

    "part 1" {
        val result1 = part1(
            listOf(r1, r2, r3, r4, r5, r6)
        )
        check(result1 == 2)

        val result2 = part1(inputReports)
        check(result2 == 686)
    }

    "part 2" {
        val result1 = part2(
            listOf(r1, r2, r3, r4, r5, r6)
        )
        check(result1 == 4)

        val result2 = part2(inputReports)
        check(result2 == 717)
    }
}

private fun part1(reports: List<Report>): Int {
    return reports.count { report ->
        report.isSave()
    }
}


private fun part2(reports: List<Report>): Int {
    return reports.count { report ->
        report.isSaveWithTolerance()
    }
}