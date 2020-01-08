package org.d3ifcool.submission.presenter.show

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import org.d3ifcool.submission.model.Show
import org.json.JSONObject
import java.lang.Exception

class ShowViewModel : ViewModel() {

    companion object {
        private const val API_KEY = "b63e4d8ae86a370699745b910ff01fad"
    }

    val listShow = MutableLiveData<ArrayList<Show>>()

    internal fun setShow(){
        val client = AsyncHttpClient()
        val listItem = ArrayList<Show>()
        val url = "https://api.themoviedb.org/3/discover/tv?api_key=$API_KEY&language=en-US"

        client.get(url, object : AsyncHttpResponseHandler(){
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                try {
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("results")

                    for (i in 0 until list.length()){
                        val show = list.getJSONObject(i)
                        val showItems = Show()
                        showItems.title = show.getString("name")
                        showItems.date = show.getString("first_air_date")
                        showItems.desc = show.getString("overview")
                        showItems.image = show.getString("poster_path")
                        listItem.add(showItems)
                    }
                    listShow.postValue(listItem)
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
                Log.d("OnFailure",error.message.toString())
            }

        })
    }

    internal fun getShow() : LiveData<ArrayList<Show>>{
        return listShow
    }

}