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

/** 主页 APP_Main
 * Created by Raleigh.Luo on 2018/03/20 14:30:55.
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
	fun APP_Main_001() {
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
	fun APP_Main_002() {
		with(mActivityTestRule.activity){
			//点击关闭按钮
			TView.click_id(R.id.btn_finished)
			//验证页面已关闭
			TSystem.check_finished(this)
		}
	}

}