package dev.ch8n.videoplayer.explorer.model

data class ExploreItem(
    var path: String,
    var name: String,
    var isDirectory:Boolean,
    var childrens: Int
)