package co.uniandes.abcall.screens.issues

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import co.uniandes.abcall.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class IssuesScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()

        login()
    }

    private fun login() {
        if(composeTestRule.onNodeWithText("Correo").isDisplayed()) {
            composeTestRule.onNodeWithText("Correo").performTextInput("user@example.com")
            composeTestRule.onNodeWithText("Contraseña").performTextInput("userpass")
            composeTestRule.onNodeWithTag("login_button").performClick()

            composeTestRule.waitForIdle()
            composeTestRule.waitUntil(3000L) {
                composeTestRule.onNodeWithTag("login_button").isNotDisplayed()
            }
        }
    }

    @Test
    fun issuesScreen_displaysComponentsCorrectly() {
        composeTestRule.onNodeWithText("Incidentes").assertIsDisplayed()
        composeTestRule.onNodeWithTag("floatingActionButton").assertIsDisplayed()
    }

}
