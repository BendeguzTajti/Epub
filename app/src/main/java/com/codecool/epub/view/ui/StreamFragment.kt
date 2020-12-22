package com.codecool.epub.view.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.codecool.epub.databinding.FragmentStreamBinding

class StreamFragment : Fragment() {

    private val args: StreamFragmentArgs by navArgs()
    private var _binding: FragmentStreamBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStreamBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.liveStream.webViewClient = WebViewClient()
        binding.liveStream.settings.apply {
            javaScriptEnabled = true
            useWideViewPort = true
            loadWithOverviewMode = true
        }
        binding.liveStream.loadUrl("https://player.twitch.tv/?channel=${args.channelName}&parent=localhost")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}