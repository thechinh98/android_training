package com.example.myapplication

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(R.layout.fragment_first){
    private val idFragment : Int = Constants.idFragmentFirst
    lateinit var iFragmentCallBack : IFragmentCallBack
    var string : String = ""

    companion object{
        fun instance1(stringText: String) : FirstFragment{
            val fragment = FirstFragment()
            val bundle = bundleOf("text" to stringText)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        if(context is MainActivity){
            iFragmentCallBack = context as MainActivity
        }
        arguments?.let {
            val text = arguments?.getString("text")
            string = text.toString()
        }
        val v = inflater.inflate(R.layout.fragment_first, container, false)
        val btnSwitch = v.findViewById<Button>(R.id.button_first)
        val editText = v.findViewById<EditText>(R.id.edtFirst)
        editText.setText(string)
        val textFirst = editText.text.toString()
        btnSwitch.setOnClickListener{
            iFragmentCallBack.switchFragment(idFragment,textFirst)
        }
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}