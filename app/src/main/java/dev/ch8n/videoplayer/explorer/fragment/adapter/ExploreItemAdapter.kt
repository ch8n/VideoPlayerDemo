package dev.ch8n.videoplayer.explorer.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.ch8n.videoplayer.R
import dev.ch8n.videoplayer.explorer.model.VideoDir
import kotlinx.android.synthetic.main.item_explorer.view.*

class ExploreItemAdapter private constructor(
    diffCallback: DiffUtil.ItemCallback<VideoDir>,
    private val listener: ExploreItemActionListener
) :
    ListAdapter<VideoDir, ExploreItemAdapter.ExploreItemViewHolder>(diffCallback) {

    companion object {
        override fun equals(other: Any?): Boolean {
            return super.equals(other)
        }

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<VideoDir>() {
            override fun areItemsTheSame(oldItem: VideoDir, newItem: VideoDir): Boolean = oldItem == newItem

            override fun areContentsTheSame(oldItem: VideoDir, newItem: VideoDir): Boolean {
                return (oldItem.path == newItem.path)
            }
        }

        fun newInstance(listener: ExploreItemActionListener) = ExploreItemAdapter(DIFF_CALLBACK, listener)
    }

    fun getExploreItemAt(position: Int) = getItem(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_explorer, parent, false)
        return ExploreItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExploreItemViewHolder, position: Int) {
        with(holder) {
            val exploreItem = getItem(position)
            text_path.text = exploreItem.path
            text_name.text = exploreItem.name
            text_type.text = if (exploreItem.isDirectory) "DIR" else "FILE"
            container_explored_item.setOnClickListener {
                listener.onClickPosition(position)
            }
        }
    }


    class ExploreItemViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        val text_path = view.text_path
        val text_name = view.text_name
        val text_type = view.text_type
        val container_explored_item = view.container_explored_item
    }
}

interface ExploreItemActionListener {
    fun onClickPosition(position: Int)
}