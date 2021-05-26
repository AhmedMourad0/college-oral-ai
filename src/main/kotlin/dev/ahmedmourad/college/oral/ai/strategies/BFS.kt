package dev.ahmedmourad.college.oral.ai.strategies

import dev.ahmedmourad.college.oral.ai.*
import dev.ahmedmourad.college.oral.ai.strategies.state.GState
import dev.ahmedmourad.college.oral.ai.strategies.state.takeAction

class BFS : Strategy<GState> {
    override fun findPath(
        traversable: Traversable,
        initialState: GState,
        target: Node
    ): GState? {
        return findPathImpl(
            traversable = traversable,
            initialState = initialState,
            target = target,
            selectCurrentState = { fringe -> fringe.firstOrNull() },
            takeAction = { state, action -> state.takeAction(action) }
        )
    }
}