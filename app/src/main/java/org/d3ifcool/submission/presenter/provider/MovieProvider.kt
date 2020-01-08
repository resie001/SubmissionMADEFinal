package org.d3ifcool.submission.presenter.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import org.d3ifcool.submission.presenter.database.DatabaseFavorite.AUTHORITY
import org.d3ifcool.submission.presenter.database.DatabaseFavorite.MovieColumns.Companion.CONTENT_URI
import org.d3ifcool.submission.presenter.database.DatabaseFavorite.MovieColumns.Companion.TABLE_MOVIE
import org.d3ifcool.submission.presenter.database.MovieHelper

class MovieProvider : ContentProvider() {

    companion object {
        private const val CATALOG = 1
        private const val CATALOG_ID = 2
        private val uriMatcher: UriMatcher= UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var movieHelper: MovieHelper

        init {
            uriMatcher.addURI(AUTHORITY, TABLE_MOVIE, CATALOG)

            uriMatcher.addURI(AUTHORITY, "$TABLE_MOVIE/#", CATALOG_ID)
        }
    }

    override fun onCreate(): Boolean {
        movieHelper = MovieHelper.getInstance(context as Context)
        movieHelper.openDB()
        return true
    }

    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortCONTENTCONTENTOrder: String?
    ): Cursor {
        val cursor: Cursor
        when(uriMatcher.match(uri)){
            CATALOG -> cursor = movieHelper.readAll()
            else -> cursor = movieHelper.selectData(uri.lastPathSegment.toString())
        }
        return cursor
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri {
        val added = movieHelper.insert(values)

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted : Int = when(CATALOG_ID) {
            uriMatcher.match(uri) -> movieHelper.delete(uri.lastPathSegment.toString())
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return deleted
    }

}
