package org.jesperancinha.pingoline

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.jesperancinha.pingoline.ui.theme.PingolineTheme

class PingRouteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PingolineTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorScheme().background
                ) {
                    PingRouteForm("Android", intent = intent, activity = this)
                }
            }
        }
    }

    @Deprecated("Deprecated in Java", ReplaceWith("finish()"))
    override fun onBackPressed() {
        finish()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PingRouteForm(name: String, intent: Intent, activity: PingRouteActivity) {
    var dns by remember {
        mutableStateOf("")
    }
    var results by remember {
        mutableStateOf("")
    }
    Column(
        modifier = Modifier
            .background(colorScheme().primary)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.align(alignment = Alignment.Top),
                text = "Welcome to Pingoline",
                textAlign = TextAlign.Center,
            )
        }
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center,
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Place your domain here:",
                textAlign = TextAlign.Left
            )
            TextField(value = dns, onValueChange = { }, modifier = Modifier.fillMaxWidth())
        }
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Results:",
                textAlign = TextAlign.Left
            )
            TextField(
                enabled = false,
                value = results,
                onValueChange = { }, modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { activity.finish() }, modifier = Modifier.fillMaxWidth(0.5f)) {
                Text(text = "Back")
            }
            Button(onClick = {
                PingolineConnector.traceroute(dns).let { results = it.getOrNull()?.result ?: "" }
            }, modifier = Modifier.fillMaxWidth()) {
                Text(text = "Ping Route")
            }
        }
    }

}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PingRouteFormNightDemo() {
    PingRouteForm("android", Intent(), PingRouteActivity())
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PingRouteFormDemo() {
    PingRouteForm("android", Intent(), PingRouteActivity())
}