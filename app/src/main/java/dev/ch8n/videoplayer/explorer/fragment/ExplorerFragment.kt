package dev.ch8n.videoplayer.explorer.fragment

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ch8n.videoplayer.MainActivity
import dev.ch8n.videoplayer.R
import dev.ch8n.videoplayer.explorer.fragment.adapter.ExploreItemActionListener
import dev.ch8n.videoplayer.explorer.fragment.adapter.ExploreItemAdapter
import dev.ch8n.videoplayer.explorer.model.VideoDir
import dev.ch8n.videoplayer.utils.FileUtils
import kotlinx.android.synthetic.main.fragment_explorer.*

class ExplorerFragment : Fragment() {

    private var listener: OnExplorerFragmentInteraction? = null
    private lateinit var attachedContext: Context

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnExplorerFragmentInteraction) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
        attachedContext = requireContext()
    }

    private var currentPath: ArrayList<VideoDir> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_explorer, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setup()
    }

    lateinit var exploreItemAdapter: ExploreItemAdapter
    private fun setup() {
        list_explored_items.run {
            layoutManager = LinearLayoutManager(attachedContext)
            adapter = ExploreItemAdapter.newInstance(
                object : ExploreItemActionListener {
                    override fun onClickPosition(position: Int) {
                        val item = exploreItemAdapter.getExploreItemAt(position)
                        Toast.makeText(attachedContext, item.toString(), Toast.LENGTH_LONG).show()

                        if (item.isDirectory) {
                            val videoItems = FileUtils.getVideoFiles(item)
                            exploreItemAdapter.submitList(videoItems)
                        } else {
                            startActivity(Intent(
                                attachedContext, MainActivity::class.java
                            ).also {
                                it.putExtra("video_path", item.path)
                            })
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

    interface OnExplorerFragmentInteraction {
        fun onFragmentInteraction(uri: Uri)
    }


    fun openPath(displayItems: ArrayList<VideoDir>) {
        if (!isAdded) {
            return
        }
        val newDisplayItems = FileUtils.removeDuplicates(displayItems)
        currentPath = newDisplayItems
        Log.e("EXPLORE_FRAG", newDisplayItems.toString())
        exploreItemAdapter.submitList(newDisplayItems)
    }

    fun navigateUp(): Boolean {

        return true
    }


}
