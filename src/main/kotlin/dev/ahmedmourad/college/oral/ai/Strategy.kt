package dev.ahmedmourad.college.oral.ai

interface Strategy<S : State> {
    fun findPath(
        traversable: Traversable,
        initialState: S,
        target: Node
    ): S?
}
