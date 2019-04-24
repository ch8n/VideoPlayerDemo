package dev.ch8n.videoplayer.explorer.fragment.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import dev.ch8n.videoplayer.R
import dev.ch8n.videoplayer.explorer.model.ExploreItem
import kotlinx.android.synthetic.main.item_explorer.view.*

class ExploreItemAdapter private constructor(
    diffCallback: DiffUtil.ItemCallback<ExploreItem>,
    private val listener: ExploreItemActionListener
) :
    ListAdapter<ExploreItem, ExploreItemAdapter.ExploreItemViewHolder>(diffCallback) {


    companion object {
        override fun equals(other: Any?): Boolean {
            return super.equals(other)
        }

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ExploreItem>() {
            override fun areItemsTheSame(oldItem: ExploreItem, newItem: ExploreItem): Boolean = oldItem == newItem

            override fun areContentsTheSame(oldItem: ExploreItem, newItem: ExploreItem): Boolean {
                return (oldItem.name == newItem.name &&
                        oldItem.path == newItem.path &&
                        oldItem.isDirectory == newItem.isDirectory &&
                        oldItem.childrens == newItem.childrens)
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
            text_name.text = exploreItem.name
            text_path.text = exploreItem.path
            text_type.text = if (exploreItem.isDirectory) "DIR" else "FILE"
            text_children.text = exploreItem.childrens.toString()
            container_explored_item.setOnClickListener {
                listener.onClickPosition(position)
            }
        }
    }


    class ExploreItemViewHolder(
        view: View
    ) : RecyclerView.ViewHolder(view) {
        val text_name = view.text_name
        val text_path = view.text_path
        val text_type = view.text_type
        val text_children = view.text_children
        val container_explored_item = view.container_explored_item
    }
}

interface ExploreItemActionListener {
    fun onClickPosition(position: Int)
}