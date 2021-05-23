package dev.ahmedmourad.college.oral.ai.strategies

import dev.ahmedmourad.college.oral.ai.*

class UCS : Strategy {
    override fun findPath(
        traversable: Traversable,
        initialState: State,
        target: Node
    ): Answer {

        val visitedNodes = mutableSetOf<Node>()
        val queue = mutableSetOf(initialState)

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

        val correct = queue
            .minByOrNull { it.totalCost }
            ?: return Answer(null)

        return Answer(correct, queue.minus(correct))
    }
}
