package dev.ahmedmourad.college.oral.ai.strategies

import dev.ahmedmourad.college.oral.ai.*
import dev.ahmedmourad.college.oral.ai.state.GState
import dev.ahmedmourad.college.oral.ai.state.takeAction

class DFS : Strategy<GState> {
    override fun findPath(
        traversable: Traversable,
        initialState: GState,
        target: Node
    ): GState? {
        return findPathImpl(
            traversable = traversable,
            initialState = initialState,
            target = target,
            selectNextState = { fringe -> fringe.lastOrNull() },
            takeAction = { state, action -> state.takeAction(action) }
        )
    }
}
