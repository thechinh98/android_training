package com.example.myapplication.database.sqlite.model

import com.example.myapplication.model.MemoModel
import com.example.myapplication.util.DateConverter

data class MemoSqlModel (
    var id: Int = 0,
    var title: String,
    var content: String,
    val createAt: Long,
    var updateAt: Long,
        ) {
    companion object{
        fun fromMemoModel(memoModel: MemoModel) : MemoSqlModel{
            val dateConverter = DateConverter()
            return MemoSqlModel(
                id = memoModel.id,
                title = memoModel.title,
                content = memoModel.content,
                createAt = dateConverter.fromDate(memoModel.createAt) ?: 0,
                updateAt = dateConverter.fromDate(memoModel.updateAt) ?: 0

            )
        }
    }
}