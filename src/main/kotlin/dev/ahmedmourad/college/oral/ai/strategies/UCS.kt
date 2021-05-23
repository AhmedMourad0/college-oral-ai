package dev.ahmedmourad.college.oral.ai.strategies

import dev.ahmedmourad.college.oral.ai.*
import java.util.*

class UCS : Strategy {
    override fun findPath(
        traversable: Traversable,
        initialState: State,
        target: Node
    ): Answer {

        val visitedNodes = mutableSetOf<Node>()

        val queue = TreeSet<State> { s1, s2 ->
            s1.totalCost.compareTo(s2.totalCost)
        }

        val possibleAnswers = mutableSetOf<State>()

        queue.add(initialState)

        while (queue.isNotEmpty()) {

            val currentState = queue.firstOrNull {
                it.position != target
            } ?: break

            queue.remove(currentState)

            val availableActions = traversable
                .findAvailableActions(currentState)
                .filter { it.target !in visitedNodes }

            val finishingMove = availableActions.firstOrNull { it.target == target }
            if (finishingMove != null) {
                possibleAnswers.add(currentState.takeAction(finishingMove))
            } else {
                queue.addAll(availableActions.map {
                    currentState.takeAction(it)
                })
            }

            visitedNodes.add(currentState.position)
        }

        val correct = possibleAnswers.minByOrNull {
            it.totalCost
        } ?: return Answer(null)

        return Answer(correct, possibleAnswers.minus(correct))
    }
}
