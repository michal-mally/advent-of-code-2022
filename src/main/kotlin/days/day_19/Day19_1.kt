package days.day_19

import util.Solver

class Day19_1 : Solver<Sequence<String>, Int> {
    override fun solve(input: Sequence<String>) =
        input
            .map(::parseProductionCosts)
            .mapIndexed { index, blueprint -> (index + 1) * evaluate(blueprint) }
            .sum()

    private fun evaluate(productionCosts: Map<String, Map<String, Int>>): Int {
        val robots = mutableMapOf("ore" to 1).withDefault { 0 }
        val resources = mutableMapOf<String, Int>().withDefault { 0 }

        repeat(24) { minute ->
            println("== Minute ${minute + 1} ==")
            val nextProduction = decideNextProduction(productionCosts, robots, resources)
            if (nextProduction != null) {
                println(
                    "Spend ${
                        productionCosts.getValue(nextProduction).map { (resource, amount) -> "$amount $resource" }
                            .joinToString(" and ")
                    } to start building ${if (nextProduction.startsWith("o")) "an" else "a"} $nextProduction-collecting robot."
                )
                productionCosts
                    .getValue(nextProduction)
                    .forEach { (resource, cost) ->
                        resources[resource] = resources.getValue(resource) - cost
                    }
            }
            robots.forEach { (resource, amount) ->
                resources[resource] = resources.getValue(resource) + amount
                println("$amount $resource-collecting ${if (amount == 1) "robot collects" else "robots collect"} $amount $resource; you now have ${resources[resource]} $resource.")
            }
            if (nextProduction != null) {
                robots[nextProduction] = robots.getValue(nextProduction) + 1
                println("The new $nextProduction-collecting robot is ready; you now have ${robots[nextProduction]} of them.")
            }
            println()
        }

        return resources.getValue("geode")
    }

    private fun decideNextProduction(
        productionCosts: Map<String, Map<String, Int>>,
        robots: Map<String, Int>,
        resources: Map<String, Int>
    ): String? {
        fun canBeBuilt(robotType: String) =
            productionCosts
                .getValue(robotType)
                .all { (resource, amount) -> resources.getValue(resource) >= amount }

        return sequenceOf("geode", "obsidian", "clay", "ore").firstOrNull { canBeBuilt(it) }
    }

    private fun parseProductionCosts(blueprint: String) =
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
