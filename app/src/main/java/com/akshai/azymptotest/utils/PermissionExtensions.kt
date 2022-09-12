package com.akshai.azymptotest.utils

import android.Manifest
import android.app.Activity
import android.content.Context
import com.vmadalin.easypermissions.EasyPermissions

fun Context.hasStoragePermission() = EasyPermissions.hasPermissions(
    this,
    Manifest.permission.READ_EXTERNAL_STORAGE,
    Manifest.permission.WRITE_EXTERNAL_STORAGE
)

fun Activity.requestStoragePermission() {
    EasyPermissions.requestPermissions(
        this,
        "Can not find audio file with out this permission",
        1,
        Manifest.permission.READ_EXTERNAL_STORAGE,
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
    )
}