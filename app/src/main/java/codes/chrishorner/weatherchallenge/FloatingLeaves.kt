package codes.chrishorner.weatherchallenge

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.vector.PathParser
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import kotlin.math.sin
import kotlin.random.Random

private const val LEAF_COUNT = 100
private const val MIN_SPAWN_THRESHOLD_MS = 100L
private const val SPAWN_VARIANCE = 200L

private val leafPaths: Array<Path> = arrayOf(
    "M0.5661,5.9881C5.4426,4.3573 10.2816,1.1409 15.4639,2.2018C21.509,3.4384 25.0151,9.6953 31.4684,8.1315C26.8732,18.3713 5.3424,19.596 4.9526,6.0114C3.5855,6.1177 1.7967,6.075 0.5313,6.8165L0.5661,5.9881Z".asPath(),
    "M0.4848,10.309C2.5983,11.3231 3.1569,11.412 4.2808,11.4801C5.9268,0.5713 20.2426,0.6337 27.4946,6.3093C29.3586,7.7681 30.4511,9.7861 31.472,11.8655C29.5582,12.0093 27.6841,11.6614 25.8117,12.3166C20.9716,14.009 17.6838,17.2328 12.1456,16.6439C8.1333,16.2169 4.8585,13.2333 1.211,11.5159L0.4848,10.309Z".asPath(),
    "M1.0268,7.9562L1.0133,7.9595L0.7564,7.1953C0.7722,7.1894 0.7886,7.1832 0.8052,7.1776C1.7749,6.8292 2.9804,6.7607 3.8648,6.2327C4.9587,5.5799 5.6436,4.3334 6.6855,3.577C8.9823,1.9078 12.1496,1.6441 14.9018,1.8956C17.156,2.1008 19.3035,2.8708 21.2559,3.9977C23.4323,5.2525 25.4624,6.9447 28.0952,6.9649C29.3558,6.9747 30.3441,6.4378 31.4721,5.9596C29.6077,13.6546 20.0865,17.0158 12.9032,16.0137C8.6759,15.4239 3.3236,12.454 3.9427,7.4662L3.8556,7.4783L3.7067,7.4979L3.5861,7.5144L3.4599,7.5314L3.4222,7.5365L3.3636,7.5441L3.2893,7.5545L3.2454,7.5603L3.1773,7.5699L3.1097,7.5793L3.0679,7.5857L3.0064,7.5944L2.9797,7.5983L2.931,7.6051L2.8892,7.6115L2.8406,7.6184L2.7951,7.6254L2.7297,7.6354L2.6848,7.6422L2.614,7.6533L2.5625,7.6611L2.4972,7.6716L2.4398,7.6808L2.3792,7.6903L2.3081,7.7022L2.2617,7.7102L2.1888,7.7227L2.1405,7.7307L2.0684,7.7435L2.0113,7.7538L1.947,7.7653L1.9235,7.7694L1.8624,7.781L1.8283,7.7874L1.7683,7.7987L1.7277,7.8067L1.7042,7.8108L1.6428,7.8232L1.6052,7.8307L1.5485,7.8422L1.5067,7.8505L1.4834,7.8552L1.4227,7.868L1.3834,7.8761L1.3573,7.8818L1.3098,7.8921L1.2623,7.9023L1.2054,7.9151L1.1598,7.9254L1.1107,7.9368L1.0615,7.9477L1.0268,7.9562Z".asPath(),
    "M0.5325,7.4136C2.5576,5.9151 6.1758,5.5525 7.2903,5.6058C8.3875,4.554 9.1434,2.5236 14.0188,2.5236C24.6734,2.5236 21.7684,9.0001 28.7035,9.0001C30.126,9.0001 30.8966,8.7774 31.6978,8.3335C29.1781,13.4901 23.9468,15.4752 18.9048,15.4752C12.3803,15.4752 6.6051,12.3636 6.6051,8.0508C6.6051,7.5776 6.961,7.1176 7.109,6.8803C4.9573,6.6084 2.7562,7.3123 1.5271,8.4748L0.5325,7.4136Z".asPath(),
    "M0.947,9.7954C3.0237,10.7697 3.571,10.852 4.6714,10.909C6.1894,0.2206 20.1982,0.1601 27.3426,5.6522C29.1789,7.0638 30.2651,9.0292 31.2817,11.0553C29.4102,11.2122 27.5734,10.8877 25.7468,11.5447C21.0251,13.2419 17.8353,16.4243 12.4111,15.8951C8.4813,15.5114 5.2516,12.6197 1.6679,10.9702L0.947,9.7954Z".asPath(),
    "M1.099,8.1761L1.9506,6.447C5.8107,5.2626 8.4042,2.0261 12.6542,1.8425C17.8862,1.6174 20.8207,4.9103 25.3413,6.6099C27.2619,7.3325 29.2657,7.0901 31.2584,7.2634C26.9872,14.9806 13.9741,19.7098 6.7888,12.8583C4.9958,11.1499 4.4084,8.8913 3.7575,6.588C2.7843,7.0434 1.9036,7.5767 1.099,8.1761Z".asPath(),
    "M2.3906,6.8174C3.6337,6.9624 5.2599,7.0485 6.9031,6.5056C8.5069,4.0982 12.4178,2.6748 15.5426,2.8766C21.6075,3.2712 25.4976,8.3672 31.7704,7.3066C28.1545,15.2612 14.1188,20.3328 7.6338,12.3724C6.5907,11.0941 6.1556,9.8918 6.1663,8.8028C4.7503,8.2552 2.8312,8.1421 0.859,8.5663L2.3906,6.8174Z".asPath(),
)

