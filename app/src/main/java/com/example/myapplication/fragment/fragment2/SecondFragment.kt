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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.IFragmentCallBack
import com.example.myapplication.MainActivity
import com.example.myapplication.MainViewModel
import com.example.myapplication.R
import com.example.myapplication.content_provider.DictionaryProvider
import com.example.myapplication.model.UserModel
import com.example.myapplication.util.Constants
import io.reactivex.rxjava3.subjects.ReplaySubject

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment(R.layout.fragment_second) {
    private val idFragment: Int = Constants.idFragmentSecond
    lateinit var iFragmentCallBack: IFragmentCallBack
    var nameText: String = ""
    var phoneText: String = ""
    val DictionaryURL = DictionaryProvider.URL

    var rxContent: ReplaySubject<String>? = null
    private val viewModel: MainViewModel by activityViewModels()


    var listUserAdapter = ListUserAdapter()

    companion object {
        fun instance1(stringName: String, stringPhone: String): SecondFragment {
            val fragment = SecondFragment()
            val bundle = bundleOf("name" to stringName, "phone" to stringPhone)
            fragment.arguments = bundle
            return fragment
        }

        fun instance2(rxContent: ReplaySubject<String>): SecondFragment {
            val fragment = SecondFragment()
            fragment.rxContent = rxContent
            return fragment
        }
    }


    override fun onStart() {
        super.onStart()
        if (context is MainActivity) {
            iFragmentCallBack = context as MainActivity
        }
        val previousInput = view?.findViewById<TextView>(R.id.previousInputText)
        val editText = view?.findViewById<EditText>(R.id.previousInputEditText)
        arguments?.let {
            val name = arguments?.getString("name")
            val phone = arguments?.getString("phone")
            nameText = name.toString()
            phoneText = phone.toString()
        }
//

        editText?.setText(nameText)
        "Previous Input: $nameText - $phoneText".also { previousInput?.text = it }

//        USing share View Model
//        nameText = viewModel.nameText.value ?: ""
//        phoneText = viewModel.phoneText.value ?: ""

//        Using Rx
//        rxContent?.subscribe { fullText ->
//            previousInput?.text = fullText
//        }

        val btnSwitch = view?.findViewById<Button>(R.id.btnSwitch)
        btnSwitch?.setOnClickListener {
            val text = editText?.text.toString()
            iFragmentCallBack.switchFragment(idFragment, text, "")
        }
    }

    private fun onClickShowDetails(view: View?) {
        // inserting complete table details in this text field


        val listUser: MutableList<UserModel> = mutableListOf()
        // creating a cursor object of the
        // content URI

        val cursor =
            context?.contentResolver?.query(Uri.parse(DictionaryURL), null, null, null, null)

        // iteration of the cursor
        // to print whole table
        cursor.use { cursorLoader ->
            if (cursorLoader!!.moveToFirst()) {
                while (!cursorLoader.isAfterLast) {
                    val id = cursorLoader.getString(cursorLoader.getColumnIndexOrThrow("id"))
                    val name = cursorLoader.getString(
                        cursorLoader.getColumnIndexOrThrow(
                            "name"
                        )
                    )
                    val phone = cursorLoader.getString(cursorLoader.getColumnIndexOrThrow("phone"))
                    listUser.add(UserModel(id, name, phone))
                    cursorLoader.moveToNext()
                }
            } else {
                print("Xin la xin vinh biet")
            }
        }
        listUserAdapter.addList(listUser)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val buttonLoad = view.findViewById<Button>(R.id.loadButton)
        buttonLoad.setOnClickListener {
            onClickShowDetails(view)
        }
        val recyclerView = view.findViewById<RecyclerView>(R.id.listUserRcv)
        val customLayoutManager = LinearLayoutManager(context)
        recyclerView.apply {
            adapter = listUserAdapter
            layoutManager = customLayoutManager
        }

    }

}