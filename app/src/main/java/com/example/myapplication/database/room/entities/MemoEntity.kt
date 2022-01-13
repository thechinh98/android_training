package com.example.myapplication.database.room.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.myapplication.model.MemoModel
import com.example.myapplication.util.DateConverter

@Entity(tableName = "memo")
data class MemoEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id")
    val uid: Int,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "create_at")
    val createAt: Long,
    @ColumnInfo(name = "update_at")
    val updateAt: Long,
) {
    companion object {
        fun createMemo(memoModel: MemoModel): MemoEntity {
            // use to convert from other object
            val dateConverter = DateConverter()
            return MemoEntity(
                memoModel.id,
                memoModel.title,
                memoModel.content,
                dateConverter.fromDate(memoModel.createAt) ?: 0,
                dateConverter.fromDate(memoModel.updateAt) ?: 0
            )
        }
    }
}