private val leafCenter = DpOffset(16.dp, 9.dp)

private val leafColors: Array<Color> = arrayOf(
    Color(0xFFFE8318),
    Color(0xFFF3D220),
    Color(0xFFFFC651),
    Color(0xFFCE6C59),
    Color(0xFFCE422A),
    Color(0xFFC35C17),
    Color(0xFFA04427),
)

@Composable
fun FloatingLeaves(modifier: Modifier = Modifier) {
    val simulation = remember { LeavesSimulation() }
    val frameTime by frameTimeMillis()

    Canvas(modifier = modifier) {
        simulation.update(frameTime, size.width.dp, size.height.dp)
        val leaves = simulation.leaves

        for (index in leaves.indices) {
            val leaf = leaves[index]
            translate(leaf.x.toPx(), leaf.y.toPx()) {
                val centreOffset = Offset(leafCenter.x.toPx(), leafCenter.y.toPx())
                rotate(leaf.rotation, centreOffset) {
                    scale(leaf.scale, centreOffset) {
                        drawPath(leaf.path, color = leaf.color, alpha = leaf.alpha)
                    }
                }
            }
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
    var amplitude: Float = 0f,
    var alpha: Float = 0f,
    var scale: Float = 0f,
    var path: Path = leafPaths[0],
    var color: Color = leafColors[0],
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
                x = random(0f, 100f).dp
                y = random(16f, 100f).dp
                scale = random(0.8f, 1.2f)
                xSpeed = random(90f, 136f).dp
                ySpeed = random(48f, 70f).dp
                rotation = random(0f, 360f)
                rotationSpeed = random(24f, 180f) * if (Random.nextBoolean()) 1 else -1
                period = random(0.05f, 0.6f)
                amplitude = random(0.003f, 0.05f)
                alpha = 0f
                path = leafPaths.random()
                color = leafColors.random()
            }

            activeLeaves.add(leaves)
        }

        val deltaSeconds = (time - lastUpdateTime) / 1000f

        for (index in activeLeaves.indices.reversed()) {
            with(activeLeaves[index]) {
                rotation += rotationSpeed * deltaSeconds
                x += xSpeed * deltaSeconds
                y += (period * sin(amplitude * x.value) + (ySpeed.value * deltaSeconds)).dp
                alpha = (alpha + 0.97f * deltaSeconds).coerceAtMost(1f)

                if (x > worldWidth) {
                    activeLeaves.remove(this)
                    pool.add(this)
                }
            }
        }

        lastUpdateTime = time
    }
}

private fun String.asPath(): Path {
    return PathParser().parsePathString(this).toPath()
}
