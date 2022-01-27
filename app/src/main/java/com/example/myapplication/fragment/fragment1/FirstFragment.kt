package com.example.myapplication.fragment.fragment1

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.MainViewModel
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFirstBinding
import com.example.myapplication.util.Constants
import com.example.myapplication.viewmodelFactory.ViewModelFactory


class FirstFragment : Fragment(R.layout.fragment_first) {
    private val idFragment: Int = Constants.idFragmentFirst
    var string: String = ""
    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var firstFragmentViewModel: FirstFragmentViewModel
    private var binding: FragmentFirstBinding? = null
    private var factory: ViewModelFactory = ViewModelFactory()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        val listRepoAdapter = ListGitRepoAdapter()
        val listRepoLayoutManager = LinearLayoutManager(context)
        recyclerView.apply {
            adapter = listRepoAdapter
            layoutManager = listRepoLayoutManager
        }
        firstFragmentViewModel = ViewModelProvider(this, factory).get(FirstFragmentViewModel::class.java)
        firstFragmentViewModel.getData()
        firstFragmentViewModel.listGitRepo.observe(viewLifecycleOwner){
            listRepoAdapter.addData(it)
        }

    }

    companion object {
        fun instance1(stringText: String): FirstFragment {
            val fragment = FirstFragment()
            val bundle = bundleOf("text" to stringText)
            fragment.arguments = bundle
            return fragment
        }
    }

}