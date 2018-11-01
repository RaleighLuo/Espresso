package com.gkzxhn.app.unit

import com.gkzxhn.app.WebViewActivity
import com.gkzxhn.app.R
import org.junit.runners.MethodSorters
import org.junit.runner.RunWith
import org.junit.*
import android.support.test.runner.AndroidJUnit4
import android.support.test.filters.LargeTest
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.IdlingResource
import android.support.test.espresso.IdlingRegistry
import android.content.Intent
import android.app.Instrumentation
import android.os.Build
import android.support.test.InstrumentationRegistry.*
import com.gkzxhn.autoespresso.operate.*
import android.app.Activity
import android.text.InputType
import android.Manifest
import android.provider.MediaStore
import android.support.test.InstrumentationRegistry
import android.os.Environment
import android.view.Gravity
import android.content.Context
import android.content.SharedPreferences

/** 网页 WEBVIEW
 * Created by Raleigh.Luo on 2018/11/01 18:07:34.
 */
@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@LargeTest
class WebViewActivityTest {
	private var mIdlingResource: IdlingResource?=null
	@Rule
	@JvmField
	val mActivityTestRule: IntentsTestRule<WebViewActivity> = object : IntentsTestRule<WebViewActivity>(WebViewActivity::class.java) {
		override fun getActivityIntent(): Intent {
			val intent = Intent(getInstrumentation().targetContext,WebViewActivity::class.java)
			return intent
		}
	}
	@Before
	fun setUp() {
		//从Activity中获取延迟操作对象
		mIdlingResource=mActivityTestRule.activity.getIdlingResource()
		//注册空闲资源－便于网络请求等耗时操作阻塞线程，进行单元测试
		if(mIdlingResource!=null)IdlingRegistry.getInstance().register(mIdlingResource)
	}
	@After
	fun unregisterIdlingResource(){
		//注销延迟操作对象
		if(mIdlingResource!=null)IdlingRegistry.getInstance().unregister(mIdlingResource)
	}
	/**
	 * 验证文本
	 */
	@Test
	fun WEBVIEW_002() {
		with(mActivityTestRule.activity){
			//清除文本
			TWebView.clear_text("id"," text_input")
			//输入新文本
			TWebView.input_text("id","text_input","Raleigh")
			//点击按钮
			TWebView.click("id","changeTextBtn")
			//验证显示文字
			TWebView.check_text("id","message","Raleigh")
			//验证跳转的url
			TWebView.check_url("id","changeTextBtn","http://xxxl")
		}
	}

}