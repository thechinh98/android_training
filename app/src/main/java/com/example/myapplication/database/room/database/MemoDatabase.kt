package com.example.myapplication.database.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.myapplication.database.CommonDatabase
import com.example.myapplication.database.room.dao.MemoDao
import com.example.myapplication.database.room.entities.MemoEntity
import com.example.myapplication.model.MemoModel

@Database(entities = [MemoEntity::class], version = 1)
abstract class MemoDatabase : RoomDatabase(), CommonDatabase {
    companion object {
        fun buildDatabase(context: Context, tableName: String) =
            Room.databaseBuilder(context, MemoDatabase::class.java, tableName)
                .allowMainThreadQueries().build()
    }

    abstract fun memoDao(): MemoDao
    private var memoDatabase: MemoDatabase? = null
    override fun initDatabase(commonDatabase: CommonDatabase) {
        if (commonDatabase is MemoDatabase) {
            memoDatabase = commonDatabase
        }
    }

    override fun insert(memoModel: MemoModel) {
        if (memoDatabase != null) {
            memoDao().insert(MemoEntity.createMemo(memoModel))
        }
    }

    override fun update(memoModel: MemoModel) {
        if (memoDatabase != null) {
            memoDao().update(MemoEntity.createMemo(memoModel))
        }
    }

    override fun delete(memoModel: MemoModel) {
        if (memoDatabase != null) {
            memoDao().delete(MemoEntity.createMemo(memoModel))
        }
    }

    override fun getList(): MutableList<MemoModel> {
        if (memoDatabase != null) {
            return convertData(memoDao().getAllOrderByUpdateTime())
        }
        return mutableListOf()
    }

    private fun convertData(listEntities: MutableList<MemoEntity>): MutableList<MemoModel> {
        val listUpdateMemo: MutableList<MemoModel> = listEntities.map { memoEntity ->
            MemoModel.fromMemoEntity(memoEntity)
        } as MutableList<MemoModel>

        return listUpdateMemo
    }
}