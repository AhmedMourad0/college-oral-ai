package dev.ahmedmourad.college.oral.ai

data class Answer(val correct: State?, val others: Set<State> = emptySet())

interface Strategy {
    fun findPath(
        traversable: Traversable,
        initialState: State,
        target: Node
    ): Answer
}
