package com.example.guide2

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.guide.R


class Video : AppCompatActivity() {

    private lateinit var webView: WebView

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video) // Make sure this is the correct layout file

        webView = findViewById(R.id.webView) // Reference WebView

        // Get the video URL passed from the previous activity
        val videoUrl = intent.getStringExtra("VIDEO_URL")

        if (videoUrl != null) {
            // Convert YouTube link to embed link
            val embedUrl = videoUrl.replace("watch?v=", "embed/")

            // Set up WebView settings
            val webSettings: WebSettings = webView.settings
            webSettings.javaScriptEnabled = true

            // Load the embed URL in the WebView
            webView.loadUrl(embedUrl)
        } else {
            // Handle case where video URL is null
            Toast.makeText(this, "Video URL is not available.", Toast.LENGTH_SHORT).show()
        }
    }
}
