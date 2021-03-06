package dev.ch8n.videoplayer.ui.explorer.members

import android.content.Intent
import dev.ch8n.videoplayer.ui.explorer.model.VideoDir
import dev.ch8n.videoplayer.ui.videoplayer.VideoPlayerActivity


class DirectoryNavigator(
    private val fragment: DirectoryFragment
) : DirectoryContract.Navigator {


    override fun openVideoPlayer(videoItem: VideoDir) {
        fragment.startActivity(
            Intent(
            fragment.requireContext(), VideoPlayerActivity::class.java
        ).also {
            it.putExtra("video_path", videoItem.path)
        })
    }

    override fun canNavigateUp(videoDirItemFromList: VideoDir): Boolean = !videoDirItemFromList.isDirectory


}