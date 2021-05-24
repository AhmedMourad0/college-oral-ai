package dev.ahmedmourad.college.oral.ai

import com.jakewharton.picnic.RowDsl
import com.jakewharton.picnic.table
import dev.ahmedmourad.college.oral.ai.Direction.*
import dev.ahmedmourad.college.oral.ai.strategies.*
import kotlin.math.abs

fun main() {

    val mat = buildGameMat()
    val target = Node(1, 3)
    val initialPosition = Node(0, 2)
    val initialDirection = X_POSITIVE

    println("UCS:")
    ucs(mat, target, initialPosition, initialDirection)

    println()
    println("Greedy:")
    greedy(mat, target, initialPosition, initialDirection, ::h1)

    println()
    println("A*:")
    aStar(mat, target, initialPosition, initialDirection, ::h1)
}

private fun ucs(
    mat: GameMat,
    target: Node,
    initialPosition: Node,
    initialDirection: Direction
) {
    val initialState = buildInitialUcsState(initialPosition, initialDirection)
    val answer = UCS().findPath(mat, initialState, target)
    mat.print(target, answer?.path.orEmpty())
    println(answer?.totalCost)
}

private fun greedy(
    mat: GameMat,
    target: Node,
    initialPosition: Node,
    initialDirection: Direction,
    heuristic: (State, Node) -> Int
) {
    val initialState = buildInitialGreedyState(initialPosition, initialDirection)
    val answer = Greedy(heuristic).findPath(mat, initialState, target)
    mat.print(target, answer?.path.orEmpty())
}

private fun aStar(
    mat: GameMat,
    target: Node,
    initialPosition: Node,
    initialDirection: Direction,
    heuristic: (State, Node) -> Int
) {
    val initialState = buildInitialAStarState(initialPosition, initialDirection)
    val answer = AStar(heuristic).findPath(mat, initialState, target)
    mat.print(target, answer?.path.orEmpty())
    println(answer?.totalCost)
}

private fun h1(current: State, target: Node): Int {

    val distanceX: Int = abs(current.position.x - target.x)
    val distanceY: Int = abs(current.position.y - target.y)

    return if (distanceX > distanceY) {
        14 * distanceY + 10 * (distanceX - distanceY)
    } else {
        14 * distanceX + 10 * (distanceY - distanceX)
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

    fun RowDsl.addCell(x: Int, y: Int) {
        if (this@print.isAnObstacle(x, y)) {
            cell("X")
        } else if (target.equals(x, y)) {
            cell("T")
        } else {
            val p = path.firstOrNull { it.target.equals(x, y) }
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

    table {
        cellStyle {
            border = true
            padding = 1
            paddingLeft = 2
            paddingRight = 2
        }
        row {
            cell(" ") {
                this.border = false
                this.paddingBottom = 0
                this.paddingTop = 0
                this.paddingLeft = 0
                this.paddingRight = 1
            }
            repeat(this@print.size.width) { x ->
                cell(x) {
                    this.border = false
                    this.paddingBottom = 0
                    this.paddingTop = 0
                }
            }
        }
        repeat(this@print.size.height) { y ->
            row {
                cell(y) {
                    this.border = false
                    this.paddingLeft = 0
                    this.paddingRight = 0
                }
                repeat(this@print.size.width) { x ->
                    addCell(x, y)
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
