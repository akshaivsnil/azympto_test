package com.akshai.azymptotest.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.akshai.azymptotest.R
import com.akshai.azymptotest.utils.DataHandler
import com.akshai.azymptotest.utils.requestStoragePermission
import com.akshai.azymptotest.viewmodel.MyViewModel
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), EasyPermissions.PermissionCallbacks {

    private val myViewModel: MyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        myViewModel.mockLiveData.observe(this, Observer {
            when (it) {
                is DataHandler.ERROR -> {
                    //println("Response -> ${it.message}")
                }
                is DataHandler.LOADING -> {

                }
                is DataHandler.SUCCESS -> {
                    //println("Response -> ${it.data!!.items}")
                }
            }
        })

        this.requestStoragePermission()
    }


    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionDenied(this, perms.first()))
            SettingsDialog.Builder(this).build().show()
        else
            requestStoragePermission()
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>) {

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

}