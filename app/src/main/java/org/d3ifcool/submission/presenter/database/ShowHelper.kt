package org.d3ifcool.submission.presenter.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.d3ifcool.submission.presenter.database.DatabaseFavorite.ShowColumns.Companion.TABLE_SHOW
import org.d3ifcool.submission.presenter.database.DatabaseFavorite.ShowColumns.Companion.TITLE

class ShowHelper (context: Context) {

    companion object {
        private const val DATABASE_TABLE = TABLE_SHOW
        private lateinit var databaseShowHelper : DatabaseHelper
        private var INSTANCE : ShowHelper? = null
        private lateinit var database : SQLiteDatabase
        fun getInstance(context: Context) : ShowHelper {
            if (INSTANCE == null) {
                synchronized(SQLiteOpenHelper::class.java){
                    if (INSTANCE==null) INSTANCE = ShowHelper(context)
                }
            }
            return INSTANCE as ShowHelper
        }
    }

    init {
        databaseShowHelper = DatabaseHelper(context)
    }

    @Throws(SQLException::class)
    fun openDB(){
        database = databaseShowHelper.writableDatabase
    }

    fun closeDB(){
        if (database.isOpen){
            database.close()
            databaseShowHelper.close()
        }
    }

    fun readAll() : Cursor {
        val cursor = database.rawQuery("SELECT * FROM $DATABASE_TABLE ORDER BY $TITLE ASC", null)
        cursor.moveToFirst()
        return database.rawQuery("SELECT * FROM $DATABASE_TABLE ORDER BY $TITLE ASC", null)
    }

    fun selectData(title : String) : Cursor {
        return database.rawQuery("SELECT COUNT(*) FROM $DATABASE_TABLE WHERE $TITLE='$title'", null)
    }

    fun insert(value: ContentValues?) : Long{
        return database.insert(DATABASE_TABLE, null, value)
    }

    fun delete(title: String) : Int {
        return database.delete(DATABASE_TABLE,"$TITLE = '$title'", null)
    }
}