package codes.chrishorner.weatherchallenge

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlin.math.sin
import kotlin.random.Random

private const val LEAF_COUNT = 100
private const val MIN_SPAWN_THRESHOLD_MS = 500

@Composable
fun FloatingLeaves(modifier: Modifier = Modifier) {
    val simulation = remember { LeavesSimulation() }
    val leafPainter = painterResource(R.drawable.leaf1)
    val frameTime by frameTimeMillis()

    BoxWithConstraints(modifier = modifier) {
        simulation.update(frameTime, maxWidth, maxHeight)

        val leaves = simulation.leaves

        for (index in leaves.indices) {
            val leaf = leaves[index]

            Image(
                painter = leafPainter,
                contentDescription = "",
                modifier = Modifier
                    .size(24.dp)
                    .offset(leaf.x, leaf.y)
                    .rotate(leaf.rotation)
            )
        }
    }
}


private data class Leaf(
    var x: Dp = 0.dp,
    var y: Dp = 0.dp,
    var xSpeed: Dp = 0.dp,
    var ySpeed: Dp = 0.dp,
    var rotation: Float = 0f,
    var rotationSpeed: Float = 0f,
    var period: Float = 0f,
    var amplitude: Float = 0f
)

private class LeavesSimulation {
    private val activeLeaves = ArrayList<Leaf>(LEAF_COUNT)
    private val pool = ArrayDeque<Leaf>(LEAF_COUNT).apply {
        repeat(LEAF_COUNT) { add(Leaf()) }
    }

    private var lastUpdateTime = 0L
    private var lastSpawnTime = 0L
    private var worldWidth = 0.dp
    private var worldHeight = 0.dp

    val leaves: List<Leaf> get() = activeLeaves

    fun update(time: Long, width: Dp, height: Dp) {
        worldWidth = width
        worldHeight = height

        if (time - lastSpawnTime > MIN_SPAWN_THRESHOLD_MS && pool.isNotEmpty()) {
            lastSpawnTime = time

            val leaves = pool.removeFirst().apply {
                x = 0.dp
                y = 0.dp
                xSpeed = 96.dp + 96.dp * Random.nextFloat()
                ySpeed = 48.dp + 48.dp * Random.nextFloat()
                rotation = Random.nextFloat() * 360f
                rotationSpeed =
                    0.5f + Random.nextFloat() * 180 * if (Random.nextBoolean()) 1 else -1
                period = 0.05f + Random.nextFloat() * 0.6f
                amplitude = 0.003f + Random.nextFloat() * 0.05f
            }

            activeLeaves.add(leaves)
        }
        val deltaSeconds = (time - lastUpdateTime) / 1000f

        for (index in activeLeaves.indices.reversed()) {
            with(activeLeaves[index]) {
                rotation += rotationSpeed * deltaSeconds
                x += xSpeed * deltaSeconds
                y += (period * sin(amplitude * x.value) + (ySpeed.value * deltaSeconds)).dp

                if (x > worldWidth) {
                    activeLeaves.remove(this)
                    pool.add(this)
                }
            }
        }

        lastUpdateTime = time
    }
}
