package dev.ch8n.videoplayer.utils

import android.content.Context
import android.os.Environment
import android.provider.MediaStore
import dev.ch8n.videoplayer.ui.explorer.model.VideoDir
import java.io.File


object FileUtils {

    val internalStorage = Environment
        .getExternalStorageDirectory()
        .absoluteFile
        .toString()
        .trimEnd('/')

    fun parentDirectory(path: String): String = with(File(path)) {
        if (exists() && !isDirectory) {
            return@with parent
        }
        return@with path
    }

    fun getAllVideoPath(context: Context): ArrayList<String> {
        val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Video.VideoColumns.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        val pathArrList = arrayListOf<String>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                pathArrList.add(cursor.getString(0))
            }
            cursor.close()
        }
        return pathArrList
    }

    fun getVideoDirectories(videoPathList: List<String>): ArrayList<VideoDir> {
        val exploreItem = arrayListOf<VideoDir>()
        videoPathList.forEach {
            val path = it.split(Regex("((?:/[^/\\r\\n]*))\$")).get(0)
            val name = it.split(Regex("((?:/[^/\\r\\n]*))\$")).get(1)
            exploreItem.add(
                VideoDir(
                    path = path,
                    name = name,
                    isDirectory = true
                )
            )
        }

        return exploreItem
    }

    fun removeDuplicates(list: ArrayList<VideoDir>): ArrayList<VideoDir> {

        val set = LinkedHashSet<String>()
        list.forEach { set.add(it.path) }
        list.clear()
        set.forEach {
            list.add(
                VideoDir(
                    path = it,
                    name = it.split(Regex("((?:/[^/\\r\\n]*))\$")).get(1).trim('/'),
                    isDirectory = true
                )
            )
        }
        return list
    }

    fun getVideoFiles(videoDIR: VideoDir): List<VideoDir> {
        val videoItems = arrayListOf<VideoDir>()
        val videoDir = File(videoDIR.path)
        videoDir.listFiles().filter {
            !it.isDirectory &&
                    it.absolutePath.toString()
                        .contains(Regex("(.mp4|.3gp|.webm|.mkv|.avi)\$"))
        }
            .forEach {
            videoItems.add(
                VideoDir(
                    path = it.absolutePath.toString(),
                    name = it.name,
                    isDirectory = false
                )
            )
        }
        return videoItems
    }


    fun getVideoCount(videoDir: VideoDir): Int {
        val file: File = File(videoDir.path)
        val videoList = file.listFiles().filter {
            !it.isDirectory && it.absolutePath.toString().contains(Regex("(.mp4|.3gp|.webm|.mkv|.avi)\$"))
        }
        return videoList.size
    }

    fun getFileName(path: String): String {
        return File(path).name
    }
}