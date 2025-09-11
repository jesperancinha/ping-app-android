package nl.joaofilipesabinoesperancinha.pingoline

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import nl.joaofilipesabinoesperancinha.pingoline.ui.theme.PingolineTheme
import kotlin.time.Duration.Companion.seconds


class PingRouteActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Register back press callback
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finish()
            }
        })
        setContent {
            PingolineTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorScheme().background
                ) {
                    PingRouteForm("Android", intent = intent, activity = this@PingRouteActivity)
                }
            }
        }
    }
}


const val TRACE_DNS_INPUT = "trace-dns-input"

const val TRACE_RESOLVE_SUBMIT = "trace-resolve-submit"

const val TRACE_DNS_RESULT = "trace-dns-result"


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PingRouteForm(name: String, intent: Intent, activity: PingRouteActivity) {
    var dns by remember {
        mutableStateOf("")
    }
    var results by remember {
        mutableStateOf("Try now!")
    }
    var ready by remember {
        mutableStateOf(true)
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
                modifier = Modifier
                    .align(alignment = Alignment.Top)
                    .fillMaxHeight(0.1f),
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
        }
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center,
        ) {
            TextField(
                value = dns, onValueChange = {
                    dns = it
                },
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f)
                    .testTag(TRACE_DNS_INPUT)
                    .defaultMinSize(minHeight = 40.dp)
                    .padding(all = 0.dp)
            )
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
        }
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center,
        ) {
            TextField(
                enabled = false,
                value = results,
                onValueChange = { }, modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .background(colorScheme().background)
                    .testTag(TRACE_DNS_RESULT)
            )
        }
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center
        ) {
            Button(onClick = { activity.finish() }, modifier = Modifier.fillMaxWidth(0.5f)) {
                Text(text = "Back")
            }
            Button(
                enabled = ready,
                onClick = {
                    MainScope().launch {
                        ready = false
                        results =
                            PingolineConnector.traceroute(dns).let { it.getOrNull()?.result ?: "" }
                        delay(5.seconds)
                        ready = true
                    }
            }, modifier = Modifier
                .fillMaxWidth()
                .testTag(TRACE_RESOLVE_SUBMIT)) {
                Text(text = "PingRoute")
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