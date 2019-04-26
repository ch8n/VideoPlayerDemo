package dev.ch8n.videoplayer.explorer.fragment

import dev.ch8n.videoplayer.explorer.model.VideoDir
import dev.ch8n.videoplayer.utils.FileUtils

class DirectoryController(
    private val view: DirectoryContract.View
) : DirectoryContract.Controller {

    override fun onStart() {
        view.attachActions()
    }


    private var currentPath: ArrayList<VideoDir> = arrayListOf()
    override fun distincList(displayItemList: ArrayList<VideoDir>) {
        view.showDirList(FileUtils.removeDuplicates(displayItemList)
            .also {
                currentPath = it
            }, 3
        )
    }

    override fun showAllVideoDirectories() {
        view.showDirList(currentPath, 3)
    }

    override fun showAllVideofiles(item: VideoDir) {
        val videoItems = FileUtils.getVideoFiles(item)
        view.showDirList(videoItems, 2)
    }


}