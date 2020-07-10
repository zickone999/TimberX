package com.naman14.timberx.youtube

import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.android.youtube.player.*
import com.naman14.timberx.R


class YoutubePlayerActivity: YouTubeBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.youtube_player_activity)

        val youtubePlayerView = findViewById<YouTubePlayerView>(R.id.player)
        val apiKey = "AIzaSyBQbjDpxbWb6fV8Ytz5dmWlyoAS1mm3E9U"

        Log.e("xDebug", "ytpv: $youtubePlayerView")

        val songId = intent.getStringExtra("YTMusic")

        youtubePlayerView.initialize(apiKey, object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(p0: YouTubePlayer.Provider?, p1: YouTubePlayer?, p2: Boolean) {
                p1?.loadVideo(songId)
                Log.e("xDebug", "Youtube player init success")
            }

            override fun onInitializationFailure(p0: YouTubePlayer.Provider?, p1: YouTubeInitializationResult?) {
                Log.e("xDebug", "Youtube player failed to init: $p1")

                val ite = YouTubeStandalonePlayer.createVideoIntent(this@YoutubePlayerActivity, apiKey, songId, 0, true, false)
                if (ite != null) {
                    if (canResolveIntent(ite)) {
                        finish()
                        startActivityForResult(ite, 100001)
                    } else {
                        Log.e("xDebug", "Final Error")
                    }
                }
            }
        })
    }

    private fun canResolveIntent(intent: Intent): Boolean {
        val resolveInfo = packageManager.queryIntentActivities(intent, 0)
        return resolveInfo != null && !resolveInfo.isEmpty()
    }
}