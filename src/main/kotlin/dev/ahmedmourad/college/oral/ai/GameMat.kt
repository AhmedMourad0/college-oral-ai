package dev.ahmedmourad.college.oral.ai

data class Size(val width: Int, val height: Int) {
    val maxX = width - 1
    val maxY = height - 1
}

class GameMat(
    val size: Size,
    val obstacles: Set<Node>
) : Traversable {

    @OptIn(ExperimentalStdlibApi::class)
    override fun findAvailableActions(state: State): List<Action> {
        return buildList {
            if (this@GameMat.size.width > 1) {
                addAll(findActionsOnX(state))
            }
            if (this@GameMat.size.height > 1) {
                addAll(findActionsOnY(state))
            }
        }.filter { it.target !in obstacles }
    }

    private fun findActionsOnX(state: State): List<Action> {

        val availableActions = mutableListOf<Action>()

        if (state.position.x < size.maxX) {
            val cost = if (state.direction == Direction.X_POSITIVE) 1 else 2
            availableActions.add(
                Action(
                    target = Node(state.position.x + 1, state.position.y),
                    direction = Direction.X_POSITIVE,
                    cost = cost
                )
            )
        }

        if (state.position.x > 0) {
            val cost = if (state.direction == Direction.X_NEGATIVE) 1 else 2
            availableActions.add(
                Action(
                    target = Node(state.position.x - 1, state.position.y),
                    direction = Direction.X_NEGATIVE,
                    cost = cost
                )
            )
        }

        return availableActions
    }

    private fun findActionsOnY(state: State): List<Action> {

        val availableActions = mutableListOf<Action>()

        if (state.position.y < size.maxY) {
            val cost = if (state.direction == Direction.Y_POSITIVE) 1 else 2
            availableActions.add(
                Action(
                    target = Node(state.position.x, state.position.y + 1),
                    direction = Direction.Y_POSITIVE,
                    cost = cost
                )
            )
        }

        if (state.position.y > 0) {
            val cost = if (state.direction == Direction.Y_NEGATIVE) 1 else 2
            availableActions.add(
                Action(
                    target = Node(state.position.x, state.position.y - 1),
                    direction = Direction.Y_NEGATIVE,
                    cost = cost
                )
            )
        }

        return availableActions
    }
}
