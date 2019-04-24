package dev.ch8n.videoplayer.explorer

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
    data class NavigateTo(val parent: String) : ExplorerView()
}

sealed class ExplorerAgent {
    object OnStart : ExplorerAgent()
    object OnStop : ExplorerAgent()
    object InitFileManager : ExplorerAgent()
}