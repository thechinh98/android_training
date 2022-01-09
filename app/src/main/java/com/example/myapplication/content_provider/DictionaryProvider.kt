package com.example.myapplication.content_provider

import android.content.*
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri

class DictionaryProvider : ContentProvider(){
    companion object{
        const val PROVIDER_NAME = "com.example.user.dictionary"
        const val URL = "content://$PROVIDER_NAME/dictionary" // content://com.example.user.dictionary/dictionary
        val CONTENT_URI = Uri.parse(URL)
        const val id = "id"
        const val name = "name"
        const val phone = "phone"
        const val uriCode = 1
        var uriMatcher : UriMatcher? = null
        private val values: HashMap<String, String>? = null
        const val DATABASE_NAME = "DictionaryDB"
        const val TABLE_NAME = "Users"
        const val DATABASE_VERSION = 1
        // 1 bang, 3 cot, id, ten , sdt
        const val CREATE_DB_TABLE = ("CREATE TABLE $TABLE_NAME (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL , phone TEXT NOT NULL)")

        init{
            uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            uriMatcher!!.addURI(
                PROVIDER_NAME,
                "dictionary",
                uriCode
            )

            uriMatcher!!.addURI(
                PROVIDER_NAME,
                "dictionary/*",
                uriCode
            )
        }
    }
    override fun onCreate(): Boolean {
        val context = context
        val dbHelper = DatabaseHelper(context)
        db = dbHelper.writableDatabase
        return db != null
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        var customSortOrder = sortOrder
        val qb = SQLiteQueryBuilder()
        qb.tables = TABLE_NAME
        when (uriMatcher!!.match(uri)) {
            uriCode -> qb.projectionMap = values
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
        if (customSortOrder == null || customSortOrder === "") {
            customSortOrder = id
        }
        val c = qb.query(
            db, projection, selection, selectionArgs, null,
            null, customSortOrder
        )
        c.setNotificationUri(context!!.contentResolver, uri)
        return c
    }

    override fun getType(uri: Uri): String? {
        TODO("Not yet implemented")
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri {
        val rowID = db!!.insert(TABLE_NAME, "", values)
        if (rowID > 0) {
            val contentUri =
                ContentUris.withAppendedId(CONTENT_URI, rowID)
            context!!.contentResolver.notifyChange(contentUri, null)
            return contentUri
        }
        throw SQLiteException("Failed to add a record into $uri")
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        var count :Int
        count = when (uriMatcher!!.match(uri)) {
            uriCode -> db!!.delete(TABLE_NAME, selection, selectionArgs)
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
        context!!.contentResolver.notifyChange(uri, null)
        return count
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        var count = 0
        count = when (uriMatcher!!.match(uri)) {
            uriCode -> db!!.update(TABLE_NAME, values, selection, selectionArgs)
            else -> throw IllegalArgumentException("Unknown URI $uri")
        }
        context!!.contentResolver.notifyChange(uri, null)
        return count
    }

    private var db: SQLiteDatabase? = null

    // creating a database
    private class DatabaseHelper  // defining a constructor
    constructor(context: Context?) : SQLiteOpenHelper(
        context,
        DATABASE_NAME,
        null,
        DATABASE_VERSION
    ) {
        // creating a table in the database
        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(CREATE_DB_TABLE)
        }

        override fun onUpgrade(
            db: SQLiteDatabase,
            oldVersion: Int,
            newVersion: Int
        ) {
            db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
            onCreate(db)
        }
    }
}