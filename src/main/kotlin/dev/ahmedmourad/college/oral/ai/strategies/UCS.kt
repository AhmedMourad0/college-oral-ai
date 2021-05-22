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
        var currentState: State

        while (queue.isNotEmpty()) {
            currentState = queue.filter { it.position != target }
                .minByOrNull { it.totalCost }
                ?: break
            queue.remove(currentState)
            val availableActions = traversable.findAvailableActions(currentState)
                .filter { it.target !in visitedNodes }
            queue.addAll(availableActions.map {
                takeAction(currentState, it)
            })
            visitedNodes.add(currentState.position)
        }

        return queue.minByOrNull { it.totalCost }
    }

    private fun takeAction(state: State, action: Action): State {
        return State(
            position = action.target,
            direction = action.direction,
            totalCost = state.totalCost + action.cost,
            path = state.path + action
        )
    }
}
