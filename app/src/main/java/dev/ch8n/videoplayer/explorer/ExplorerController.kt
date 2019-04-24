package dev.ch8n.videoplayer.explorer

import dev.ch8n.videoplayer.utils.AppConst
import dev.ch8n.videoplayer.utils.FileUtils

class ExplorerController(private val view: ExplorerContract.View) : ExplorerContract.Controller {

    override fun event(event: ExplorerAgent) = when (event) {
        ExplorerAgent.OnStart -> onStart()
        ExplorerAgent.OnStop -> TODO()
        ExplorerAgent.InitFileManager -> initFileManager()
    }

    private fun onStart() {
        val perms = AppConst.APP_PERMISSIONS
        view.act(ExplorerView.CheckPermission(perms))
    }

    private fun initFileManager() {
        val homePath = FileUtils.internalStorage
        val parent = FileUtils.parentDirectory(homePath)
        view.act(ExplorerView.NavigateTo(parent))
    }

}