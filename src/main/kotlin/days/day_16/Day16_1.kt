package days.day_16

import util.Solver
import util.graph.Edge
import util.graph.Graph
class Day16_1 : Solver<Sequence<String>, Int> {
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

        return best(valves, distances, valves.find { it.name == "AA" }!!)
    }

    private fun best(
        valves: List<Valve>,
        distances: Map<Pair<String, String>, Int>,
        currentValve: Valve,
        alreadyOpened: Set<String> = emptySet(),
        minutesLeft: Int = 30,
        path: List<String> = emptyList()
    ): Int {
        if (minutesLeft <= 1) return 0

        val newPath = path + currentValve.name
        //println(newPath)
        val byOpeningCurrentValve = if (currentValve.flowRate > 0 && currentValve.name !in alreadyOpened) {
            (minutesLeft - 1) * currentValve.flowRate + best(
                valves,
                distances,
                currentValve,
                alreadyOpened + currentValve.name,
                minutesLeft - 1,
                newPath
            )
        } else {
            0
        }

        val x = valves
            .asSequence()
            .filter { it != currentValve }
            .filter { it.flowRate > 0 }
            .filter { it.name !in alreadyOpened }
            .maxOfOrNull { valve -> valve.flowRate * (minutesLeft - distances[currentValve.name to valve.name]!!) }
            ?: 0

        if (byOpeningCurrentValve > x) return byOpeningCurrentValve

        return valves
            .asSequence()
            .filter { it.flowRate > 0 }
            .filter { it.name !in alreadyOpened }
            .filter { it != currentValve }
            .maxOfOrNull {
                best(
                    valves,
                    distances,
                    it,
                    alreadyOpened,
                    minutesLeft - distances.getValue(currentValve.name to it.name),
                    newPath
                )
            }
            ?: 0
    }

    private fun valve(it: String) =
        (regex.matchEntire(it) ?: throw IllegalArgumentException("Invalid input: $it"))
            .destructured
            .let { (name, flowRate, tunnels) -> Valve(name, flowRate.toInt(), tunnels.split(", ")) }

    private data class Valve(val name: String, val flowRate: Int, val tunnels: List<String>)

    private val regex = """Valve ([A-Z]+) has flow rate=(\d+); tunnels? leads? to valves? ([A-Z]+(, [A-Z]+)*)""".toRegex()

}
