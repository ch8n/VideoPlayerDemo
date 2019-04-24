package dev.ch8n.videoplayer.explorer

import dev.ch8n.videoplayer.explorer.fragment.ExplorerFragment
import kotlinx.android.synthetic.main.activity_explorer.*

class ExplorerNavigator(
    private val activity: ExplorerActivity
) {

    fun explorerNavigateTo(path: String) {
        (activity.fragment_holder as ExplorerFragment)
            .openPath(path)
    }

    fun onBackPressed() {
        val explorerFragment = activity.fragment_holder as ExplorerFragment
        if (!explorerFragment.navigateUp()) {
            activity.finish()
        }
    }
}