package com.example.myapplication.database.sqlite.contract

import android.provider.BaseColumns
import com.example.myapplication.util.Constants

object MemoContract {
    object MemoEntry: BaseColumns{
        const val TABLE_NAME = Constants.tableName
        const val COLUMN_NAME_TITLE = "title"
        const val COLUMN_NAME_CONTENT = "content"
        const val COLUMN_NAME_CREATE_AT = "create_at"
        const val COLUMN_NAME_UPDATE_AT = "update_at"
    }
}