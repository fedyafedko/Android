package com.example.lab4

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.lab4.ui.theme.Lab4Theme
import android.media.MediaPlayer
import android.net.Uri
import android.widget.Button
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private lateinit var videoView: VideoView
    private var mediaPlayer: MediaPlayer? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        videoView = findViewById(R.id.videoView)
        val videoPath = "android.resource://" + packageName + "/" + R.raw.video
        videoView.setVideoURI(Uri.parse(videoPath))

        findViewById<Button>(R.id.playVideoButton).setOnClickListener {
            videoView.start()
        }

        findViewById<Button>(R.id.pauseVideoButton).setOnClickListener {
            if (videoView.isPlaying) {
                videoView.pause()
            }
        }

        findViewById<Button>(R.id.stopVideoButton).setOnClickListener {
            if (videoView.isPlaying) {
                videoView.stopPlayback()
                videoView.setVideoURI(Uri.parse(videoPath)) // Reset the video to start
            } else {
                videoView.stopPlayback()
                videoView.setVideoURI(Uri.parse(videoPath))
            }
        }

        findViewById<Button>(R.id.playAudioButton).setOnClickListener {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this, R.raw.audio)
            }
            mediaPlayer?.start()
        }

        findViewById<Button>(R.id.pauseAudioButton).setOnClickListener{
            if (mediaPlayer?.isPlaying == true){
                mediaPlayer?.pause()
            }
        }

        findViewById<Button>(R.id.stopAudioButton).setOnClickListener {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        mediaPlayer = null
    }
}