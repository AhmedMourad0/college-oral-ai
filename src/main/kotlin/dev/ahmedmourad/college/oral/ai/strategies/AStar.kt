package dev.ahmedmourad.college.oral.ai.strategies

import dev.ahmedmourad.college.oral.ai.*

class AStar(
    val heuristic: (current: State, target: Node) -> Int
) : Strategy<AStarState> {
    override fun findPath(
        traversable: Traversable,
        initialState: AStarState,
        target: Node
    ): AStarState? {
        return findPathInformed(
            traversable = traversable,
            initialState = initialState,
            target = target,
            selector = { it.f },
            takeAction = { state, action -> state.takeAction(action, heuristic(state, target)) }
        )
    }
}

data class AStarState(
    override val position: Node,
    override val direction: Direction,
    override val path: List<Action>,
    val totalCost: Int,
    val h: Int
) : State {
    val f = totalCost + h
}

fun buildInitialAStarState(
    position: Node,
    direction: Direction
): AStarState {
    return AStarState(
        position = position,
        direction = direction,
        path = listOf(Action(position, direction, 0)),
        totalCost = 0,
        h = 0
    )
}

private fun AStarState.takeAction(action: Action, h: Int): AStarState {
    return AStarState(
        position = action.target,
        direction = action.direction,
        path = this.path + action,
        totalCost = this.totalCost + action.cost,
        h = h
    )
}
