package com.example.myapplication.database.sqlite.helper

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.example.myapplication.database.CommonDatabase
import com.example.myapplication.database.sqlite.contract.MemoContract
import com.example.myapplication.database.sqlite.model.MemoSqlModel
import com.example.myapplication.model.MemoModel
import com.example.myapplication.util.DateConverter


class MemoDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION), CommonDatabase {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    companion object {
        // If you change the database schema, you must increment the database version
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Memo.db"
        private const val SQL_CREATE_ENTRIES =
            "CREATE TABLE ${MemoContract.MemoEntry.TABLE_NAME} (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "${MemoContract.MemoEntry.COLUMN_NAME_TITLE} TEXT," +
                    "${MemoContract.MemoEntry.COLUMN_NAME_CONTENT} TEXT," +
                    "${MemoContract.MemoEntry.COLUMN_NAME_CREATE_AT} INTEGER," +
                    "${MemoContract.MemoEntry.COLUMN_NAME_UPDATE_AT} INTEGER)"


        private const val SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS ${MemoContract.MemoEntry.TABLE_NAME}"
    }

    var memoDatabaseHelper: MemoDatabaseHelper? = null
    var memoDb : SQLiteDatabase? = null
    val dateConverter = DateConverter()
    override fun initDatabase(commonDatabase: CommonDatabase) {
        if (commonDatabase is MemoDatabaseHelper) {
            memoDatabaseHelper = commonDatabase
            memoDb = memoDatabaseHelper?.writableDatabase
        }
    }

    override fun insert(memoModel: MemoModel): Long {
        // Create a new map of values, where column names are the keys
        if (memoDatabaseHelper != null) {
            val values = ContentValues().apply {
                put(MemoContract.MemoEntry.COLUMN_NAME_TITLE, memoModel.title)
                put(MemoContract.MemoEntry.COLUMN_NAME_CONTENT, memoModel.content)
                put(
                    MemoContract.MemoEntry.COLUMN_NAME_CREATE_AT,
                    dateConverter.fromDate(memoModel.createAt)
                )
                put(
                    MemoContract.MemoEntry.COLUMN_NAME_UPDATE_AT,
                    dateConverter.fromDate(memoModel.updateAt)
                )
            }
            val rowId = memoDb?.insert(MemoContract.MemoEntry.TABLE_NAME, "", values) ?: 0
            return rowId
        }
        return 0
    }

    override fun update(memoModel: MemoModel) {
        if(memoDatabaseHelper != null){
            val values = ContentValues().apply {
                put(MemoContract.MemoEntry.COLUMN_NAME_TITLE, memoModel.title)
                put(MemoContract.MemoEntry.COLUMN_NAME_CONTENT, memoModel.content)
                put(
                    MemoContract.MemoEntry.COLUMN_NAME_CREATE_AT,
                    dateConverter.fromDate(memoModel.createAt)
                )
                put(
                    MemoContract.MemoEntry.COLUMN_NAME_UPDATE_AT,
                    dateConverter.fromDate(memoModel.updateAt)
                )
            }
            val whereClause = "${BaseColumns._ID}= ?"
            val whereArgs = arrayOf(memoModel.id.toString())
            memoDb?.update(MemoContract.MemoEntry.TABLE_NAME, values, whereClause,whereArgs)
        }
    }

    override fun getList(): MutableList<MemoModel> {
        if(memoDatabaseHelper != null){
            val listMemo : MutableList<MemoModel> = mutableListOf()
            val sortOrder = "${MemoContract.MemoEntry.COLUMN_NAME_UPDATE_AT} DESC"
            val cursor = memoDb?.query(MemoContract.MemoEntry.TABLE_NAME, null,null,null,null,null,sortOrder)
            with(cursor) {
                while (this?.moveToNext() == true) {
                    val itemId = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                    val itemTitle = getString(getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_TITLE))
                    val itemContent = getString(getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_CONTENT))
                    val createAt = getLong(getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_CREATE_AT))
                    val updateAt = getLong(getColumnIndexOrThrow(MemoContract.MemoEntry.COLUMN_NAME_UPDATE_AT))

                    val memoSqlModel = MemoSqlModel(
                        id = itemId,
                        title = itemTitle,
                        content = itemContent,
                        createAt = createAt,
                        updateAt = updateAt
                    )
                    listMemo.add(MemoModel.fromMemoSqlModel(memoSqlModel))
                }
            }
            cursor?.close()
            return listMemo

        }
        return mutableListOf()
    }

    override fun delete(memoModel: MemoModel) {
        if(memoDatabaseHelper != null){
            val whereClause = "${BaseColumns._ID} = ?"
            val whereArgs = arrayOf(memoModel.id.toString())
            memoDb?.delete(MemoContract.MemoEntry.TABLE_NAME, whereClause,whereArgs)
        }
    }
}