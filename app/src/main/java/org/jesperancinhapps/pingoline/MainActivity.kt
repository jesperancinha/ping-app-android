package org.jesperancinhapps.pingoline

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.os.StrictMode
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.jesperancinhapps.pingoline.ui.theme.DarkColors
import org.jesperancinhapps.pingoline.ui.theme.LightColors
import org.jesperancinhapps.pingoline.ui.theme.PingolineTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val policy = StrictMode.ThreadPolicy.Builder().permitNetwork().build()
        StrictMode.setThreadPolicy(policy)
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
fun colorScheme(): ColorScheme {
    val colorScheme = when {
        isSystemInDarkTheme() -> DarkColors
        else -> LightColors
    }
    return colorScheme
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
                color = colorScheme().background
            ) {
                MainMenu(name = "Android", mainActivity = mainActivity)
            }
        }
    }
}

const val PING_SUBMIT = "ping-submit"

const val TRACE_SUBMIT = "traceroute-submit"

@Composable
fun MainMenu(name: String, modifier: Modifier = Modifier, mainActivity: MainActivity) {
    Column(
        modifier = Modifier .background(colorScheme().primary)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Welcome to Pingoline",
                textAlign = TextAlign.Center,
            )
        }
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "You can ping and trace to internal domain",
                textAlign = TextAlign.Center,
            )

        }
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Make sure to use this app responsibility",
                textAlign = TextAlign.Center,
            )
        }
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "There is a 5 second delay between requests to avoid mass requests",
                textAlign = TextAlign.Center,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                onClick = {
                    val navigate = Intent(mainActivity, PingActivity::class.java)
                    startActivity(mainActivity, navigate, null)
                },
                modifier = Modifier
                    .testTag(PING_SUBMIT)
            ) {
                Text(text = "Ping")
            }
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Button(
                onClick = {
                    val navigate = Intent(mainActivity, PingRouteActivity::class.java)
                    startActivity(mainActivity, navigate, null)
                },
                modifier = Modifier
                    .testTag(TRACE_SUBMIT)
            ) {
                Text(text = "Traceroute")
            }
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun MainMenuNightDemo(){
    MainMenu(name = "Android", mainActivity = MainActivity())
}
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun MainMenuDemo(){
    MainMenu(name = "Android", mainActivity = MainActivity())
}