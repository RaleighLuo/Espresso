package com.gkzxhn.app

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
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
import android.content.DialogInterface
import com.gkzxhn.app.R.mipmap.ic_launcher


class MainActivity : AppCompatActivity() {
    //自动化测试使用
    private var mIdlingResource: SimpleIdlingResource? = null
    private lateinit var adapter: MainAdapter
    private val mHandler:Handler= Handler()
    private lateinit var mDialog:AlertDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTitle("Main")
        adapter = MainAdapter(this)
        adapter.setOnItemClickListener(onItemClickListener)
        //初始化对话框
        initdDialog()
        //初始化数据
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
        mDatas.add("Show Dialog")
        mDatas.add("To WebView")
        mDatas.add("To Second")
        adapter.updateItems(mDatas)
    }

    /**
     * 初始化对话框
     */
    private fun initdDialog(){
        // 创建构建器
        val mBuilder = AlertDialog.Builder(this)
        // 设置参数
        mDialog= mBuilder.setTitle("提示").setIcon(R.mipmap.ic_launcher)
                .setMessage("是否删除数据？")
                .setPositiveButton("确定", object : DialogInterface.OnClickListener{
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        //关闭对话框
                        mDialog.dismiss()
                        //提示成功
                        Toast.makeText(this@MainActivity, "删除成功", Toast.LENGTH_SHORT).show()
                    }

                }).setNegativeButton("取消", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        //关闭对话框
                        mDialog.dismiss()
                    }
                }).create()

    }
    private val onItemClickListener = object : OnItemClickListener {
        override fun onClickListener(convertView: View, position: Int) {
            when(position){
                0 ->{
                    if(!mDialog.isShowing)mDialog.show()
                }
                1 ->{
                    startActivityForResult(Intent(this@MainActivity, WebViewActivity::class.java), Activity.RESULT_OK);
                }
                2 ->{
                    startActivityForResult(Intent(this@MainActivity, SecondActivity::class.java), Activity.RESULT_OK);
                }
            }


        }
    }

    override fun onDestroy() {
        super.onDestroy()
        //关闭窗口，避免窗口溢出
        if(mDialog.isShowing)mDialog.dismiss()
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
