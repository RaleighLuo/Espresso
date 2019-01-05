package com.gkzxhn.app.unit

import com.gkzxhn.app.LoginActivity
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

/** 登录 LOGIN
 * Created by Raleigh.Luo on 2019/01/05 16:05:00.
 */
@RunWith(AndroidJUnit4::class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@LargeTest
class LoginActivityTest {
	private var mIdlingResource: IdlingResource?=null
	@Rule
	@JvmField
	val mActivityTestRule: IntentsTestRule<LoginActivity> = object : IntentsTestRule<LoginActivity>(LoginActivity::class.java) {
		override fun getActivityIntent(): Intent {
			val intent = Intent(getInstrumentation().targetContext,LoginActivity::class.java)
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
	 * 验证空用户名和密码
	 */
	@Test
	fun LOGIN_002() {
		with(mActivityTestRule.activity){
			//输入空用户名
			TView.input_text(R.id.login_layout_et_username,"")
			//输入空密码
			TView.input_text(R.id.login_layout_et_password,"")
			//点击登录
			TView.click_id(R.id.login_layout_btn_login)
			//验证空用户名提示
			TView.check_id_error_text(R.id.login_layout_et_username,"Please input username!")
		}
	}
	/**
	 * 验证空密码
	 */
	@Test
	fun LOGIN_010() {
		with(mActivityTestRule.activity){
			//输入用户名
			TView.input_text(R.id.login_layout_et_username,"TestUsername")
			//输入空密码
			TView.input_text(R.id.login_layout_et_password,"")
			//点击登录
			TView.click_id(R.id.login_layout_btn_login)
			//验证空密码提示
			TView.check_id_error_text(R.id.login_layout_et_password,"Please input password!")
		}
	}
	/**
	 * 验证错误用户名或密码
	 */
	@Test
	fun LOGIN_018() {
		with(mActivityTestRule.activity){
			//输入错误用户名
			TView.input_text(R.id.login_layout_et_username,"TestUsername")
			//输入错误密码
			TView.input_text(R.id.login_layout_et_password,"123456")
			//点击登录
			TView.click_id(R.id.login_layout_btn_login)
			//验证Toast错误用户名密码提示
			TWindow.toast_check_text(this,"Error username and password!")
		}
	}
	/**
	 * 登录成功
	 */
	@Test
	fun LOGIN_026() {
		with(mActivityTestRule.activity){
			//输入空用户名
			TView.input_text(R.id.login_layout_et_username,"admin")
			//输入空密码
			TView.input_text(R.id.login_layout_et_password,"123456")
			//点击登录
			TView.click_id(R.id.login_layout_btn_login)
			//拦截Intent
			TIntent.intercept_classname("com.gkzxhn.app.MainActivity",Instrumentation.ActivityResult( Activity.RESULT_OK,null))
			//验证Intent
			TIntent.verify_classname("com.gkzxhn.app.MainActivity")
			//验证登录页面是否Finished
			TSystem.check_finished(this)
		}
	}

}