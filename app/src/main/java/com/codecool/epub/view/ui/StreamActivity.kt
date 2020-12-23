package com.codecool.epub.view.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codecool.epub.R

class StreamActivity : AppCompatActivity() {

    companion object {
        const val CHANNEL_NAME_KEY = "CHANNEL_NAME_KEY"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stream)
        val channelName = intent.getStringExtra(CHANNEL_NAME_KEY)
//        binding.liveStream.webViewClient = WebViewClient()
//        binding.liveStream.settings.apply {
//            javaScriptEnabled = true
//            useWideViewPort = true
//            loadWithOverviewMode = true
//        }
//        binding.liveStream.loadUrl("https://player.twitch.tv/?channel=${channelName}&parent=localhost&muted=false")
    }
}