package com.codepath.api_fun

import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Button
import android.widget.ImageView
import org.json.JSONObject
import com.bumptech.glide.Glide
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.codepath.asynchttpclient.AsyncHttpClient
import com.codepath.asynchttpclient.RequestParams
import com.codepath.asynchttpclient.callback.TextHttpResponseHandler
import okhttp3.Headers

class MainActivity : AppCompatActivity() {

    lateinit var imageView: ImageView
    lateinit var titleText: TextView
    lateinit var explanationText: TextView
    lateinit var refreshButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        imageView = findViewById(R.id.imageView)
        titleText = findViewById(R.id.titleText)
        explanationText = findViewById(R.id.explanationText)
        refreshButton = findViewById(R.id.refreshButton)

        fetchAPOD()

        refreshButton.setOnClickListener(){
            fetchAPOD()
        }
    }

    private fun fetchAPOD(){
        val client = AsyncHttpClient()
        val params = RequestParams()
        params["api_key"] = "QtL0L78y2DWwjkVrm1rd5jmtfyf7KRJJkqSedUoI"

        client["https://api.nasa.gov/planetary/apod", params, object :
            TextHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Headers, response: String) {
                Log.d("NASA_API", "Success response: $response")
                try {
                    val json = JSONObject(response)
                    val imageUrl = json.getString("url")
                    val title = json.getString("title")
                    val explanation = json.getString("explanation")

                    titleText.text = title
                    explanationText.text = explanation
                    Glide.with(this@MainActivity).load(imageUrl).into(imageView)
                } catch (e:Exception){
                    Log.e("NASA_API", "JSON Parsing error: ${e.message}")
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Headers?,
                errorResponse: String,
                t: Throwable?
            ) {
                Log.e("NASA_API", "Failure: $statusCode $errorResponse")
            }
        }]
    }
}