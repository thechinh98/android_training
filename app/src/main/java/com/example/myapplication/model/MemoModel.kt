package com.example.myapplication.model

import com.example.myapplication.database.room.entities.MemoEntity
import com.example.myapplication.database.sqlite.model.MemoSqlModel
import com.example.myapplication.util.DateConverter
import java.util.*

data class MemoModel(
    var id: Int = 0,
    var title : String = "",
    var content : String = "",
    val createAt: Date?,
    var updateAt: Date?,
) {
    companion object{
        val dateConverter = DateConverter()
        fun fromMemoEntity(memoEntity: MemoEntity) : MemoModel {
            return MemoModel(
                id = memoEntity.uid,
                title = memoEntity.title,
                content = memoEntity.content,
                createAt = dateConverter.toDate(memoEntity.createAt),
                updateAt = dateConverter.toDate(memoEntity.updateAt)
            )
        }
        fun fromMemoSqlModel(memoSqlModel: MemoSqlModel) : MemoModel{
            return MemoModel(
                id = memoSqlModel.id,
                title = memoSqlModel.title,
                content = memoSqlModel.content,
                createAt = dateConverter.toDate(memoSqlModel.createAt),
                updateAt = dateConverter.toDate(memoSqlModel.updateAt)
            )
        }
    }
}