package org.d3ifcool.submission.view

import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.squareup.picasso.Picasso
import org.d3ifcool.submission.model.Movie
import org.d3ifcool.submission.model.Show
import org.d3ifcool.submission.R
import kotlinx.android.synthetic.main.activity_detail.*
import org.d3ifcool.submission.presenter.database.DatabaseFavorite
import org.d3ifcool.submission.presenter.database.DatabaseFavorite.MovieColumns.Companion.CONTENT_URI
import org.d3ifcool.submission.presenter.database.ShowHelper

class DetailActivity : AppCompatActivity() {

    private var stringImage: String? = null
    private var type: String? = null
    private lateinit var showHelper: ShowHelper
    private var check = 0
    private lateinit var uriWithTitle : Uri

    companion object {
        const val EXTRA_MOVIE = "Movie"
        const val EXTRA_SHOW = "TV Show"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        btn_arrow_back.setOnClickListener {
            super.onBackPressed()
        }

        btn_favorite.setOnClickListener {
            setFavorite()
        }
        type = intent.getStringExtra("Type")
        val favorite = intent.getIntExtra("Favorite",0)

        if (favorite==1) btn_favorite.visibility = View.GONE

        if (type.equals("Movie")) {

            tv_title_toolbar.text = getString(R.string.detail_movie)

            val movie = intent.getParcelableExtra(EXTRA_MOVIE) as Movie

            tv_title.text = movie.title
            tv_date.text = movie.date
            tv_desc.text = movie.desc
            Picasso.get().load("https://image.tmdb.org/t/p/w185/" + movie.image).into(img_poster)
            stringImage = movie.image
            uriWithTitle = Uri.parse(CONTENT_URI.toString()+"/"+movie.title)
            val cursor: Cursor? = contentResolver.query(uriWithTitle, null, null, null, null)
            cursor?.moveToFirst()
            Log.e("TEST",cursor?.getInt(0).toString())
            check = cursor?.getInt(0) ?: 0
            if (check != 0) {
                btn_favorite.setImageResource(R.drawable.baseline_favorite_black_18dp)
            }
            cursor?.close()
        } else {

            showHelper = ShowHelper.getInstance(applicationContext)
            showHelper.openDB()

            tv_title_toolbar.text = getString(R.string.detail_show)

            val show = intent.getParcelableExtra(EXTRA_SHOW) as Show

            tv_title.text = show.title
            tv_date.text = show.date
            tv_desc.text = show.desc
            tv_title_static.text = getString(R.string.title_show_static)
            tv_desc_static.text = getString(R.string.show_desc)
            Picasso.get().load("https://image.tmdb.org/t/p/w185/" + show.image).into(img_poster)
            stringImage = show.image

            val cursor: Cursor = showHelper.selectData(tv_title.text.toString())
            cursor.moveToFirst()
            check = cursor.getInt(0)
            if (check != 0){
                btn_favorite.setImageResource(R.drawable.baseline_favorite_black_18dp)
            }
        }
    }

    fun setFavorite() {

        if (type.equals("Movie")){

            val cursor: Cursor? = contentResolver.query(uriWithTitle, null, null, null, null,null)
            cursor?.moveToFirst()
            Log.e("CHECK",cursor?.count.toString())
            check = cursor?.getInt(0) ?: 0
            if (check == 0) {
                val movie: Movie? = null

                val title = tv_title.text.toString()
                val date = tv_date.text.toString()
                val desc = tv_desc.text.toString()

                movie?.title = title
                movie?.date = date
                movie?.desc = desc
                movie?.image = stringImage

                val values = ContentValues()
                values.put(DatabaseFavorite.MovieColumns.TITLE, title)
                values.put(DatabaseFavorite.MovieColumns.DATE, date)
                values.put(DatabaseFavorite.MovieColumns.DESC, desc)
                values.put(DatabaseFavorite.MovieColumns.IMAGE, stringImage)

                val result = contentResolver.insert(CONTENT_URI, values)
                Log.e("INSERT", result.toString())

                if (!result?.lastPathSegment.equals("0")) {
                    btn_favorite.setImageResource(R.drawable.baseline_favorite_black_18dp)
                    Toast.makeText(this@DetailActivity, getString(R.string.success), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@DetailActivity, getString(R.string.failed), Toast.LENGTH_SHORT).show()
                }
            } else {
                val title = tv_title.text.toString()
                uriWithTitle = Uri.parse(CONTENT_URI.toString()+"/"+title)
                val result = contentResolver.delete(uriWithTitle, null, null)
//                val result = movieHelper.delete(title).toLong()

                if (result > 0) {
                    Toast.makeText(
                        this@DetailActivity,
                        getString(R.string.success_delete),
                        Toast.LENGTH_SHORT
                    ).show()
                    btn_favorite.setImageResource(R.drawable.baseline_favorite_border_black_18dp)
                } else {
                    Toast.makeText(this@DetailActivity, getString(R.string.failed), Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            val cursor: Cursor = showHelper.selectData(tv_title.text.toString())
            cursor.moveToFirst()
            check = cursor.getInt(0)
            if (check == 0) {
                var show: Show? = null

                val title = tv_title.text.toString()
                val date = tv_date.text.toString()
                val desc = tv_desc.text.toString()

                show?.title = title
                show?.date = date
                show?.desc = desc
                show?.image = stringImage

                val values = ContentValues()
                values.put(DatabaseFavorite.ShowColumns.TITLE, title)
                values.put(DatabaseFavorite.ShowColumns.DATE, date)
                values.put(DatabaseFavorite.ShowColumns.DESC, desc)
                values.put(DatabaseFavorite.ShowColumns.IMAGE, stringImage)

                val result = showHelper.insert(values)

                if (result > 0){
                    btn_favorite.setImageResource(R.drawable.baseline_favorite_black_18dp)
                    Toast.makeText(this@DetailActivity, getString(R.string.success), Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@DetailActivity, getString(R.string.failed), Toast.LENGTH_SHORT).show()
                }
            } else {
                val title = tv_title.text.toString()
                val result = showHelper.delete(title).toLong()

                if (result > 0){
                    Toast.makeText(this@DetailActivity, getString(R.string.success_delete), Toast.LENGTH_SHORT).show()
                    btn_favorite.setImageResource(R.drawable.baseline_favorite_border_black_18dp)
                } else {
                    Toast.makeText(this@DetailActivity, getString(R.string.failed), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

}
