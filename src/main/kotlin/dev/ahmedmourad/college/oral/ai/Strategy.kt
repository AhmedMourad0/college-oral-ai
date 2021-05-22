package dev.ahmedmourad.college.oral.ai

interface Strategy {
    fun findPath(
        traversable: Traversable,
        initialState: State,
        target: Node
    ): State?
}
