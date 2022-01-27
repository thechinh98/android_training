package com.example.myapplication.fragment.fragment1

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemGitRepoBinding
import com.example.myapplication.model.GitRepoModel

class ItemGitRepoViewHolder constructor(private val binding: ItemGitRepoBinding): RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun create(inflater: LayoutInflater, parent: ViewGroup) =
            ItemGitRepoViewHolder(ItemGitRepoBinding.inflate(inflater, parent, false))

    }

    fun bind(model: GitRepoModel) {
        binding.model = model

    }
}