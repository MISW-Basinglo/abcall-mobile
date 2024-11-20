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
class CreateIssueScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()

        login()
        goCreate()
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

    private fun goCreate(){
        composeTestRule.onNodeWithTag("floatingActionButton").performClick()
        composeTestRule.waitUntil(3000L){
            composeTestRule.onNodeWithTag("floatingActionButton").isNotDisplayed()
        }
    }

    @Test
    fun createIssueScreen_displaysComponentsCorrectly() {
        composeTestRule.onNodeWithText("Tipo de incidente").assertIsDisplayed()
        composeTestRule.onNodeWithText("Descripción").assertIsDisplayed()
        composeTestRule.onNodeWithTag("create_button").assertIsDisplayed()
        composeTestRule.onNodeWithTag("create_button").assertIsNotEnabled()
        composeTestRule.onNodeWithTag("cancel_button").assertIsDisplayed()
    }

    @Test
    fun createIssueScreen_cancelsCreation() {
        composeTestRule.onNodeWithTag("cancel_button").performClick()
        composeTestRule.waitUntil(3000L){
            composeTestRule.onNodeWithTag("cancel_button").isNotDisplayed()
        }
        composeTestRule.onNodeWithTag("floatingActionButton").isDisplayed()
    }

    @Test
    fun createIssueScreen_createsIssueCorrectly() {
        composeTestRule.onNodeWithText("Tipo de incidente").performClick()
        composeTestRule.onNodeWithText("Solicitud").performClick()
        composeTestRule.onNodeWithText("Descripción").performTextInput(
            "This is a test issue description."
        )
        composeTestRule.onNodeWithTag("create_button").assertIsEnabled()
        composeTestRule.onNodeWithTag("create_button").performClick()
        composeTestRule.waitUntil(3000L){
            composeTestRule.onNodeWithTag("cancel_button").isNotDisplayed()
        }
        composeTestRule.onNodeWithTag("floatingActionButton").isDisplayed()
    }

    @Test
    fun createIssueScreen_suggestIssueCorrectly() {
        composeTestRule.onNodeWithText("Tipo de incidente").performClick()
        composeTestRule.onNodeWithText("Solicitud").performClick()
        composeTestRule.onNodeWithText("Descripción").performTextInput(
            "This is a test issue description."
        )
        composeTestRule.onNodeWithTag("suggest_button").assertIsEnabled()
        composeTestRule.onNodeWithTag("suggest_button").performClick()
        composeTestRule.waitUntil(3000L){
            composeTestRule.onNodeWithTag("suggest_title").isDisplayed()
        }
        composeTestRule.onNodeWithTag("suggest_text").isDisplayed()
    }

}
