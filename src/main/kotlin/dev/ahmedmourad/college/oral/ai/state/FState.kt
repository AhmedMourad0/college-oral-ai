package dev.ahmedmourad.college.oral.ai.state

import dev.ahmedmourad.college.oral.ai.Action
import dev.ahmedmourad.college.oral.ai.Direction
import dev.ahmedmourad.college.oral.ai.Node
import dev.ahmedmourad.college.oral.ai.State

data class FState(
    override val position: Node,
    override val direction: Direction,
    override val path: List<Action>,
    val totalCost: Int,
    val h: Int
) : State {
    val f = totalCost + h
}

fun buildInitialFState(
    position: Node,
    direction: Direction
): FState {
    return FState(
        position = position,
        direction = direction,
        path = listOf(Action(position, direction, 0)),
        totalCost = 0,
        h = 0
    )
}

fun FState.takeAction(action: Action, h: Int): FState {
    return FState(
        position = action.target,
        direction = action.direction,
        path = this.path + action,
        totalCost = this.totalCost + action.cost,
        h = h
    )
}
