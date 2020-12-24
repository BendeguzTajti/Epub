package com.codecool.epub.view.ui

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import com.codecool.epub.databinding.ActivityVideoBinding

class VideoActivity : AppCompatActivity() {

    companion object {
        const val CHANNEL_NAME_KEY = "CHANNEL_NAME_KEY"
    }

    private lateinit var binding: ActivityVideoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVideoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        webViewInit()
    }

    override fun onPause() {
        super.onPause()
        overridePendingTransition(0, 0)
    }

    override fun onDestroy() {
        binding.video.destroy()
        super.onDestroy()
    }

    @SuppressLint("SetJavaScriptEnabled", "ClickableViewAccessibility")
    private fun webViewInit() {
        val channelName = intent.getStringExtra(CHANNEL_NAME_KEY)
        val url = "https://player.twitch.tv/?channel=$channelName&parent=localhost"

        binding.video.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                binding.videoLoadingContainer.visibility = View.GONE
            }
        }
        binding.video.settings.apply {
            javaScriptEnabled = true
            useWideViewPort = true
            loadWithOverviewMode = true
        }
        binding.video.setOnTouchListener { _, motionEvent -> motionEvent.action == MotionEvent.ACTION_MOVE }
        binding.video.loadUrl(url)
    }
}