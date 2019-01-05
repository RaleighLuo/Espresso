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
 * Created by Raleigh.Luo on 2019/01/05 16:05:00.
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
			intent.putExtra("userId","93829")
			return intent
		}
	}
	@Before
	fun setUp() {
		//从Activity中获取延迟操作对象
		mIdlingResource=mActivityTestRule.activity.getIdlingResource()
		//注册空闲资源－便于网络请求等耗时操作阻塞线程，进行单元测试
		if(mIdlingResource!=null)IdlingRegistry.getInstance().register(mIdlingResource)
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				TPermissions.get_permission_shell("android.permission.CALL_PHONE")
				TPermissions.get_permission_shell(Manifest.permission.ACCESS_FINE_LOCATION)
		}
		//本地存储
		val edit=mActivityTestRule.getActivity().getSharedPreferences("UserTable",Context.MODE_PRIVATE).edit()
		edit.putString("username","admin")
		edit.putString("password","123456")
		edit.apply()
	}
	@After
	fun unregisterIdlingResource(){
		//注销延迟操作对象
		if(mIdlingResource!=null)IdlingRegistry.getInstance().unregister(mIdlingResource)
	}
	/**
	 * 列表上拉刷新
	 */
	@Test
	fun MAIN_002() {
		with(mActivityTestRule.activity){
			//上拉刷新
			TRecyclerView.pull_from_start(R.id.main_layout_rl_list)
			//验证Toast提示
			TWindow.toast_check_text(this,"Refresh Finished")
		}
	}
	/**
	 * 列表滑动到指定项
	 */
	@Test
	fun MAIN_006() {
		with(mActivityTestRule.activity){
			//滑动到第15项
			TRecyclerView.scroll_to_position(R.id.main_layout_rl_list,15)
			//检验指定项文字
			TRecyclerView.check_item_view_text(R.id.main_layout_rl_list,15,R.id.main_item_layout_tv_title,"Item 15")
		}
	}
	/**
	 * 点击第一项
	 */
	@Test
	fun MAIN_010() {
		with(mActivityTestRule.activity){
			//点击第一项
			TRecyclerView.click_item(R.id.main_layout_rl_list,0)
			//拦截Intent
			TIntent.intercept_classname("com.gkzxhn.app.WebViewActivity",Instrumentation.ActivityResult(Activity.RESULT_OK,null))
			//验证Intent
			TIntent.verify_classname("com.gkzxhn.app.WebViewActivity")
		}
	}

}