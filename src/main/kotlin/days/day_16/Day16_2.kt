package days.day_16

import util.Solver
import util.graph.Edge
import util.graph.Graph
import util.list.cartesianProduct
import util.pair.map
import util.pair.toPair

class Day16_2 : Solver<Sequence<String>, Int> {

    private data class PlayerPosition(
        val valve: String,
        val toValve: Int = 0,
    ) {
        context(Context, StepInput) fun possibleMoves(): List<PlayerPosition?> {
            check(toValve >= 0)
            return if (toValve > 0) {
                listOf(copy(toValve = toValve - 1))
            } else {
                listOf(null) + valvesToOpen()
                    .filterNot { it.name == valve }
                    .map { to -> PlayerPosition(to.name, distances[valve to to.name]!! - 1) }
            }
        }
    }

    private data class StepInput(
        val minutesLeft: Int,
        val players: List<PlayerPosition>,
        val alreadyOpened: Set<String> = emptySet(),
    )

    private class Context(
        val valves: List<Valve>,
        val distances: Map<Pair<String, String>, Int>,
        var currentMax: Int,
        val cache: MutableMap<StepInput, Int>
    ) {
        context(StepInput) fun valvesToOpen() =
            valves
                .filter { it.flowRate > 0 }
                .filterNot { it.name in this@StepInput.alreadyOpened }
                .sortedByDescending { it.flowRate }
    }

    override fun solve(input: Sequence<String>): Int {
        val valves = input.map { valve(it) }.toList()

        val graph = valves
            .asSequence()
            .flatMap { valve -> valve.tunnels.map { tunnel -> Edge(valve.name to tunnel) } }
            .toSet()
            .let { Graph(it) }

        val distances = valves
            .asSequence()
            .map { valve -> valve to graph.distancesFrom(valve.name) }
            .flatMap { (valve, distances) ->
                distances.asSequence().map { (name, distance) -> (valve.name to name) to distance }
            }
            .associate { it }

        val receiver = Context(
            valves,
            distances,
            0,
            mutableMapOf()
        )
        Thread {
            while (true) {
                Thread.sleep(5000)
                println(receiver.currentMax)
            }
        }.apply { isDaemon = true }.start()

        return with(receiver) {
            best(StepInput(26, listOf(PlayerPosition("AA"), PlayerPosition("AA"))))
        }
    }

    context(Context) private fun best(stepInput: StepInput, alreadyAccumulated: Int = 0): Int {
        if (stepInput.minutesLeft == 0) {
            return 0
        }

//        if (stepInput in cache) {
//            return cache[stepInput]!!
//        }

        fun potentialLeft(): Int {
            val toList = valves
                .asSequence()
                .filter { it.flowRate > 0 }
                .filterNot { it.name in stepInput.alreadyOpened }
                .map { it.flowRate }
                .sortedDescending()
                .chunked(2)
                .map { it.sum() }
                .toList()

            var potential = 0
            for (i in 1..stepInput.minutesLeft step 2) {
                potential += (stepInput.minutesLeft - i) * toList.getOrElse(i / 2) { 0 }
            }

            return potential
        }

        val maxPotential = alreadyAccumulated +
                valves
                    .filter { it.name in stepInput.alreadyOpened }
                    .sumOf { it.flowRate * stepInput.minutesLeft } +
                potentialLeft()

        if (maxPotential <= currentMax) {
            return 0
        }

        // accumulated
        // + already open * minutes left
        // + not open sorted by flow rate (chunked by 2)
        // if currentMax is much higher than accumulated, we can stop

        val accumulated = stepInput.alreadyOpened.sumOf { opened -> valves.find { it.name == opened }!!.flowRate }
        return (accumulated + with(stepInput) {
            players
                .map { it.possibleMoves() }
                .toPair()
                .map { first, second -> first cartesianProduct second }
                .maxOf { (first, second) ->
                    val newPlayerPositions = mutableListOf<PlayerPosition>()
                    val newAlreadyOpened = alreadyOpened.toMutableSet()

                    if (first == null) {
                        newAlreadyOpened += players.first().valve
                    }

                    if (first != null) {
                        newPlayerPositions.add(first)
                    } else {
                        newPlayerPositions.add(players.first())
                    }

                    if (second == null) {
                        newAlreadyOpened += players.last().valve
                    }

                    if (second != null) {
                        newPlayerPositions.add(second)
                    } else {
                        newPlayerPositions.add(players.last())
                    }

                    best(
                        stepInput.copy(
                            minutesLeft = minutesLeft - 1,
                            players = newPlayerPositions.sortedBy { it.valve },
                            alreadyOpened = newAlreadyOpened,
                        ),
                        alreadyAccumulated + accumulated
                    )
                }
        }).also {
            //cache[stepInput] = it
            currentMax = maxOf(currentMax, it)
        }
    }


    private fun valve(it: String) =
        (regex.matchEntire(it) ?: throw IllegalArgumentException("Invalid input: $it"))
            .destructured
            .let { (name, flowRate, tunnels) -> Valve(name, flowRate.toInt(), tunnels.split(", ")) }

    private data class Valve(val name: String, val flowRate: Int, val tunnels: List<String>)

    private val regex =
        """Valve ([A-Z]+) has flow rate=(\d+); tunnels? leads? to valves? ([A-Z]+(, [A-Z]+)*)""".toRegex()

}
