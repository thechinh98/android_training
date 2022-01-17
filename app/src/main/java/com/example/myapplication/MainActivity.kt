package com.example.myapplication

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.database.CommonDatabase
import com.example.myapplication.database.room.dao.MemoDao
import com.example.myapplication.database.room.database.MemoDatabase
import com.example.myapplication.database.sqlite.helper.MemoDatabaseHelper
import com.example.myapplication.memo_adapter.MemoListAdapter
import com.example.myapplication.model.MemoModel
import com.example.myapplication.util.Constants

import java.util.*


interface IFragmentCallBack {
    fun switchFragment(idFragment: Int, textName: String, textPhone: String)
}

class MainActivity : AppCompatActivity(R.layout.activity_main), IFragmentCallBack {
    private lateinit var memoDb: CommonDatabase
    private lateinit var memoDao: MemoDao
    private lateinit var memoListAdapter: MemoListAdapter
    override fun switchFragment(idFragment: Int, textName: String, textPhone: String) {
        // K co tac dung gi dau
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        memoDb = MemoDatabase.buildDatabase(applicationContext, Constants.tableName)

//        memoDb = MemoDatabaseHelper(applicationContext)
        memoDb.initDatabase(memoDb)
        // init view
        val buttonAdd = findViewById<Button>(R.id.btn_add)
        val inputTitle = findViewById<EditText>(R.id.edt_title)
        val inputContent = findViewById<EditText>(R.id.edt_content)
        val memoRecyclerView = findViewById<RecyclerView>(R.id.rcv_memo)
        memoListAdapter = MemoListAdapter()

        //Pop up
        val popUp = findViewById<ConstraintLayout>(R.id.popUp)
        val edtUpdateTitle = findViewById<EditText>(R.id.edt_update_title)
        val edtUpdateContent = findViewById<EditText>(R.id.edt_update_content)
        val btnUpdate = findViewById<Button>(R.id.btn_update)
        val btnDelete = findViewById<Button>(R.id.btn_delete)

        memoRecyclerView.apply {
            adapter = memoListAdapter
            layoutManager = LinearLayoutManager(context)
        }
        updateData()

        memoListAdapter.onClickItem = { memoModel ->
            popUp.visibility = View.VISIBLE
            edtUpdateTitle.setText(memoModel.title)
            edtUpdateContent.setText(memoModel.content)
            btnDelete.setOnClickListener {
                memoDb.delete(memoModel)
                popUp.visibility = View.GONE
                updateData()
            }
            btnUpdate.setOnClickListener {
                val oldModel = memoModel.copy()
                memoModel.title = edtUpdateTitle.text.toString()
                memoModel.content = edtUpdateContent.text.toString()
                if (memoModel != oldModel) {
                    memoModel.updateAt = getCurrentTime()
                    memoDb.update(memoModel)
                    updateData()
                }
                popUp.visibility = View.GONE
            }
        }
        buttonAdd.setOnClickListener {
            val title = inputTitle.text.toString()
            val content = inputContent.text.toString()
            val time = getCurrentTime()
            val memoModel=  MemoModel(
                title = title,
                content = content,
                createAt = time,
                updateAt = time
            )
            val rowId = memoDb.insert(
               memoModel
            )
            memoModel.id = rowId.toInt()
            memoListAdapter.addMemo(memoModel)
            inputTitle.setText("")
            inputContent.setText("")
        }
    }

    private fun updateData() {
        val listUpdateMemo: MutableList<MemoModel> = memoDb.getList()
        memoListAdapter.updateListMemo(listUpdateMemo)
    }

    private fun getCurrentTime(): Date {
        return Calendar.getInstance().time
    }
}