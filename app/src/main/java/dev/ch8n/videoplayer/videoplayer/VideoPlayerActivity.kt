package dev.ch8n.videoplayer.videoplayer

import android.media.MediaMetadataRetriever
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import cn.jzvd.Jzvd
import dev.ch8n.videoplayer.utils.FileUtils
import kotlinx.android.synthetic.main.activity_main.*


class VideoPlayerActivity : AppCompatActivity() {



    private var video_path: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(dev.ch8n.videoplayer.R.layout.activity_main)
        video_path = intent?.extras?.getString("video_path") ?: ""
        updatePlayerUrl(video_path)
        attachActions()
    }

    private fun updatePlayerUrl(path: String) {

        val videoName = FileUtils.getFileName(path)


        videoplayer.setUp(
            path,
            videoName,
            Jzvd.SCREEN_WINDOW_NORMAL
        )

        videoplayer.startButton.callOnClick()

        val videoMeta = MediaMetadataRetriever().also { it.setDataSource(path) }
        val videoRotation = videoMeta.extractMetadata(MediaMetadataRetriever.METADATA_KEY_VIDEO_ROTATION)

        when (videoRotation) {
            //0, 90, 180, or 270
            "0", "270" -> videoplayer.startWindowFullscreen()
        }

        Log.e("videoPlayer", videoRotation)

    }

    override fun onBackPressed() {
        finish()
        Jzvd.releaseAllVideos()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }

    private fun attachActions() {

    }
}
