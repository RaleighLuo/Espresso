package com.gkzxhn.app

import android.app.Activity
import android.os.Bundle
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
}