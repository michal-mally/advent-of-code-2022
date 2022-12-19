package days.day_19

import util.Solver

private val regex =
    """Blueprint 1: Each ore robot costs 4 ore. Each clay robot costs 2 ore. Each obsidian robot costs 3 ore and 14 clay. Each geode robot costs 2 ore and 7 obsidian.""".trimIndent()
        .toRegex()

class Day19_1 : Solver<Sequence<String>, Int> {
    override fun solve(input: Sequence<String>): Int {
        val blueprints = input
            .map { parseBlueprint(it) }
            .toList()

        blueprints.forEach(::println)

        TODO("Not yet implemented")
    }

    private fun parseBlueprint(blueprint: String) =
        blueprint
            .substringAfter(": ")
            .splitToSequence("""\.\s?""".toRegex())
            .filter(String::isNotBlank)
            .map(::productionCost)
            .toMap()

    private fun productionCost(it: String): Pair<String, Map<String, Int>> =
        """Each (?<robotType>\w+) robot costs (?<firstCostAmount>\d+) (?<firstCostType>\w+)( and (?<secondCostAmount>\d+) (?<secondCostType>\w+))?"""
            .toRegex()
            .matchEntire(it)
            ?.groups
            ?.let {
                it["robotType"]!!.value to buildMap {
                    this[it["firstCostType"]!!.value] = it["firstCostAmount"]!!.value.toInt()
                    if (it["secondCostType"] != null && it["secondCostAmount"] != null) {
                        this[it["secondCostType"]!!.value] = it["secondCostAmount"]!!.value.toInt()
                    }
                }
            }
            ?: error("Invalid production cost: $it")

}
