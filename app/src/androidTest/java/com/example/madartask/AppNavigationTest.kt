import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.madartask.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppNavigationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun fullAppFlow_addUserAndVerifyInList() {

        composeTestRule.onNodeWithText("Name").performTextInput("Ahmed")

        composeTestRule.onNodeWithText("Age").performTextInput("28")

        composeTestRule.onNodeWithText("Job Title").performTextInput("Android Developer")

        composeTestRule.onNodeWithText("Save").performClick()


        composeTestRule.onNodeWithText("Name").assertTextContains("")

        composeTestRule.onNodeWithText("View Users").performClick()

        composeTestRule.onNodeWithText("Users List").assertIsDisplayed()

        composeTestRule.onNodeWithText("Name: Ahmed").assertIsDisplayed()
        composeTestRule.onNodeWithText("Age: 28").assertIsDisplayed()
        composeTestRule.onNodeWithText("Job Title: Android Developer").assertIsDisplayed()
    }
}

fun androidx.compose.ui.test.SemanticsNodeInteraction.assertTextContains(value: String, ignoreCase: Boolean = false): androidx.compose.ui.test.SemanticsNodeInteraction {
    val text = (fetchSemanticsNode().config.firstOrNull { it.key.name == "EditableText" }?.value as? androidx.compose.ui.text.AnnotatedString)?.text ?: ""
    assert(text.contains(value, ignoreCase)) { "Text '$text' does not contain '$value'" }
    return this
}