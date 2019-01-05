package com.gkzxhn.app.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import com.gkzxhn.app.R
import kotlinx.android.synthetic.main.main_item_layout.view.main_item_layout_tv_title
as tvTitle

class MainAdapter (private val mContext: Context) : RecyclerView.Adapter<ViewHolder>(){
    private var onItemClickListener: OnItemClickListener? = null
    private var mDatas:ArrayList<String> = ArrayList<String>()
    fun setOnItemClickListener(onItemClickListener: OnItemClickListener?) {
        this.onItemClickListener = onItemClickListener
    }
    /**
     * 更新数据
     */
    fun updateItems(mDatas: List<String>?) {
        this.mDatas.clear()
        if (mDatas != null && mDatas.isNotEmpty()) {
            this.mDatas.addAll(mDatas)
        }
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.main_item_layout, null)
        view.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder.itemView){
            if(position<mDatas.size){
                tvTitle.setText(mDatas[position])
            }else{
                tvTitle.setText("Item "+position)
            }
            tvTitle.setOnClickListener({
                onItemClickListener?.onClickListener(this,position)
            })
        }
    }
}