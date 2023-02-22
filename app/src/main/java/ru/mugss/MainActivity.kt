package ru.mugss

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import dev.olshevski.navigation.reimagined.NavBackHandler
import dev.olshevski.navigation.reimagined.NavHost
import dev.olshevski.navigation.reimagined.rememberNavController
import ru.mugss.ui.EndScreen
import ru.mugss.ui.PlayScreen
import ru.mugss.ui.Screen
import ru.mugss.ui.StartScreen
import ru.mugss.ui.theme.MuGssTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MuGssTheme {
                NavHostScreen()
            }
        }
    }
}

@Composable
fun NavHostScreen() {
    val navController = rememberNavController<Screen>(
        startDestination = Screen.PlayScreen
    )
    NavBackHandler(navController)
    NavHost(navController) { screen ->
        when (screen) {
            is Screen.StartScreen -> StartScreen(navController)
            is Screen.PlayScreen -> PlayScreen(navController)
            is Screen.EndScreen -> EndScreen(navController)
        }
    }
}
