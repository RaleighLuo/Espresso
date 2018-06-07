package com.gkzxhn.app.unit

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.web.assertion.WebViewAssertions.webMatches
import android.support.test.espresso.web.sugar.Web.onWebView
import android.support.test.espresso.web.webdriver.DriverAtoms
import android.support.test.espresso.web.webdriver.Locator
import android.support.test.filters.LargeTest
import android.support.test.runner.AndroidJUnit4
import com.gkzxhn.app.MainActivity
import com.gkzxhn.app.WebViewActivity
import com.gkzxhn.autoespresso.operate.TWebView
import org.hamcrest.Matchers.containsString
import org.junit.FixMethodOrder
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.MethodSorters

/**
 * Created by Raleigh.Luo on 18/6/4.
 */
@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@LargeTest
class WebViewActivityTest {
    @Rule
    @JvmField
    val mActivityTestRule: IntentsTestRule<WebViewActivity> = object : IntentsTestRule<WebViewActivity>(WebViewActivity::class.java) {
        override fun getActivityIntent(): Intent {
            val intent = Intent(InstrumentationRegistry.getInstrumentation().targetContext,WebViewActivity::class.java)
            return intent
        }
        override fun afterActivityLaunched() {
            TWebView.forceJavascriptEnabled()
        }
    }
    @Test
    fun WebView() {
        TWebView.clear_text("text_input")
        Thread.sleep(1000)
        // Enter text into the input element
        TWebView.input_text("text_input","Raleigh")
        Thread.sleep(1000)
        //Click on element.
        TWebView.click_id("changeTextBtn")
        Thread.sleep(1000)
        // Verify that the text is displayed
        TWebView.check_text("message","Raleigh")
        Thread.sleep(1000)
//        TWebView.reset("changeTextBtn")
//        val url="file:///android_asset/web_form.html"
//        TWebView.check_url("changeTextBtn",url)
//        Thread.sleep(1000)
        Thread.sleep(5000)
    }
}