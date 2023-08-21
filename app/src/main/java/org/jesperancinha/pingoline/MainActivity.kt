package org.jesperancinha.pingoline

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jesperancinha.pingoline.ui.theme.PingolineTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PingolineTheme {
                val navController = rememberNavController()
                SetupNavGraph(navController = navController, this)
            }
        }
    }
}


@Composable
fun SetupNavGraph(navController: NavHostController, mainActivity: MainActivity) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            MatrixSplashScreen(navController = navController)
        }
        composable(route = Screen.Home.route) {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                MainMenu("Android", mainActivity = mainActivity)
            }
        }
    }
}


@Composable
fun MainMenu(name: String, modifier: Modifier = Modifier, mainActivity: MainActivity) {    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}
