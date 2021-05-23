package dev.ahmedmourad.college.oral.ai

import com.jakewharton.picnic.table
import dev.ahmedmourad.college.oral.ai.Direction.*
import dev.ahmedmourad.college.oral.ai.strategies.UCS

fun main() {
    val mat = buildGameMat()
    val initialState = buildInitialState(Node(0, 2), X_POSITIVE)
    val target = Node(2, 2)
    val answer = UCS().findPath(
        traversable = mat,
        initialState = initialState,
        target = target
    )
    mat.print(target, answer.correct?.path.orEmpty())
    println(answer.correct?.totalCost)

    answer.others.forEach { lessOptimalAnswer ->
        println()
        mat.print(target, lessOptimalAnswer.path)
        println(lessOptimalAnswer.totalCost)
    }
}

@OptIn(ExperimentalStdlibApi::class)
private fun buildGameMat(): GameMat {
    val obstacles = buildSet {
        add(Node(0, 0))
        add(Node(0, 3))
        add(Node(1, 2))
//        add(Node(3, 2))
    }
    val size = Size(5, 4)
    return GameMat(size, obstacles)
}

fun GameMat.print(target: Node, path: List<Action>) {
    table {
        cellStyle {
            border = true
            padding = 1
            paddingLeft = 2
            paddingRight = 2
        }
        repeat(this@print.size.height) { y ->
            row {
                repeat(this@print.size.width) { x ->
                    if (this@print.isAnObstacle(x, y)) {
                        cell("X")
                    } else if (target.x == x && target.y == y) {
                        cell("T")
                    } else {
                        val p = path.firstOrNull {
                            it.target.x == x && it.target.y == y
                        }
                        if (p != null) {
                            when (p.direction) {
                                X_POSITIVE -> cell("→")
                                X_NEGATIVE -> cell("←")
                                Y_POSITIVE -> cell("↓")
                                Y_NEGATIVE -> cell("↑")
                            }
                        } else {
                            cell(" ")
                        }
                    }
                }
            }
        }
    }.also(::println)
}

private fun Node.equals(x: Int, y: Int): Boolean {
    return this.x == x && this.y == y
}

private fun GameMat.isAnObstacle(x: Int, y: Int): Boolean {
    return obstacles.any { it.equals(x, y) }
}
