package dev.ch8n.videoplayer.ui.explorer.members.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import dev.ch8n.videoplayer.R
import dev.ch8n.videoplayer.ui.explorer.model.VideoDir

class ExploreItemAdapter private constructor(
    diffCallback: DiffUtil.ItemCallback<VideoDir>,
    private val events: MutableLiveData<ExploredItemEvents>
) :
    ListAdapter<VideoDir, ExploreItemViewHolder>(diffCallback) {

    fun getExploreItemAt(position: Int) = getItem(position)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExploreItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_explorer, parent, false)
        return ExploreItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ExploreItemViewHolder, position: Int) = with(holder) {
        if (holder is ExploreItemViewHolder) {
            holder.bind(getItem(position), events)
        }
    }

    fun onEvent(): LiveData<ExploredItemEvents> = events

    companion object {

        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<VideoDir>() {

            override fun areItemsTheSame(oldItem: VideoDir, newItem: VideoDir): Boolean = oldItem.path == newItem.path

            override fun areContentsTheSame(oldItem: VideoDir, newItem: VideoDir): Boolean {
                return (oldItem.path == newItem.path &&
                        oldItem.name == newItem.name &&
                        oldItem.isDirectory == newItem.isDirectory)
            }
        }

        private val events: MutableLiveData<ExploredItemEvents> = MutableLiveData()

        fun newInstance() = ExploreItemAdapter(DIFF_CALLBACK, events)
    }

}

sealed class ExploredItemEvents {
    data class OnExploreItemClicked(val position: Int) : ExploredItemEvents()
}