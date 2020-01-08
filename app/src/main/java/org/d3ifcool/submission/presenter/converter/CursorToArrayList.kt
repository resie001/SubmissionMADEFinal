package org.d3ifcool.submission.presenter.converter

import android.database.Cursor
import org.d3ifcool.submission.model.Movie
import org.d3ifcool.submission.model.Show
import org.d3ifcool.submission.presenter.database.DatabaseFavorite

object CursorToArrayList {

    fun mappingForMovie(noteCursor: Cursor) : ArrayList<Movie> {
        val noteList = ArrayList<Movie>()
        noteCursor.moveToFirst()
        if (noteCursor.count == 0) return noteList
        else {
            do {
                val title =
                    noteCursor.getString(noteCursor.getColumnIndex(DatabaseFavorite.MovieColumns.TITLE))
                val date =
                    noteCursor.getString(noteCursor.getColumnIndex(DatabaseFavorite.MovieColumns.DATE))
                val desc =
                    noteCursor.getString(noteCursor.getColumnIndex(DatabaseFavorite.MovieColumns.DESC))
                val image =
                    noteCursor.getString(noteCursor.getColumnIndex(DatabaseFavorite.MovieColumns.IMAGE))
                noteList.add(Movie(title, date, desc, image))
            } while (noteCursor.moveToNext())

            return noteList
        }
    }

    fun mappingForShow(noteCursor: Cursor) : ArrayList<Show> {
        val noteList = ArrayList<Show>()
        noteCursor.moveToFirst()
        if (noteCursor.count == 0) return noteList
        else {
            do {
                val title =
                    noteCursor.getString(noteCursor.getColumnIndex(DatabaseFavorite.ShowColumns.TITLE))
                val date =
                    noteCursor.getString(noteCursor.getColumnIndex(DatabaseFavorite.ShowColumns.DATE))
                val desc =
                    noteCursor.getString(noteCursor.getColumnIndex(DatabaseFavorite.ShowColumns.DESC))
                val image =
                    noteCursor.getString(noteCursor.getColumnIndex(DatabaseFavorite.ShowColumns.IMAGE))
                noteList.add(Show(title, date, desc, image))
            } while (noteCursor.moveToNext())

            return noteList
        }
    }


}