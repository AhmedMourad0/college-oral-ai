package dev.ahmedmourad.college.oral.ai

enum class Direction {
    X_POSITIVE, X_NEGATIVE, Y_POSITIVE, Y_NEGATIVE
}

data class State(
    val position: Node,
    val direction: Direction,
    val totalCost: Int,
    val path: List<Action>
)

fun buildInitialState(
    position: Node,
    direction: Direction
): State {
    return State(
        position = position,
        direction = direction,
        totalCost = 0,
        path = listOf(Action(position, direction, 0))
    )
}