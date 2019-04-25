package dev.ch8n.videoplayer.explorer.model

data class VideoDir(
    var path: String,
    var name: String,
    var isDirectory: Boolean = false
)

