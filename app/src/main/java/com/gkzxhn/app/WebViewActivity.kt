package com.gkzxhn.app

import android.app.Activity
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.test.espresso.IdlingResource
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import android.webkit.WebViewClient
import kotlinx.android.synthetic.main.webview_layout.web_view
as mWebView

/**
 * Created by Raleigh.Luo on 18/6/4.
 */
class WebViewActivity: AppCompatActivity() {
    protected val WEB_FORM_URL = "file:///android_asset/web_form.html"
    //自动化测试使用
    private var mIdlingResource: SimpleIdlingResource? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview_layout)
        mWebView.getSettings().setJavaScriptEnabled(true)
        mWebView.loadUrl(WEB_FORM_URL)
        mWebView.requestFocus()
        mWebView.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                return false
            }
        })
    }

    /**
     * Only called from test, creates and returns a new [SimpleIdlingResource].
     */
    @VisibleForTesting
    fun getIdlingResource(): IdlingResource {
        if (mIdlingResource == null) {
            mIdlingResource = SimpleIdlingResource()
        }
        return mIdlingResource!!
    }
    /**
     * Espresso 自动化测试延迟操作
     * @param isIdleNow 是否为空闲，false则阻塞测试线程
     */
    fun setIdleNow(isIdleNow: Boolean){
        //状态不相等时
        if(mIdlingResource?.isIdleNow!=isIdleNow) {
            if (isIdleNow) {
                //耗时操作结束，设置空闲状态为true，放开测试线程
                mIdlingResource?.setIdleState(true);
            } else {
                //耗时操作开始，设置空闲状态为false，阻塞测试线程
                mIdlingResource?.setIdleState(false);
            }
        }
    }
}