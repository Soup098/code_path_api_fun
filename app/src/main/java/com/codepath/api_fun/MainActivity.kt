package com.codepath.api_fun

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
import okhttp3.Headers
import org.json.JSONException

class MainActivity : AppCompatActivity() {
    private lateinit var rvPhotos: RecyclerView
    private val photos = mutableListOf<MarsPhoto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvPhotos = findViewById(R.id.photosRecyclerView)
        val photoAdapter = PhotoAdapter(photos)
        rvPhotos.adapter = photoAdapter
        rvPhotos.layoutManager = LinearLayoutManager(this)

        val client = AsyncHttpClient()
        val apiKey = "fU8akJc3BSNS3Q938d2Ua4xUdRJvAuJAXfx4ZpZt"
        val url = "https://api.nasa.gov/mars-photos/api/v1/rovers/curiosity/photos?sol=1500&api_key=$apiKey"

        client.get(url, object : JsonHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, json: JSON) {
                Log.d("NASA_API", "Success: $json")

                try {
                    val photoArray = json.jsonObject.getJSONArray("photos")
                    for (i in 0 until photoArray.length()) {
                        val photoObj = photoArray.getJSONObject(i)

                        val imgSrc = photoObj.getString("img_src")
                        val roverName = photoObj.getJSONObject("rover").getString("name")
                        val cameraFullName =
                            photoObj.getJSONObject("camera").getString("full_name")
                        val sol = photoObj.getInt("sol")

                        val photo = MarsPhoto(imgSrc, roverName, cameraFullName, sol)
                        photos.add(photo)
                    }

                    photoAdapter.notifyDataSetChanged()
                } catch (e: JSONException) {
                    Log.e("NASA_API", "JSON Error: ${e.localizedMessage}")
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                response: String?,
                throwable: Throwable?
            ) {
                Log.e("NASA_API", "Failed: $response")
            }
        })
    }
}