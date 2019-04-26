package dev.ch8n.videoplayer.explorer.fragment

import android.content.Intent
import dev.ch8n.videoplayer.videoplayer.VideoPlayerActivity
import dev.ch8n.videoplayer.explorer.model.VideoDir

class DirectoryNavigator(
    private val fragment: DirectoryFragment
) : DirectoryContract.Navigator {


    override fun openVideoPlayer(videoItem: VideoDir) {
        fragment.startActivity(Intent(
            fragment.requireContext(), VideoPlayerActivity::class.java
        ).also {
            it.putExtra("video_path", videoItem.path)
        })
    }

    override fun canNavigateUp(videoDirItemFromList: VideoDir): Boolean = !videoDirItemFromList.isDirectory


}