package dev.ahmedmourad.college.oral.ai.strategies

import dev.ahmedmourad.college.oral.ai.*
import dev.ahmedmourad.college.oral.ai.strategies.state.FState
import dev.ahmedmourad.college.oral.ai.strategies.state.takeAction

class AStar(
    val heuristic: (current: State, target: Node) -> Int
) : Strategy<FState> {
    override fun findPath(
        traversable: Traversable,
        initialState: FState,
        target: Node
    ): FState? {
        return findPathImpl(
            traversable = traversable,
            initialState = initialState,
            target = target,
            selectNextState = { fringe -> fringe.minByOrNull { it.f } },
            takeAction = { state, action -> state.takeAction(action, heuristic(state, target)) }
        )
    }
}
