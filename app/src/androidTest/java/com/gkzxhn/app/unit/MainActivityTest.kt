package com.gkzxhn.app.unit

import com.gkzxhn.app.MainActivity
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

/** 主页 MAIN
 * Created by Raleigh.Luo on 2018/06/12 11:41:47.
 */
@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@LargeTest
class MainActivityTest {
	private var mIdlingResource: IdlingResource?=null
	@Rule
	@JvmField
	val mActivityTestRule: IntentsTestRule<MainActivity> = object : IntentsTestRule<MainActivity>(MainActivity::class.java) {
		override fun getActivityIntent(): Intent {
			val intent = Intent(getInstrumentation().targetContext,MainActivity::class.java)
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
	fun MAIN_2() {
		with(mActivityTestRule.activity){
			//验证开始文字包含Hello
			TView.check_id_contains_text(R.id.et_text,"Hello")
			//输入新文本
			TView.input_text(R.id.et_text,"My new Text")
			//验证显示文字
			TView.check_id_text(R.id.et_text,"My new Text")
		}
	}
	/**
	 * 验证页面关闭
	 */
	@Test
	fun MAIN_8() {
		with(mActivityTestRule.activity){
			//点击关闭按钮
			TView.click_id(R.id.btn_finished)
			//验证页面已关闭
			TSystem.check_finished(this)
		}
	}

}