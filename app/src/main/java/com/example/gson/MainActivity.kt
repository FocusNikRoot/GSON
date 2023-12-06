package com.example.gson

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import timber.log.Timber.Forest.plant
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private val URL = "https://api.flickr.com/services/rest/?method=flickr.photos.search&api_key=8625ec317d2c6b641758c9456b01cc57&tags=Kemerovo&format=json&nojsoncallback=1"
    private val okHttpClient : OkHttpClient = OkHttpClient()
    private var links : Array<String> = arrayOf()
    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        plant(Timber.DebugTree())
        setContentView(R.layout.activity_main)
        recyclerView = findViewById(R.id.rView)

        getGSONFromserver()
    }
    private fun getGSONFromserver(){
        val request : Request = Request.Builder().url(URL).build()
        okHttpClient.newCall(request).enqueue(object : Callback{
            override fun onFailure(call: Call, e: IOException) {
                TODO("Not yet implemented")
            }

            override fun onResponse(call: Call, response: Response) {
                val jsonFromServer = response.body?.string()
                parseJSON(jsonFromServer)
            }

        })
    }

    private fun parseJSON(jsonFromServer: String?) {
    val result : Wrapper = Gson().fromJson(jsonFromServer, Wrapper::class.java)
        for (i in 0 .. result.photos.photo.size-1){
            links += ("https://farm"+result.photos.photo[i].farm+".staticflickr.com/"+ result.photos.photo[i].server+"/"+result.photos.photo[i].id+"_"+result.photos.photo[i].secret+"_z.jpg")
             Timber.d("Photo_link", links[i])
        }
        runOnUiThread{
           recyclerView.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
           recyclerView.adapter = MyRecyclerAdapter(this, links)
        }

    }
}

data class Wrapper(
    val photos : Page,
    val stat : String
)

data class Page(
    val page : Number,
    val pages : Number,
    val perpage : Number,
    val total : Number,
    val photo : Array<Photo>
)

data class Photo(
    val id : Number,
    val owner : String,
    val secret : Number,
    val server : Number,
    val farm : Number,
    val title : String,
    val isPublic : Boolean,
    val isFriend: Boolean,
    val isFamily : Boolean
)