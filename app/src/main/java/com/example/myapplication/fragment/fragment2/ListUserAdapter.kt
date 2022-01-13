package com.example.myapplication.fragment.fragment2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.model.UserModel

class ListUserAdapter : RecyclerView.Adapter<ListUserAdapter.ViewHolder>() {
    var listUserData : MutableList<UserModel> = mutableListOf()
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val textId : TextView = view.findViewById(R.id.userId)
        val textName: TextView = view.findViewById(R.id.userName)
        val textPhone: TextView = view.findViewById(R.id.userPhone)

        fun bindData(userModel: UserModel){
            textId.text = userModel.id
            textName.text = userModel.name
            textPhone.text = userModel.phone
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user_data, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val userModel = listUserData[position]
        holder.bindData(userModel)
    }

    override fun getItemCount(): Int = listUserData.size

    fun addItem(itemUser: UserModel){
        listUserData.add(itemUser)
        notifyItemChanged(listUserData.size)
    }

    fun addList(listUser: MutableList<UserModel>){
        listUserData.clear()
        notifyItemRangeRemoved(0, listUserData.size)
        listUserData = listUser
        notifyItemRangeChanged(0, listUserData.size)
    }

}