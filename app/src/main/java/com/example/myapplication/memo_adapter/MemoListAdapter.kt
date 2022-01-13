package com.example.myapplication.memo_adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.MemoModel
import java.text.SimpleDateFormat

typealias onClickItem = (MemoModel) -> Unit

class MemoListAdapter : RecyclerView.Adapter<MemoListAdapter.ViewHolder>() {
    var listMemo: MutableList<MemoModel> = mutableListOf()
    var onClickItem : onClickItem? = null
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textId = itemView.findViewById<TextView>(R.id.txt_id)
        private val textTitle = itemView.findViewById<TextView>(R.id.txt_title)
        private val textContent = itemView.findViewById<TextView>(R.id.txt_content)
        private val textUpdateTime = itemView.findViewById<TextView>(R.id.txt_update_time)

        @SuppressLint("SimpleDateFormat")
        fun bindData(memoModel: MemoModel, onClickItem: onClickItem?){
            val formatDate = SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(memoModel.updateAt!!)
            textId.text = memoModel.id.toString()
            textContent.text = memoModel.content
            textTitle.text = memoModel.title
            textUpdateTime.text = formatDate
            itemView.setOnClickListener { onClickItem?.invoke(memoModel) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_memo, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentMemo = listMemo[position]
        holder.bindData(currentMemo, onClickItem)
    }

    override fun getItemCount(): Int = listMemo.size

    fun addMemo(memo: MemoModel){
        listMemo.add(memo)
        notifyItemChanged(listMemo.size)
    }

    fun updateListMemo(updateList: MutableList<MemoModel>){
        val oldSize = listMemo.size
        listMemo.clear()
        notifyItemRangeRemoved(0, oldSize)
        listMemo = updateList
        notifyItemRangeChanged(0, listMemo.size)
    }
}