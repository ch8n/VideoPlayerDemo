package dev.ch8n.videoplayer.ui.explorer.members

import dev.ch8n.videoplayer.ui.explorer.model.VideoDir

interface DirectoryContract {
    interface View {
        fun showDirList(list: List<VideoDir>, columnCount: Int)
        fun attachActions()
    }

    interface Controller {
        fun onStart()
        fun distincList(displayItemList: ArrayList<VideoDir>)
        fun showAllVideoDirectories()
        fun showAllVideofiles(videoItem: VideoDir)
    }

    interface Navigator {
        fun canNavigateUp(videoDirItemFromList: VideoDir): Boolean
        fun openVideoPlayer(videoItem: VideoDir)

    }
}