package dev.ch8n.videoplayer

import android.Manifest
import android.content.Context
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import cn.jzvd.Jzvd
import dev.ch8n.videoplayer.utils.AppConst
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.EasyPermissions


class VideoPlayerActivity : AppCompatActivity() {


    private var video_path: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        video_path = intent?.extras?.getString("video_path") ?: ""
        updatePlayerUrl(video_path)

        attachActions()
        populateOnlinelist()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        // Forward results to EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private val perms =
        arrayOf<String>(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

    fun populateOfflineList() {
        if (EasyPermissions.hasPermissions(this, *perms)) {
            val list = getAllVideoPath(this)
            list_offline.run {
                adapter = ArrayAdapter<String>(
                    this@VideoPlayerActivity,
                    android.R.layout.simple_expandable_list_item_1,
                    list
                )
                setOnItemClickListener { parent, view, position, id ->
                    updatePlayerUrl(list.get(position))
                }
            }
        } else {
            EasyPermissions.requestPermissions(
                this, "Need read permission",
                PERMISSION_CAMERA_CODE, *perms
            )
        }
    }

    private fun getAllVideoPath(context: Context): ArrayList<String> {
        val uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
        val projection = arrayOf(MediaStore.Video.VideoColumns.DATA)
        val cursor = context.getContentResolver().query(uri, projection, null, null, null)
        val pathArrList = arrayListOf<String>()
        if (cursor != null) {
            while (cursor.moveToNext()) {
                pathArrList.add(cursor.getString(0))
            }
            cursor.close()
        }

        return pathArrList
    }


    private fun populateOnlinelist() {
        list_online.run {
            adapter = ArrayAdapter<String>(
                this@VideoPlayerActivity,
                android.R.layout.simple_expandable_list_item_1,
                AppConst.PUBLIC_VIDEO_URL
            )
            setOnItemClickListener { parent, view, position, id ->
                updatePlayerUrl(AppConst.PUBLIC_VIDEO_URL.get(position))
            }
        }
    }

    private fun updatePlayerUrl(url: String) {
        videoplayer.setUp(
            url,
            "Sample",
            Jzvd.SCREEN_NORMAL
        )
    }


    private fun attachActions() {
        container_online.visibility = View.VISIBLE
        container_offline.visibility = View.GONE

        btn_online_list.setOnClickListener {
            container_online.visibility = View.VISIBLE
            container_offline.visibility = View.GONE
        }

        btn_offline_list.setOnClickListener {

            container_online.visibility = View.GONE
            container_offline.visibility = View.VISIBLE

            populateOfflineList()
        }
    }

    companion object {
        private const val PERMISSION_CAMERA_CODE = 1000
    }
}
