package com.example.myapplication.fragment.fragment1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.model.GitRepoModel

class ListGitRepoAdapter : RecyclerView.Adapter<ItemGitRepoViewHolder>() {
    var listGitRepo : MutableList<GitRepoModel> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemGitRepoViewHolder =
        ItemGitRepoViewHolder.create(LayoutInflater.from(parent.context), parent)


    override fun onBindViewHolder(holder: ItemGitRepoViewHolder, position: Int) {
        val model = listGitRepo[position]
        holder.bind(model)
    }

    override fun getItemCount(): Int = listGitRepo.size

    fun addData(listInputGitRepo : MutableList<GitRepoModel>){
        val oldSize = listGitRepo.size
        listGitRepo.clear()
        notifyItemRangeRemoved(0, oldSize)
        listGitRepo = listInputGitRepo
        notifyItemRangeInserted(0,listGitRepo.size)

    }

}