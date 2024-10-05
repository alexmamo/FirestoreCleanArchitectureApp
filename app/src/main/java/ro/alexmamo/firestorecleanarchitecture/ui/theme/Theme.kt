package ro.alexmamo.firestorecleanarchitecture.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryDark,
    onPrimary = White,
    secondary = SecondaryDark,
    onSecondary = White,
    tertiary = TertiaryDark,
    onTertiary = White,
    background = BackgroundDark,
    onBackground = White,
    surface = PrimaryDark,
    onSurface = White
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLight,
    onPrimary = Black,
    secondary = SecondaryLight,
    onSecondary = Black,
    tertiary = TertiaryLight,
    onTertiary = White,
    background = BackgroundLight,
    onBackground = Black,
    surface = White,
    onSurface = Black
)

@Composable
fun FirestoreCleanArchitectureTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
      darkTheme -> DarkColorScheme
      else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
      SideEffect {
        val window = (view.context as Activity).window
        window.statusBarColor = colorScheme.primary.toArgb()
        WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
      }
    }

    MaterialTheme(
      colorScheme = colorScheme,
      typography = Typography,
      content = content
    )
}