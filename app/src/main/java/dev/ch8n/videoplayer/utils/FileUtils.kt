package dev.ch8n.videoplayer.utils

import android.os.Environment
import dev.ch8n.videoplayer.explorer.model.ExploreItem
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


    fun getFiles(path: String): ArrayList<ExploreItem> {
        val exploreItems = arrayListOf<ExploreItem>()
        val files: Array<File> = File(path).listFiles()
        files.forEach { file ->
            when {
                file.exists()
                        && !file.isDirectory
//                        && file.absoluteFile.toString()
//                    .contains(".mp4|.3gp|.webm|.mkv|.avi")
                -> {
                    exploreItems.add(
                        ExploreItem(
                            file.absolutePath,
                            file.name,
                            false, 0
                        )
                    )
                }
                file.exists() && file.isDirectory -> {
                    exploreItems.add(
                        ExploreItem(
                            file.absolutePath.toString()
                                .trimEnd('/'),
                            file.name,
                            true,
                            file.listFiles().size
                        )
                    )
                }

            }

        }

        return exploreItems
    }
}