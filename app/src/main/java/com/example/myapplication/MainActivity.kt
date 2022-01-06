package com.example.myapplication

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


interface IFragmentCallBack {
    fun switchFragment(idFragment: Int, textFill: String)
}
class MainActivity : AppCompatActivity(R.layout.activity_main), IFragmentCallBack {
    override fun onResume() {
        super.onResume()
        val fm: FragmentManager = supportFragmentManager
        val ftAdd: FragmentTransaction = fm.beginTransaction()
        ftAdd.add(R.id.frameLayout,FirstFragment())
        ftAdd.commit()
        print("CREATE VIEW")
    }

    override fun switchFragment(idFragment: Int, textFill: String) {
        if(idFragment == Constants.idFragmentFirst){
            val fm: FragmentManager = supportFragmentManager
            val fmReplace: FragmentTransaction = fm.beginTransaction()
            fmReplace.replace(R.id.frameLayout, SecondFragment.instance1(textFill))
        } else if(idFragment == Constants.idFragmentFirst){
            val fm: FragmentManager = supportFragmentManager
            val fmReplace: FragmentTransaction = fm.beginTransaction()
            fmReplace.replace(R.id.frameLayout, FirstFragment.instance1(textFill))
        }
    }
}