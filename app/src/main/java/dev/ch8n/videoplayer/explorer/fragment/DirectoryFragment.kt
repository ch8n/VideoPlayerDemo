package dev.ch8n.videoplayer.explorer.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import dev.ch8n.videoplayer.R
import dev.ch8n.videoplayer.explorer.fragment.adapter.ExploreItemActionListener
import dev.ch8n.videoplayer.explorer.fragment.adapter.ExploreItemAdapter
import dev.ch8n.videoplayer.explorer.model.VideoDir
import kotlinx.android.synthetic.main.fragment_explorer.*

class DirectoryFragment : Fragment(), DirectoryContract.View {


    private var listener: OnExplorerFragmentInteraction? = null
    private lateinit var attachedContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnExplorerFragmentInteraction) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnExplorerFragmentInteraction")
        }
        attachedContext = requireContext()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_explorer, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    lateinit var exploreItemAdapter: ExploreItemAdapter
    lateinit var navigator: DirectoryNavigator
    lateinit var controller: DirectoryController
    private fun setup() {
        navigator = DirectoryNavigator(this)
        controller = DirectoryController(this)
        controller.onStart()
    }

    override fun attachActions() {
        list_explored_items.run {
            layoutManager = GridLayoutManager(attachedContext, 3)
            adapter = ExploreItemAdapter.newInstance(
                object : ExploreItemActionListener {
                    override fun onClickPosition(position: Int) {
                        val item = exploreItemAdapter.getExploreItemAt(position)
                        if (item.isDirectory) {
                            controller.showAllVideofiles(item)
                        } else {
                            navigator.openVideoPlayer(item)
                        }
                    }
                }
            ).also {
                exploreItemAdapter = it
            }
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    override fun showDirList(list: List<VideoDir>, columnCount: Int) {
        Log.e("EXPLORE_FRAG", list.toString())
        //list_explored_items.layoutManager = GridLayoutManager(attachedContext, columnCount)
        exploreItemAdapter.submitList(list)
    }

    fun openPath(displayItems: ArrayList<VideoDir>) {
        if (!isAdded) {
            return
        }
        controller.distincList(displayItems)
    }

    fun navigateUp(): Boolean = navigator.canNavigateUp(
        exploreItemAdapter.getExploreItemAt(0)
    ).also { canNavigateup ->
        if (canNavigateup) {
            controller.showAllVideoDirectories()
        }
    }

    interface OnExplorerFragmentInteraction {
        fun onFragmentInteraction(uri: Uri)
    }

}
