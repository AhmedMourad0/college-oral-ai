package dev.ahmedmourad.college.oral.ai

enum class Direction {
    X_POSITIVE, X_NEGATIVE, Y_POSITIVE, Y_NEGATIVE
}

interface State {
    val direction: Direction
    val position: Node
    val path: List<Action>
}
