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
private const val MIN_SPAWN_THRESHOLD_MS = 200L
private const val SPAWN_VARIANCE = 300L

@Composable
fun FloatingLeaves(modifier: Modifier = Modifier) {
    val simulation = remember { LeavesSimulation() }
    val frameTime by frameTimeMillis()

    BoxWithConstraints(modifier = modifier) {
        simulation.update(frameTime, maxWidth, maxHeight)
        val leaves = simulation.leaves

        for (index in leaves.indices) {
            val leaf = leaves[index]
            Image(
                painter = painterResource(leaf.drawableRes),
                contentDescription = "",
                modifier = Modifier
                    .size(width = leaf.width, height = leaf.height)
                    .offset(leaf.x, leaf.y)
                    .rotate(leaf.rotation)
            )
        }
    }
}

private val leafDrawableIds = intArrayOf(
    R.drawable.leaf1,
    R.drawable.leaf2,
    R.drawable.leaf3,
    R.drawable.leaf4,
    R.drawable.leaf5,
    R.drawable.leaf6,
    R.drawable.leaf7
)

private data class Leaf(
    var drawableRes: Int = leafDrawableIds[0],
    var x: Dp = 0.dp,
    var y: Dp = 0.dp,
    var width: Dp = 32.dp,
    var height: Dp = 18.dp,
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
    private var nextSpawnTime = 0L
    private var worldWidth = 0.dp
    private var worldHeight = 0.dp

    val leaves: List<Leaf> get() = activeLeaves

    fun update(time: Long, width: Dp, height: Dp) {
        worldWidth = width
        worldHeight = height

        if (time >= nextSpawnTime && pool.isNotEmpty()) {
            nextSpawnTime = time + MIN_SPAWN_THRESHOLD_MS + Random.nextLong(SPAWN_VARIANCE)

            val leaves = pool.removeFirst().apply {
                drawableRes = leafDrawableIds.random()
                x = random(0f, 72f).dp
                y = random(0f, 100f).dp
                val scale = random(0.5f, 1f)
                this.width = 32.dp * scale
                this.height = 18.dp * scale
                xSpeed = random(96f, 192f).dp
                ySpeed = random(48f, 96f).dp
                rotation = random(0f, 360f)
                rotationSpeed = random(0.5f, 180f) * if (Random.nextBoolean()) 1 else -1
                period = random(0.05f, 0.6f)
                amplitude = random(0.003f, 0.05f)
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
