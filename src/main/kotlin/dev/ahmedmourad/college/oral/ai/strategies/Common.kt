package dev.ahmedmourad.college.oral.ai.strategies

import dev.ahmedmourad.college.oral.ai.Action
import dev.ahmedmourad.college.oral.ai.Node
import dev.ahmedmourad.college.oral.ai.State
import dev.ahmedmourad.college.oral.ai.Traversable

fun <S : State> findPathInformed(
    traversable: Traversable,
    initialState: S,
    target: Node,
    selector: (S) -> Int,
    takeAction: (S, Action) -> S
): S? {

    val visitedNodes = mutableSetOf<Node>()
    val fringe = mutableListOf<S>()

    fringe.add(initialState)

    while (fringe.isNotEmpty()) {

        val currentState = fringe.minByOrNull { selector(it) } ?: break

        fringe.remove(currentState)

        val availableActions = traversable
            .findAvailableActions(currentState)
            .filter { it.target !in visitedNodes }

        val finishingMove = availableActions.firstOrNull { it.target == target }
        if (finishingMove != null) {
            return takeAction(currentState, finishingMove)
        } else {
            fringe.addAll(availableActions.map { action ->
                takeAction(currentState, action)
            })
        }

        visitedNodes.add(currentState.position)
    }

    return null
}
