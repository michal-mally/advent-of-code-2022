package days.day_16

import util.Solver
import util.graph.Edge
import util.graph.Graph

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
                val shouldOpen =
                    if (valve !in alreadyOpened && valves.find { it.name == valve }!!.flowRate > 0)
                        listOf(null)
                    else
                        emptyList()
                shouldOpen + valvesToOpen()
                    .filterNot { it.name == valve }
                    .map { to -> PlayerPosition(to.name, distances[valve to to.name]!! - 1) }
            }
        }
    }

    private data class StepInput(
        val minutesLeft: Int,
        val players: Set<PlayerPosition>,
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

        return with(
            Context(
                valves,
                distances,
                0,
                mutableMapOf()
            )
        ) {
            best(StepInput(30, setOf(PlayerPosition("AA"))))
        }
    }

    context(Context) private fun best(stepInput: StepInput): Int {
        if (stepInput.minutesLeft == 0) {
            return 0
        }

        if (stepInput in cache) {
            return cache[stepInput]!!
        }

        // if currentMax is much higher than accumulated, we can stop

        val accumulated = stepInput.alreadyOpened.sumOf { opened -> valves.find { it.name == opened }!!.flowRate }
        return (accumulated + with(stepInput) {
            players
                .first()
                .possibleMoves()
                .maxOfOrNull { move ->
                    if (move == null) {
                        if (players.first().valve !in alreadyOpened) {
                            best(
                                copy(
                                    alreadyOpened = alreadyOpened + players.first().valve,
                                    minutesLeft = minutesLeft - 1
                                )
                            )
                        } else {
                            best(copy(minutesLeft = minutesLeft - 1))
                        }
                    } else {
                        best(copy(players = setOf(move), minutesLeft = minutesLeft - 1))
                    }
                }
                ?: best(copy(minutesLeft = minutesLeft - 1))
        }).also { cache[stepInput] = it }
    }


    private fun valve(it: String) =
        (regex.matchEntire(it) ?: throw IllegalArgumentException("Invalid input: $it"))
            .destructured
            .let { (name, flowRate, tunnels) -> Valve(name, flowRate.toInt(), tunnels.split(", ")) }

    private data class Valve(val name: String, val flowRate: Int, val tunnels: List<String>)

    private val regex =
        """Valve ([A-Z]+) has flow rate=(\d+); tunnels? leads? to valves? ([A-Z]+(, [A-Z]+)*)""".toRegex()

}
