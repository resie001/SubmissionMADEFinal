package org.d3ifcool.submission.presenter.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.d3ifcool.submission.presenter.database.DatabaseFavorite.MovieColumns.Companion.TABLE_MOVIE
import org.d3ifcool.submission.presenter.database.DatabaseFavorite.MovieColumns
import org.d3ifcool.submission.presenter.database.DatabaseFavorite.ShowColumns
import org.d3ifcool.submission.presenter.database.DatabaseFavorite.ShowColumns.Companion.TABLE_SHOW

internal class DatabaseHelper (context : Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {

        private const val DATABASE_NAME = "dbmovie"

        private const val DATABASE_VERSION = 1

        private val SQL_CREATE_TABLE_MOVIE = "CREATE TABLE $TABLE_MOVIE" +
                " (${MovieColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${MovieColumns.TITLE} TEXT NO NULL," +
                " ${MovieColumns.DATE} TEXT NO NULL," +
                " ${MovieColumns.DESC} TEXT NO NULL," +
                " ${MovieColumns.IMAGE} TEXT NO NULL)"

        private val SQL_CREATE_TABLE_SHOW = "CREATE TABLE $TABLE_SHOW" +
                " (${ShowColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                " ${ShowColumns.TITLE} TEXT NO NULL," +
                " ${ShowColumns.DATE} TEXT NO NULL," +
                " ${ShowColumns.DESC} TEXT NO NULL," +
                " ${ShowColumns.IMAGE} TEXT NO NULL)"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE)
        db.execSQL(SQL_CREATE_TABLE_SHOW)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_MOVIE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_SHOW")
        onCreate(db)
    }
}