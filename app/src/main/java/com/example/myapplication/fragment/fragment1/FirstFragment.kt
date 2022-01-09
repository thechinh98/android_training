package com.example.myapplication.fragment.fragment1

import android.content.ContentValues
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.myapplication.IFragmentCallBack
import com.example.myapplication.MainActivity
import com.example.myapplication.MainViewModel
import com.example.myapplication.R
import com.example.myapplication.content_provider.DictionaryProvider
import com.example.myapplication.util.Constants

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(R.layout.fragment_first) {
    private val idFragment: Int = Constants.idFragmentFirst
    lateinit var iFragmentCallBack: IFragmentCallBack
    var string: String = ""
    private val viewModel: MainViewModel by activityViewModels()

    companion object {
        fun instance1(stringText: String): FirstFragment {
            val fragment = FirstFragment()
            val bundle = bundleOf("text" to stringText)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (context is MainActivity) {
            iFragmentCallBack = context as MainActivity
        }
        arguments?.let {
            val text = arguments?.getString("text")
            string = text.toString()
        }
        val btnSwitch = view.findViewById<Button>(R.id.button_first)
        val btnAdd= view.findViewById<Button>(R.id.buttonAdd)
        val nameEditText = view.findViewById<EditText>(R.id.edtFirst)
        val phoneEditText = view.findViewById<EditText>(R.id.edtPhone)
        nameEditText?.setText(string)
        btnSwitch?.setOnClickListener {
            val textName = nameEditText?.text.toString()
            val textPhone = phoneEditText?.text.toString()
            viewModel.savePreviousValue(textName, textPhone)
            iFragmentCallBack.switchFragment(idFragment, textName, textPhone)
        }

        btnAdd.setOnClickListener {
            val textName = nameEditText?.text.toString()
            val textPhone = phoneEditText?.text.toString()
            onClickAdd(textName, textPhone)
        }
    }

    private fun onClickAdd(name: String, phone: String){
        val value = ContentValues()
        value.put(DictionaryProvider.name, name)
        value.put(DictionaryProvider.phone, phone)
        context?.contentResolver?.insert(DictionaryProvider.CONTENT_URI, value)
        Toast.makeText(context, "ADD $name - $phone", Toast.LENGTH_LONG).show()
    }
}