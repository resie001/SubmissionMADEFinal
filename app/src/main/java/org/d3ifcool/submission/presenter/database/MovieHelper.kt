package org.d3ifcool.submission.presenter.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.d3ifcool.submission.presenter.database.DatabaseFavorite.MovieColumns.Companion.TABLE_MOVIE
import org.d3ifcool.submission.presenter.database.DatabaseFavorite.MovieColumns.Companion.TITLE

class MovieHelper (context: Context) {

    companion object {
        private const val DATABASE_TABLE = TABLE_MOVIE
        private lateinit var databaseHelper : DatabaseHelper
        private var INSTANCE : MovieHelper? = null
        private lateinit var database : SQLiteDatabase
        fun getInstance(context: Context) : MovieHelper {
            if (INSTANCE == null) {
                synchronized(SQLiteOpenHelper::class.java){
                    if (INSTANCE==null) INSTANCE = MovieHelper(context)
                }
            }
            return INSTANCE as MovieHelper
        }
    }

    init {
        databaseHelper = DatabaseHelper(context)
    }

    @Throws(SQLException::class)
    fun openDB(){
        database = databaseHelper.writableDatabase
    }

    fun closeDB(){
        databaseHelper.close()
        if (database.isOpen) {
            database.close()
        }
    }

    fun readAll() : Cursor {
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