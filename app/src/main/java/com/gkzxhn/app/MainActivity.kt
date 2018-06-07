package com.gkzxhn.app

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.test.espresso.IdlingResource
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    //自动化测试使用
    private var mIdlingResource: SimpleIdlingResource? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_finished.setOnClickListener { finish() }
        btn_second.setOnClickListener{ startActivity(Intent(this,SecondActivity::class.java)) }
        btn_three.setOnClickListener {
            startActivityForResult(Intent(this, ThreedActivity::class.java), Activity.RESULT_OK);
        }

        //延迟加载的使用
//        setIdleNow(false); 执行网络请求或耗时操作前，设置等待
//        requestHttp(); 执行网络请求
//        setIdleNow(true); 执行完成，释放为空闲状态，单元测试可继续执行
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
