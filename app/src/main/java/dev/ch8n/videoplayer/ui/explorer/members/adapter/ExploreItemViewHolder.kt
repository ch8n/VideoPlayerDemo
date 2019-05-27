package dev.ch8n.videoplayer.ui.explorer.members.adapter

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import dev.ch8n.videoplayer.R
import dev.ch8n.videoplayer.ui.explorer.model.VideoDir
import dev.ch8n.videoplayer.utils.FileUtils
import kotlinx.android.synthetic.main.item_explorer.view.*

class ExploreItemViewHolder(
    view: View
) : RecyclerView.ViewHolder(view) {
    val text_name = view.text_name
    val image_thumb = view.image_thumb
    val text_children = view.text_children
    val container_explored_item = view.container_explored_item

    fun bind(
        videoItem: VideoDir,
        events: MutableLiveData<ExploredItemEvents>
    ) {

        text_name.text = FileUtils.getFileName(videoItem.path)
        container_explored_item.setOnClickListener {
            events.postValue(ExploredItemEvents.OnExploreItemClicked(adapterPosition))
        }

        if (!videoItem.isDirectory) {
            text_children.visibility = View.GONE
            Glide.with(image_thumb)
                .load(videoItem.path)
                .placeholder(R.drawable.ic_image)
                .into(image_thumb)
        } else {
            text_children.visibility = View.VISIBLE
            text_children.text = "${FileUtils.getVideoCount(videoItem)} videos"
            Glide.with(image_thumb)
                .load(R.drawable.ic_folder)
                .placeholder(R.drawable.ic_folder)
                .into(image_thumb)
        }

    }
}