package days.day_19

import util.Solver
import util.map.addValues
import util.map.minusValues
import util.pair.map
import kotlin.math.min

class Day19_2 : Solver<Sequence<String>, Int> {

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

        fun canBeBuilt() =
            sequenceOf("ore", "clay", "obsidian", "geode")
                .filter { canBeBuilt(it) }

    }

    override fun solve(input: Sequence<String>) =
        input
            .take(3)
            .map(::parseProductionCosts)
            .onEachIndexed { index, _ -> println("Blueprint: ${index + 1}") }
            .map { blueprint -> evaluate(blueprint) }
            .reduce(Int::times)

    private fun evaluate(productionCosts: Map<String, Map<String, Int>>): Int {
        var factory = Factory(productionCosts)

        val totalMinutes = 32
        repeat(totalMinutes) { minute ->
            println("== Minute ${minute + 1} ==")
            val nextProductions = nextProductions(min(24, totalMinutes - minute), factory)
            println(nextProductions)
            val nextProduction = nextProductions.removeFirst()
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

    private fun nextProductions(minutesLeft: Int, factory: Factory): ArrayDeque<String?> {
        if (factory.canBeBuilt().none()) {
            return ArrayDeque(listOf(null))
        }

        return ArrayDeque(bestOutcome(minutesLeft, factory).first.reversed())
    }

    private fun bestOutcome(
        minutesLeft: Int,
        factory: Factory,
    ): Pair<List<String?>, Int> {
        if (minutesLeft == 0) {
            return emptyList<String?>() to factory.resources.getValue("geode")
        }

        if (factory.canBeBuilt("geode")  && factory.productionCosts.getValue("geode").all { (resource, amount) -> factory.robots.getValue(resource) >= amount }) {
            val robotType = "geode"
            val newFactory = factory
                .consumeRobotCosts(robotType)
                .produceResources()
                .produceRobot(robotType)
            return bestOutcome(minutesLeft - 1, newFactory).map { moves, value -> moves + robotType to value }
        }

        return sequenceOf("geode", "obsidian", "clay", "ore")
            .filter { factory.canBeBuilt(it) && minutesLeft > 1 }
            .filterNot { resource -> factory.robots.getValue(resource) >= factory.productionCosts.values.maxOf { it.getOrDefault(resource, 0) } }

            .filterNot {
                factory
                    .productionCosts
                    .all { (_, costs) -> costs.getOrDefault(it, 0) * 2 < factory.resources.getValue(it) }
            }
//            .filterNot {
//                factory
//                    .productionCosts
//                    .all { (_, costs) -> costs.getOrDefault(it, 0) <= factory.robots.getValue(it) }
//            }
            .map { robotType ->
                val newFactory = factory
                    .consumeRobotCosts(robotType)
                    .produceResources()
                    .produceRobot(robotType)
                bestOutcome(minutesLeft - 1, newFactory).map { moves, value -> moves + robotType to value }
            }
            .let {
                sequence {
                    yieldAll(it)
                    yield(
                        bestOutcome(
                            minutesLeft - 1,
                            factory.produceResources()
                        ).map { moves, value -> moves + null to value }
                    )
                    if (factory.canBeBuilt("geode")) {
                        val robotType = "geode"
                        val newFactory = factory
                            .consumeRobotCosts(robotType)
                            .produceResources()
                            .produceRobot(robotType)
                        yield(bestOutcome(minutesLeft - 1, newFactory).map { moves, value -> moves + robotType to value })
                    }
                }
            }
            .maxBy { it.second }
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
