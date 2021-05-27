package dev.ahmedmourad.college.oral.ai.state

import dev.ahmedmourad.college.oral.ai.Action
import dev.ahmedmourad.college.oral.ai.Direction
import dev.ahmedmourad.college.oral.ai.Node
import dev.ahmedmourad.college.oral.ai.State

data class HState(
    override val position: Node,
    override val direction: Direction,
    override val path: List<Action>,
    val h: Int
) : State

fun buildInitialHState(
    position: Node,
    direction: Direction
): HState {
    return HState(
        position = position,
        direction = direction,
        path = listOf(Action(position, direction, 0)),
        h = 0
    )
}

fun HState.takeAction(action: Action, h: Int): HState {
    return HState(
        position = action.target,
        direction = action.direction,
        path = this.path + action,
        h = h
    )
}
