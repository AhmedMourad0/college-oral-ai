package dev.ahmedmourad.college.oral.ai.strategies

import dev.ahmedmourad.college.oral.ai.*

class Greedy(
    val heuristic: (current: State, target: Node) -> Int
) : Strategy<GreedyState> {
    override fun findPath(
        traversable: Traversable,
        initialState: GreedyState,
        target: Node
    ): GreedyState? {
        return findPathInformed(
            traversable = traversable,
            initialState = initialState,
            target = target,
            selector = { it.h },
            takeAction = { state, action -> state.takeAction(action, heuristic(state, target)) }
        )
    }
}

data class GreedyState(
    override val position: Node,
    override val direction: Direction,
    override val path: List<Action>,
    val h: Int
) : State

fun buildInitialGreedyState(
    position: Node,
    direction: Direction
): GreedyState {
    return GreedyState(
        position = position,
        direction = direction,
        path = listOf(Action(position, direction, 0)),
        h = 0
    )
}

private fun GreedyState.takeAction(action: Action, h: Int): GreedyState {
    return GreedyState(
        position = action.target,
        direction = action.direction,
        path = this.path + action,
        h = h
    )
}
