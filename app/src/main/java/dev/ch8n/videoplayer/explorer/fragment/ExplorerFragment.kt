package dev.ch8n.videoplayer.explorer.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dev.ch8n.videoplayer.R
import dev.ch8n.videoplayer.explorer.fragment.adapter.ExploreItemActionListener
import dev.ch8n.videoplayer.explorer.fragment.adapter.ExploreItemAdapter
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

    private var currentPath: String = FileUtils.internalStorage

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
                        if (item.isDirectory) {
                            openPath(item.path)
                        }
                        Toast.makeText(attachedContext, item.toString(), Toast.LENGTH_LONG).show()
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


    fun openPath(path: String) {
        if (!isAdded) {
            return
        }

        var newPath = path.trimEnd('/')
        if (newPath.isEmpty()) {
            newPath = FileUtils.internalStorage
        }

        currentPath = newPath
        val dirList = FileUtils.getFiles(newPath)
        Log.e("EXPLORE_FRAG", path)
        Log.e("EXPLORE_FRAG", dirList.size.toString())
        Log.e("EXPLORE_FRAG", dirList.toString())
        exploreItemAdapter.submitList(dirList)
    }

    fun navigateUp(): Boolean {

        if (currentPath == FileUtils.internalStorage) {
            return false
        }

        val previousPath = currentPath.split(Regex("((?:/[^/\\r\\n]*))\$")).get(0)
        Log.e("EXPLORE_FRAG", previousPath)
        currentPath = previousPath
        val dirList = FileUtils.getFiles(previousPath)
        exploreItemAdapter.submitList(dirList)
        return true
    }


}
