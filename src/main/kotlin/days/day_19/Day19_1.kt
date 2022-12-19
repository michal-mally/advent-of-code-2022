package days.day_19

import util.Solver
import util.map.addValues
import util.map.minusValues
import kotlin.math.min

class Day19_1 : Solver<Sequence<String>, Int> {

    data class CacheKey(
        val minutesLeft: Int,
        val robots: Map<String, Int>,
        val resources: Map<String, Int>,
    )

    val cache = mutableMapOf<CacheKey, Pair<String?, Int>>()

    data class Factory(
        val productionCosts: Map<String, Map<String, Int>>,
        val robots: Map<String, Int> = mapOf("ore" to 1, "clay" to 0, "obsidian" to 0, "geode" to 0),
        val resources: Map<String, Int> = mapOf("ore" to 0, "clay" to 0, "obsidian" to 0, "geode" to 0),
    ) {

        fun consumeRobotCosts(robotType: String) =
            this.copy(
                resources = resources.minusValues(productionCosts.getValue(robotType)),
            )

        fun produceResources() =
            this.copy(
                resources = resources.addValues(robots)
            )

        fun produceRobot(robotType: String) =
            this.copy(
                robots = robots.addValues(mapOf(robotType to 1)),
            )

        fun canBeBuilt(robotType: String) =
            productionCosts
                .getValue(robotType)
                .all { (resource, amount) -> resources.getValue(resource) >= amount }

    }

    override fun solve(input: Sequence<String>) =
        input
            .map(::parseProductionCosts)
            .mapIndexed { index, blueprint -> (index + 1) * evaluate(blueprint) }
            .sum()

    private fun evaluate(productionCosts: Map<String, Map<String, Int>>): Int {
        var factory = Factory(productionCosts)

        repeat(24) { minute ->
            println("== Minute ${minute + 1} ==")
//            println("productionCosts: $productionCosts")
//            println("robots: $robots")
//            println("resources: $resources")
            val nextProduction = bestOutcome(min(24 - minute + 1, 18), factory).first
            if (nextProduction != null) {
                println(
                    "Spend ${
                        productionCosts.getValue(nextProduction).map { (resource, amount) -> "$amount $resource" }
                            .joinToString(" and ")
                    } to start building ${if (nextProduction.startsWith("o")) "an" else "a"} $nextProduction-collecting robot."
                )
                factory = factory.consumeRobotCosts(nextProduction)
            }
            factory = factory.produceResources()
            factory
                .robots
                .filter { (_, amount) -> amount > 0 }
                .forEach { (resource, amount) ->
                println("$amount $resource-collecting ${if (amount == 1) "robot collects" else "robots collect"} $amount $resource; you now have ${factory.resources[resource]} $resource.")
            }
            if (nextProduction != null) {
                factory = factory.produceRobot(nextProduction)
                println("The new $nextProduction-collecting robot is ready; you now have ${factory.robots[nextProduction]} of them.")
            }
            println()
        }

        return factory.resources.getValue("geode")
    }

    private fun bestOutcome(
        minutesLeft: Int,
        factory: Factory,
    ): Pair<String?, Int> {
        if (minutesLeft == 0) {
            return null to factory.resources.getValue("geode")
        }

//        if (cache.containsKey(CacheKey(minutesLeft, robots, resources))) {
//            return cache.getValue(CacheKey(minutesLeft, robots, resources))
//        }

        if (factory.canBeBuilt("geode")) {
            val robotType = "geode"
            val newFactory = factory
                .consumeRobotCosts(robotType)
                .produceResources()
                .produceRobot(robotType)
            return robotType to bestOutcome(minutesLeft - 1, newFactory).second
        }

        return sequenceOf("obsidian", "clay", "ore")
            .filter { factory.canBeBuilt(it) }
            .map { robotType ->
                val newFactory = factory
                    .consumeRobotCosts(robotType)
                    .produceResources()
                    .produceRobot(robotType)
                robotType to bestOutcome(minutesLeft - 1, newFactory).second
            }
            .let {
                sequence {
                    yieldAll(it)
                    yield(
                        null to bestOutcome(
                            minutesLeft - 1,
                            factory.produceResources()
                        ).second
                    )
                }
            }
            .maxBy { it.second }
//            .also { cache[CacheKey(minutesLeft, robots, resources)] = it }
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
