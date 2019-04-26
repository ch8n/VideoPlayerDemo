package dev.ch8n.videoplayer.videoplayer

import android.os.Bundle
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
    }

    override fun onBackPressed() {
        if (Jzvd.backPress()) {
            return
        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
        Jzvd.releaseAllVideos()
    }

    private fun attachActions() {

    }
}
