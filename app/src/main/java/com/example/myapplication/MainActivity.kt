package com.example.myapplication

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.myapplication.fragment.fragment1.FirstFragment
import com.example.myapplication.fragment.fragment2.SecondFragment
import com.example.myapplication.util.Constants


interface IFragmentCallBack {
    fun switchFragment(idFragment: Int, textName: String, textPhone: String)
}
class MainActivity : AppCompatActivity(R.layout.activity_main), IFragmentCallBack {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val fm: FragmentManager = supportFragmentManager
        val ftAdd: FragmentTransaction = fm.beginTransaction()
        ftAdd.add(R.id.frameLayout, FirstFragment())
        ftAdd.commit()
    }

    override fun switchFragment(idFragment: Int, textName: String, textPhone: String) {
        val fm: FragmentManager = supportFragmentManager
        if(idFragment == Constants.idFragmentFirst){
            val fmReplace: FragmentTransaction = fm.beginTransaction()
            fmReplace.replace(R.id.frameLayout, SecondFragment.instance1(textName, textPhone))
            fmReplace.commit()
        } else if(idFragment == Constants.idFragmentSecond){
            val fmReplace: FragmentTransaction = fm.beginTransaction()
            fmReplace.replace(R.id.frameLayout, FirstFragment.instance1(textName))
            fmReplace.commit()
        }
    }
}