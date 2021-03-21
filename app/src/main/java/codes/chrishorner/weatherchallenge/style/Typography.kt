package codes.chrishorner.weatherchallenge.style

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import codes.chrishorner.weatherchallenge.R

private val ZillaSlab = FontFamily(
    Font(R.font.zilla_slab_medium, weight = FontWeight.Medium)
)

private val RobotoSlab = FontFamily(
    Font(R.font.roboto_slab_regular),
    Font(R.font.roboto_slab_medium, weight = FontWeight.Medium)
)

private val Inter = FontFamily(
    Font(R.font.inter_regular)
)

val WeatherChallengeTypography = Typography(
    defaultFontFamily = Inter,
    h1 = TextStyle(
        fontSize = 96.sp,
        fontFamily = ZillaSlab,
        fontWeight = FontWeight.Medium
    ),
    h2 = TextStyle(
        fontSize = 60.sp,
        letterSpacing = (-0.5).sp,
        fontFamily = ZillaSlab,
        fontWeight = FontWeight.Medium
    ),
    h3 = TextStyle(
        fontSize = 48.sp,
        fontFamily = ZillaSlab,
        fontWeight = FontWeight.Medium
    ),
    h4 = TextStyle(
        fontSize = 34.sp,
        letterSpacing = 0.25.sp,
        fontFamily = ZillaSlab,
        fontWeight = FontWeight.Medium
    ),
    h5 = TextStyle(
        fontSize = 24.sp,
        letterSpacing = 0.2.sp,
        fontFamily = ZillaSlab,
        fontWeight = FontWeight.Medium
    ),
    h6 = TextStyle(
        fontSize = 20.sp,
        letterSpacing = 0.15.sp,
        fontFamily = ZillaSlab,
        fontWeight = FontWeight.Medium
    ),
    subtitle1 = TextStyle(
        fontSize = 18.sp,
        letterSpacing = 0.2.sp,
        fontWeight = FontWeight.Normal
    ),
    subtitle2 = TextStyle(
        fontSize = 14.sp,
        letterSpacing = 0.1.sp,
        fontWeight = FontWeight.Normal
    ),
    overline = TextStyle(
        fontSize = 15.sp
    )
)

val LargeTempTextStyle = WeatherChallengeTypography.h2.copy(
    fontSize = 56.sp,
    fontFamily = RobotoSlab,
    fontWeight = FontWeight.Normal
)

val MediumTempTextStyle = WeatherChallengeTypography.h5.copy(
    fontFamily = RobotoSlab,
    fontWeight = FontWeight.Medium
)

val SmallTempTextStyle = WeatherChallengeTypography.body1.copy(
    fontSize = 18.sp,
    fontFamily = RobotoSlab,
    fontWeight = FontWeight.Medium
)