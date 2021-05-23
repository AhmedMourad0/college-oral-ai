package dev.ahmedmourad.college.oral.ai

data class Answer(val correct: State?, val others: Set<State> = emptySet())

interface Strategy {
    fun findPath(
        traversable: Traversable,
        initialState: State,
        target: Node
    ): Answer
}

fun State.takeAction(action: Action): State {
    return State(
        position = action.target,
        direction = action.direction,
        totalCost = this.totalCost + action.cost,
        path = this.path + action
    )
}
