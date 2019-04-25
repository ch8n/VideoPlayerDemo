package dev.ch8n.videoplayer.explorer

import dev.ch8n.videoplayer.explorer.fragment.ExplorerFragment
import dev.ch8n.videoplayer.explorer.model.VideoDir
import kotlinx.android.synthetic.main.activity_explorer.*

class ExplorerNavigator(
    private val activity: ExplorerActivity
) {

    fun explorerNavigateTo(items: ArrayList<VideoDir>) {
        (activity.fragment_holder as ExplorerFragment)
            .openPath(items)
    }

    fun onBackPressed() {
        val explorerFragment = activity.fragment_holder as ExplorerFragment
        if (!explorerFragment.navigateUp()) {
            activity.finish()
        }
    }
}