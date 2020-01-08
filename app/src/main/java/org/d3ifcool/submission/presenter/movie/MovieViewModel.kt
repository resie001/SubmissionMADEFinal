package org.d3ifcool.submission.presenter.movie

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.d3ifcool.submission.model.Movie
import org.json.JSONObject
import java.lang.Exception

class MovieViewModel : ViewModel(){

    companion object{
        private const val API_KEY = "b63e4d8ae86a370699745b910ff01fad"
    }

    private val listMovie = MutableLiveData<ArrayList<Movie>>()

    internal fun setMovie(){
        val client = AsyncHttpClient()
        val listItem = ArrayList<Movie>()
        val url = "https://api.themoviedb.org/3/discover/movie?api_key=$API_KEY&language=en-US"

        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")

                    for (i in 0 until list.length()){
                        val movie = list.getJSONObject(i)
                        val movieItems = Movie()
                        movieItems.title = movie.getString("title")
                        movieItems.date = movie.getString("release_date")
                        movieItems.desc = movie.getString("overview")
                        movieItems.image = movie.getString("poster_path")
                        listItem.add(movieItems)
                    }
                    listMovie.postValue(listItem)
                } catch (e : Exception){
                    Log.d("Exception", e.message.toString())
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }

        })
    }

    internal fun getMovie() : LiveData<ArrayList<Movie>>{
        return listMovie
    }
}