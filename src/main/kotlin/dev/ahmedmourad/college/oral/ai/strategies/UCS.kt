package dev.ahmedmourad.college.oral.ai.strategies

import dev.ahmedmourad.college.oral.ai.*

class UCS : Strategy<UcsState> {
    override fun findPath(
        traversable: Traversable,
        initialState: UcsState,
        target: Node
    ): UcsState? {
        return findPathInformed(
            traversable = traversable,
            initialState = initialState,
            target = target,
            selector = { it.totalCost },
            takeAction = { state, action -> state.takeAction(action) }
        )
    }
}

data class UcsState(
    override val position: Node,
    override val direction: Direction,
    override val path: List<Action>,
    val totalCost: Int
) : State

fun buildInitialUcsState(
    position: Node,
    direction: Direction
): UcsState {
    return UcsState(
        position = position,
        direction = direction,
        path = listOf(Action(position, direction, 0)),
        totalCost = 0
    )
}

private fun UcsState.takeAction(action: Action): UcsState {
    return UcsState(
        position = action.target,
        direction = action.direction,
        path = this.path + action,
        totalCost = this.totalCost + action.cost
    )
}
