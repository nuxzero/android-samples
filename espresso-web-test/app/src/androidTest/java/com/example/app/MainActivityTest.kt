package com.example.app

import android.util.Base64
import android.view.View
import android.webkit.WebView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.RootMatchers.*
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.espresso.web.assertion.WebViewAssertions.webMatches
import androidx.test.espresso.web.sugar.Web.onWebView
import androidx.test.espresso.web.webdriver.DriverAtoms.*
import androidx.test.espresso.web.webdriver.Locator
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.Matcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @get:Rule
    val activityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun webView_shouldLoadHtml() {
        onView(withId(R.id.webview)).check(matches(isDisplayed()))

        onView(withId(R.id.webview)).perform(
            loadHtml(
                """
                    <html>
                        <body>
                            <h1 id="message">Hello, world!</h1>
                        </body>
                    </html>
                """.trimIndent()
            )
        )

        onWebView(withId(R.id.webview))
            .forceJavascriptEnabled()
            .withElement(findElement(Locator.ID, "message"))
            .check(webMatches(getText(), containsString("Hello, world!")))
    }

    @Test
    fun webView_shouldLoadHtmlAndJavaScript() {
        onView(withId(R.id.webview)).check(matches(isDisplayed()))

        onView(withId(R.id.webview)).perform(
            loadHtml(
                """
                    <!DOCTYPE html>
                    <html>
                        <body>
                            <p id="message">Test alert().</p>
                            <button onclick="setTimeout(displayAlert, 100);" id="button">Submit</button>
                            <script>
                                function displayAlert() {
                                  alert("Test alert!");
                                }
                            </script>
                        </body>
                    </html>
                """.trimIndent()
            )
        )

        onWebView(withId(R.id.webview))
            .forceJavascriptEnabled()
            .withElement(findElement(Locator.ID, "message"))
            .check(webMatches(getText(), containsString("Test alert().")))

        onWebView()
            .withElement(findElement(Locator.ID, "button"))
            .perform(webClick())

        onView(withText(("Test alert!"))).inRoot(isDialog()).check(matches(isDisplayed()))
        // Required to dismiss the dialog, otherwise other tests will fail.
        onView(withText("OK")).perform(click())
    }

    private fun loadHtml(html: String): ViewAction = object : ViewAction {
        override fun getDescription(): String {
            return "Load html to WebView"
        }

        override fun getConstraints(): Matcher<View> {
            return CoreMatchers.allOf(ViewMatchers.isAssignableFrom(WebView::class.java))
        }

        override fun perform(uiController: UiController?, view: View?) {
            val webView = view as? WebView ?: return
            val encodedHtml = Base64.encodeToString(html.toByteArray(), Base64.NO_PADDING)
            webView.loadData(encodedHtml, "text/html", "base64")
        }
    }
}
