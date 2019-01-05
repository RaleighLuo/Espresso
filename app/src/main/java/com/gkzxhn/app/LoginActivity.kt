package com.gkzxhn.app

import android.content.Intent
import android.os.Bundle
import android.support.annotation.VisibleForTesting
import android.support.test.espresso.IdlingResource
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.login_layout.login_layout_et_username
as etUsername
import kotlinx.android.synthetic.main.login_layout.login_layout_et_password
as etPassword

class LoginActivity : AppCompatActivity(){
    //自动化测试使用
    private var mIdlingResource: SimpleIdlingResource? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_layout)
        setTitle("Login")
    }
    fun onClick(v: View){
        if(v.id==R.id.login_layout_btn_login){
            if(etUsername.text.length==0){
                etUsername.error="Please input username!"
            }else if(etPassword.text.length==0){
                etPassword.error="Please input password!"
            }else if(etUsername.text.toString()!="admin"||etPassword.text.toString()!="123456"){
                Toast.makeText(this,"Error username and password!",Toast.LENGTH_SHORT).show()
            }else{
                //登录  跳转到主页
                startActivity(Intent(this,MainActivity::class.java))
                finish()
            }
        }
    }

    override fun onBackPressed() {
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