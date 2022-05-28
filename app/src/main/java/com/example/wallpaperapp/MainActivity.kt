package com.example.wallpaperapp

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest

class MainActivity : AppCompatActivity(), WallpaperItemClicked {
    private lateinit var mAdapter: WallpaperAdapter
    private var recyclerView:RecyclerView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recyclerView=findViewById(R.id.recyclerView)
        recyclerView?.layoutManager= LinearLayoutManager(this)
        fetchData()
        mAdapter= WallpaperAdapter(this)
        recyclerView?.adapter=mAdapter
    }
    private fun fetchData(){

        val url = "https://pixabay.com/api/?key=27701269-dc791b614da9befe5a600f47c&q=yellow+flowers&image_type=photo"
        val getRequest: JsonObjectRequest = object : JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {
                val wallpaperJsonArray = it.getJSONArray("hits")
                val wallpaperArray = ArrayList<wallpaper>()
                for(i in 0 until  wallpaperJsonArray.length()){
                    val wallpaperJsonObject = wallpaperJsonArray.getJSONObject(i)
                    val mWallpaper =wallpaper(
                        wallpaperJsonObject.getString("pageURL"),
                        wallpaperJsonObject.getString("largeImageURL"),
                        wallpaperJsonObject.getInt("views"),
                        wallpaperJsonObject.getInt("downloads"),
                        wallpaperJsonObject.getInt("likes"),
                    )
                    wallpaperArray.add(mWallpaper)
                }
                mAdapter.updateWallpapers(wallpaperArray)
            },
            Response.ErrorListener { error ->
                Toast.makeText(this, "error loading", Toast.LENGTH_SHORT).show()
            }
        ) {
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["User-Agent"] = "Mozilla/5.0"
                return params
            }
        }
        MySingleton.getInstance(this).addToRequestQueue(getRequest)
    }

    override fun onItemClicked(item: wallpaper) {
        val builder = CustomTabsIntent.Builder()
        val customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.pageUrl))
    }

}