package com.gkzxhn.app

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.annotation.VisibleForTesting
import android.support.test.espresso.IdlingResource
import android.view.View
import android.widget.Toast
import com.gkzxhn.app.adapter.MainAdapter
import com.gkzxhn.app.adapter.OnItemClickListener
import kotlinx.android.synthetic.main.activity_main.main_layout_rl_list
as mRecyclerView
import kotlinx.android.synthetic.main.activity_main.main_layout_swipeRefresh
as mSwipeRefreshLayout
class MainActivity : AppCompatActivity() {
    //自动化测试使用
    private var mIdlingResource: SimpleIdlingResource? = null
    private lateinit var adapter: MainAdapter
    private val mHandler:Handler= Handler()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("Main")
        adapter = MainAdapter(this)
        adapter.setOnItemClickListener(onItemClickListener)
        initData()
        mRecyclerView.adapter = adapter
        mSwipeRefreshLayout.setOnRefreshListener {
            //延迟加载的使用
            //执行网络请求或耗时操作前，设置等待
            setIdleNow(false)
            //执行网络请求等，模拟2秒延迟
            mHandler.postDelayed(Runnable {
                // 执行完成，释放为空闲状态，单元测试可继续执行
                setIdleNow(true)
                //刷新完成
                mSwipeRefreshLayout.setRefreshing(false)
                Toast.makeText(this@MainActivity,  "Refresh Finished", Toast.LENGTH_LONG).show();

            },2000)
        }
    }
    fun initData(){
        var mDatas:ArrayList<String> = ArrayList<String>()
        mDatas.add("WebView Page")
        mDatas.add("Second Page")
        mDatas.add("Three Page")
        adapter.updateItems(mDatas)
    }
    private val onItemClickListener = object : OnItemClickListener {
        override fun onClickListener(convertView: View, position: Int) {
            when(position){
                0 ->{
                    startActivityForResult(Intent(this@MainActivity, WebViewActivity::class.java), Activity.RESULT_OK);

                }
                1 ->{
                    startActivityForResult(Intent(this@MainActivity, SecondActivity::class.java), Activity.RESULT_OK);

                }
                2 ->{
                    startActivityForResult(Intent(this@MainActivity, ThreeActivity::class.java), Activity.RESULT_OK);
                }
            }


        }
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
