package org.d3ifcool.submission.presenter.database

import android.net.Uri
import android.provider.BaseColumns

object DatabaseFavorite {

    const val AUTHORITY = "org.d3ifcool.submission"
    const val SCHEME = "content"

    internal class MovieColumns : BaseColumns {
        companion object {
            const val TABLE_MOVIE = "tbl_movie"
            const val _ID = "_id"
            const val TITLE = "title"
            const val DATE = "date"
            const val DESC = "desc"
            const val IMAGE = "image"

            val CONTENT_URI : Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .build()
        }
    }

    internal class ShowColumns : BaseColumns {
        companion object {
            const val TABLE_SHOW = "tbl_show"
            const val _ID = "_id"
            const val TITLE = "title"
            const val DATE = "date"
            const val DESC = "desc"
            const val IMAGE = "image"

            val CONTENT_URI : Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_SHOW)
                .build()
        }
    }
}