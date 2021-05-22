package dev.ahmedmourad.college.oral.ai

interface Traversable {
    fun findAvailableActions(state: State): List<Action>
}
