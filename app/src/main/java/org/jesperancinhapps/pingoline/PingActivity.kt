package org.jesperancinhapps.pingoline

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
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
import org.jesperancinhapps.pingoline.ui.theme.PingolineTheme
import kotlin.time.Duration.Companion.seconds


class PingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PingolineTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = colorScheme().background
                ) {
                    PingForm("Android", intent = intent, activity = this)
                }
            }
        }
    }

    @Deprecated("Deprecated in Java", ReplaceWith("finish()"))
    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }
}

const val PING_DNS_INPUT = "ping-dns-input"

const val PING_RESOLVE_SUBMIT = "ping-resolve-submit"

const val PING_DNS_RESULT = "ping-dns-result"

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PingForm(name: String, intent: Intent, activity: PingActivity) {
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
                textAlign = TextAlign.Left,
            )
        }
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.padding(all = 0.dp)
        ) {
            TextField(
                value = dns, onValueChange = {
                    dns = it
                },
                textStyle = TextStyle(fontSize = 20.sp),
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.2f)
                    .testTag(PING_DNS_INPUT)
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
            Text(
                color = colorScheme().secondary,
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f)
                    .background(colorScheme().primaryContainer)
                    .testTag(PING_DNS_RESULT),
                text = results,
                textAlign = TextAlign.Left
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
                            PingolineConnector.ping(dns).let { it.getOrNull()?.result ?: "" }
                        delay(5.seconds)
                        ready = true
                    }
                }, modifier = Modifier
                    .fillMaxWidth()
                    .testTag(PING_RESOLVE_SUBMIT)
            ) {
                Text(text = "Ping")
            }
        }
    }

}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun PingFormNightDemo() {
    PingForm("android", Intent(), PingActivity())
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PingFormDemo() {
    PingForm("android", Intent(), PingActivity())
}