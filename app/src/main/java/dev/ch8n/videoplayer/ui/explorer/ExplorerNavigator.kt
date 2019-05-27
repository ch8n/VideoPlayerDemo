package dev.ch8n.videoplayer.ui.explorer

import dev.ch8n.videoplayer.ui.explorer.members.DirectoryFragment
import dev.ch8n.videoplayer.ui.explorer.model.VideoDir
import kotlinx.android.synthetic.main.activity_explorer.*

class ExplorerNavigator(
    private val activity: ExplorerActivity
) {

    fun explorerNavigateTo(items: ArrayList<VideoDir>) {
        (activity.fragment_holder as DirectoryFragment)
            .openPath(items)
    }

    fun onBackPressed() {
        val explorerFragment = activity.fragment_holder as DirectoryFragment
        if (!explorerFragment.navigateUp()) {
            activity.finish()
        }
    }
}