package org.jesperancinha.pingoline

import androidx.compose.ui.test.assertTextContains
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextClearance
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import java.lang.Thread.sleep
import kotlin.time.Duration.Companion.seconds

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun  `should test app contex`() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("org.jesperancinha.pingoline", appContext.packageName)
    }

    @Test
    fun `should test ping operation against localhost`(){
        composeTestRule.onNodeWithTag(PING_SUBMIT).performClick()
        composeTestRule.onNodeWithTag(PING_DNS_INPUT).apply {
            performTextClearance()
            performTextInput("localhost")
        }
        composeTestRule.onNodeWithTag(PING_RESOLVE_SUBMIT).performClick()
        sleep(1.seconds.inWholeMilliseconds)
        composeTestRule.onNodeWithTag(PING_DNS_RESULT).assertTextContains("127.0.0.1", substring = true)
    }

    @Test
    fun `should test trace operation against localhost`(){
        composeTestRule.onNodeWithTag(TRACE_SUBMIT).performClick()
        composeTestRule.onNodeWithTag(TRACE_DNS_INPUT).apply {
            performTextClearance()
            performTextInput("localhost")
        }
        composeTestRule.onNodeWithTag(TRACE_RESOLVE_SUBMIT).performClick()
        sleep(1.seconds.inWholeMilliseconds)
        composeTestRule.onNodeWithTag(TRACE_DNS_RESULT).assertTextContains("127.0.0.1", substring = true)
    }

}