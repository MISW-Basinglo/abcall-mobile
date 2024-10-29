package co.uniandes.abcall.screens.settings

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import co.uniandes.abcall.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class SettingsScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()

        login()
        goSettings()
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

    private fun goSettings() {
        composeTestRule.onNodeWithTag("settings").performClick()
        composeTestRule.waitForIdle()
        composeTestRule.waitUntil(3000L){
            composeTestRule.onNodeWithTag("floatingActionButton").isNotDisplayed()
        }
    }

    @Test
    fun settingsScreen_displaysComponentsCorrectly() {
        composeTestRule.onNodeWithText("PERFIL").assertIsDisplayed()
        composeTestRule.onNodeWithTag("logout_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("update_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("update_button").isDisplayed()
    }

    @Test
    fun settingsScreen_logoutCorrectly() {
        composeTestRule.onNodeWithTag("logout_button").performClick()
        composeTestRule.waitUntil(3000L){
            composeTestRule.onNodeWithTag("logout_button").isNotDisplayed()
        }
        composeTestRule.onNodeWithText("Correo").assertIsDisplayed()
    }

    @Test
    fun settingsScreen_updateChannelCorrectly() {
        var channel = ""
        if(composeTestRule.onNodeWithText("SMS").isDisplayed()){
            composeTestRule.onNodeWithText("Canal de comunicación").performClick()
            composeTestRule.onNodeWithText("EMAIL").performClick()
            channel = "EMAIL"
        }else{
            composeTestRule.onNodeWithText("Canal de comunicación").performClick()
            composeTestRule.onNodeWithText("SMS").performClick()
            channel = "SMS"
        }

        composeTestRule.onNodeWithTag("update_button").assertIsEnabled()
        composeTestRule.onNodeWithText(channel).assertIsDisplayed()
    }


}
