package co.uniandes.abcall.screens.auth

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import co.uniandes.abcall.MainActivity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class LoginScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    var composeTestRule = createAndroidComposeRule<MainActivity>()

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun loginScreen_displaysComponentsCorrectly() {
        composeTestRule.onNodeWithText("Correo").assertIsDisplayed()
        composeTestRule.onNodeWithText("Contraseña").assertIsDisplayed()
        composeTestRule.onNodeWithTag("login_button").assertIsDisplayed()
    }

    @Test
    fun loginButton_isEnabled_whenEmailAndPasswordAreValid() {
        composeTestRule.onNodeWithText("Correo").performTextInput("user@example.com")
        composeTestRule.onNodeWithText("Contraseña").performTextInput("validpassword")

        composeTestRule.onNodeWithTag("login_button").assertIsEnabled()
    }

    @Test
    fun loginButton_isDisabled_whenEmailOrPasswordAreInvalid() {
        composeTestRule.onNodeWithText("Correo").performTextInput("invalid-email")
        composeTestRule.onNodeWithText("Contraseña").performTextInput("validpassword")

        composeTestRule.onNodeWithTag("login_button").assertIsNotEnabled()

        composeTestRule.onNodeWithText("Correo").performTextInput("user@example.com")
        composeTestRule.onNodeWithText("Contraseña").performTextClearance()

        composeTestRule.onNodeWithTag("login_button").assertIsNotEnabled()
    }

    @Test
    fun loginScreen_showsErrorMessage_whenLoginFails() {
        composeTestRule.onNodeWithText("Correo").performTextInput("user@example.com")
        composeTestRule.onNodeWithText("Contraseña").performTextInput("wrongpassword")

        composeTestRule.onNodeWithTag("login_button").performClick()

        composeTestRule.waitForIdle()
        composeTestRule.onNodeWithTag("login_button").assertIsDisplayed()

    }

    @Test
    fun loginScreen_navigatesToMainScreen_whenLoginSucceeds() {
        composeTestRule.onNodeWithText("Correo").performTextInput("user@example.com")
        composeTestRule.onNodeWithText("Contraseña").performTextInput("userpass")

        composeTestRule.onNodeWithTag("login_button").performClick()

        composeTestRule.waitForIdle()
        composeTestRule.waitUntil(3000L){
            composeTestRule.onNodeWithTag("login_button").isNotDisplayed()
        }
        composeTestRule.onNodeWithTag("login_button").assertIsNotDisplayed()

    }

}
