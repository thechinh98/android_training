package com.example.myapplication.database

import com.example.myapplication.model.MemoModel

interface CommonDatabase {
    fun initDatabase(commonDatabase: CommonDatabase)
    fun insert(memoModel : MemoModel) : Long
    fun update(memoModel: MemoModel)
    fun getList(): MutableList<MemoModel>
    fun delete(memoModel: MemoModel)
}