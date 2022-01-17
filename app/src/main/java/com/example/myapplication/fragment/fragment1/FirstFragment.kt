package com.example.myapplication.fragment.fragment1

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.myapplication.MainViewModel
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentFirstBinding
import com.example.myapplication.util.Constants


class FirstFragment : Fragment(R.layout.fragment_first) {
    private val idFragment: Int = Constants.idFragmentFirst
    var string: String = ""
    private val viewModel: MainViewModel by activityViewModels()

    private lateinit var firstFragmentViewModel: FirstFragmentViewModel
    private var binding: FragmentFirstBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_first, container,
            false
        )
        binding?.lifecycleOwner = this
        firstFragmentViewModel = ViewModelProvider(this).get(FirstFragmentViewModel::class.java)
        binding?.viewModel = firstFragmentViewModel
        return binding?.root
    }

    companion object {
        fun instance1(stringText: String): FirstFragment {
            val fragment = FirstFragment()
            val bundle = bundleOf("text" to stringText)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        firstFragmentViewModel =
            ViewModelProvider(this).get(FirstFragmentViewModel::class.java)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firstFragmentViewModel.stringFirst.observe(viewLifecycleOwner) {
            Log.d("LOG NE", it)
        }
        Log.d("LOG NE", "HELLO")
    }
}