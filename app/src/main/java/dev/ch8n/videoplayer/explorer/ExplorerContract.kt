package dev.ch8n.videoplayer.explorer

import dev.ch8n.videoplayer.explorer.model.VideoDir

interface ExplorerContract {

    interface View {
        fun act(action: ExplorerView)
    }

    interface Controller {
        fun event(event: ExplorerAgent)
    }
}

const val PERMISSION_READ_WRITE_STORAGE = 1000

sealed class ExplorerView {
    data class CheckPermission(val perms: Array<String>) : ExplorerView()
    data class NavigateTo(val videoDirs:ArrayList<VideoDir>) : ExplorerView()
}

sealed class ExplorerAgent {
    object OnStart : ExplorerAgent()
    object OnStop : ExplorerAgent()
    data class InitFileManager(val videoPathList: List<String>) : ExplorerAgent()
}