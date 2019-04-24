package dev.ch8n.videoplayer.explorer

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.ch8n.videoplayer.R
import dev.ch8n.videoplayer.explorer.fragment.ExplorerFragment
import pub.devrel.easypermissions.EasyPermissions

class ExplorerActivity : AppCompatActivity(), ExplorerContract.View, ExplorerFragment.OnExplorerFragmentInteraction {

    override fun act(action: ExplorerView) = when (action) {
        is ExplorerView.CheckPermission -> checkPermission(action.perms)
        is ExplorerView.NavigateTo -> navigateTo(action.parent)
    }

    override fun onFragmentInteraction(action: Uri) {
        TODO("Event arch")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_explorer)
        setup()
    }

    lateinit var controller: ExplorerController
    lateinit var navigator: ExplorerNavigator

    private fun setup() {
        navigator = ExplorerNavigator(this)
        controller = ExplorerController(this)
        controller.event(ExplorerAgent.OnStart)
    }

    private fun checkPermission(perms: Array<String>) {
        if (EasyPermissions.hasPermissions(this, *perms)) {
            controller.event(ExplorerAgent.InitFileManager)
        } else {
            EasyPermissions.requestPermissions(
                this,
                "Need read permission",
                PERMISSION_READ_WRITE_STORAGE,
                *perms
            )
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    private fun navigateTo(path: String) = navigator.explorerNavigateTo(path)

    override fun onBackPressed() = navigator.onBackPressed()

}

