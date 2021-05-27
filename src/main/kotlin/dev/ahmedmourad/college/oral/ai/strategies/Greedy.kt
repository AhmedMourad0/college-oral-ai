package dev.ahmedmourad.college.oral.ai.strategies

import dev.ahmedmourad.college.oral.ai.*
import dev.ahmedmourad.college.oral.ai.state.HState
import dev.ahmedmourad.college.oral.ai.state.takeAction

class Greedy(
    val heuristic: (current: State, target: Node) -> Int
) : Strategy<HState> {
    override fun findPath(
        traversable: Traversable,
        initialState: HState,
        target: Node
    ): HState? {
        return findPathImpl(
            traversable = traversable,
            initialState = initialState,
            target = target,
            selectNextState = { fringe -> fringe.minByOrNull { it.h } },
            takeAction = { state, action -> state.takeAction(action, heuristic(state, target)) }
        )
    }
}
