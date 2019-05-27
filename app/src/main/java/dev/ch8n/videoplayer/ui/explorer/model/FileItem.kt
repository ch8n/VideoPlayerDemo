package dev.ch8n.videoplayer.ui.explorer.model

data class VideoDir(
    var path: String,
    var name: String,
    var isDirectory: Boolean = false
)

