package dev.ahmedmourad.college.oral.ai.state

import dev.ahmedmourad.college.oral.ai.Action
import dev.ahmedmourad.college.oral.ai.Direction
import dev.ahmedmourad.college.oral.ai.Node
import dev.ahmedmourad.college.oral.ai.State

data class GState(
    override val position: Node,
    override val direction: Direction,
    override val path: List<Action>,
    val totalCost: Int
) : State

fun buildInitialGState(
    position: Node,
    direction: Direction
): GState {
    return GState(
        position = position,
        direction = direction,
        path = listOf(Action(position, direction, 0)),
        totalCost = 0
    )
}

fun GState.takeAction(action: Action): GState {
    return GState(
        position = action.target,
        direction = action.direction,
        path = this.path + action,
        totalCost = this.totalCost + action.cost
    )
}
