package dev.ahmedmourad.college.oral.ai.strategies

import dev.ahmedmourad.college.oral.ai.*

class UCS : Strategy {

    override fun findPath(
        traversable: Traversable,
        initialState: State,
        target: Node
    ): State? {

        val visitedNodes = mutableSetOf<Node>()
        val queue = mutableListOf(initialState)

        while (queue.isNotEmpty()) {

            val currentState = queue
                .filter { it.position != target }
                .minByOrNull { it.totalCost }
                ?: break

            queue.remove(currentState)

            val availableActions = traversable
                .findAvailableActions(currentState)
                .filter { it.target !in visitedNodes }

            queue.addAll(availableActions.map {
                currentState.takeAction(it)
            })

            visitedNodes.add(currentState.position)
        }

        return queue.minByOrNull { it.totalCost }
    }
}

fun State.takeAction(action: Action): State {
    return State(
        position = action.target,
        direction = action.direction,
        totalCost = this.totalCost + action.cost,
        path = this.path + action
    )
}
