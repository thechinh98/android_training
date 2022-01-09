package com.example.myapplication.fragment.fragment2

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import com.example.myapplication.IFragmentCallBack
import com.example.myapplication.MainActivity
import com.example.myapplication.MainViewModel
import com.example.myapplication.R
import com.example.myapplication.content_provider.DictionaryProvider
import com.example.myapplication.util.Constants

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(R.layout.fragment_second) {
    private val idFragment: Int = Constants.idFragmentSecond
    lateinit var iFragmentCallBack: IFragmentCallBack
    var nameText: String = ""
    var phoneText: String = ""
    val DictionaryURL = DictionaryProvider.URL
    val CONTENT_URI = Uri.parse(DictionaryURL)

    private val viewModel: MainViewModel by activityViewModels()

    companion object {
        fun instance1(stringName: String, stringPhone: String): SecondFragment {
            val fragment = SecondFragment()
            val bundle = bundleOf("name" to stringName, "phone" to stringPhone)
            fragment.arguments = bundle
            return fragment
        }
    }


    override fun onStart() {
        super.onStart()
        if(context is MainActivity){
            iFragmentCallBack = context as MainActivity
        }
//        arguments?.let {
//            val name = arguments?.getString("name")
//            val phone = arguments?.getString("phone")
//            nameText= name.toString()
//            phoneText = phone.toString()
//        }
        nameText = viewModel.nameText.value ?: ""
        phoneText = viewModel.phoneText.value ?: ""
        val textView = view?.findViewById<TextView>(R.id.previousInputText)
        val btnSwitch = view?.findViewById<Button>(R.id.btnSwitch)
        textView?.text = "Previous Input: $nameText - $phoneText"
        btnSwitch?.setOnClickListener {
            iFragmentCallBack.switchFragment(idFragment, "", "")
        }
    }

    private fun onClickShowDetails(view: View?) {
        // inserting complete table details in this text field

        val resultView = view!!.findViewById<TextView>(R.id.res)
        // creating a cursor object of the
        // content URI

        val cursor =
            context?.contentResolver?.query(Uri.parse(DictionaryURL), null, null, null, null)

        // iteration of the cursor
        // to print whole table
        cursor.use { cursor ->
            if (cursor!!.moveToFirst()) {
                val strBuild = StringBuilder()
                while (!cursor.isAfterLast) {
                    strBuild.append(
                        """
          
        ${cursor.getString(cursor.getColumnIndexOrThrow("id"))}-${
                            cursor.getString(
                                cursor.getColumnIndexOrThrow(
                                    "name"
                                )
                            )
                        } -  ${cursor.getString(cursor.getColumnIndexOrThrow("phone"))}
        """.trimIndent()
                    )
                    cursor.moveToNext()
                }
                resultView.text = strBuild
            } else {
                resultView.text = "No Records Found"
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonLoad = view.findViewById<Button>(R.id.loadButton)
        buttonLoad.setOnClickListener {
            onClickShowDetails(view)
        }
    }

}