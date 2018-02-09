package sa.waqood.hakeem.ui.pickup_images

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v4.app.Fragment
import com.elshawaf.simpleimagepicker.pickup_images.PickUpImagesActivity
import com.elshawaf.simpleimagepicker.pickup_images.utilites.CommonMethods
import android.content.pm.PackageManager
import android.support.v4.content.PermissionChecker.checkCallingOrSelfPermission


/**
 * Created by mohamedelshawaf on 1/15/18.
 */
class PickUpImageUtilites {

    companion object {
        fun chooseProfilePicImage(activity: Activity, requestCode: Int, resultCode: Int) {
            if (CommonMethods.shouldAskPermission() && !checkWriteExternalPermission(activity)) {
                val permissions = arrayOf("android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE")
                activity.requestPermissions(permissions, requestCode)
            } else {
                dispatchSingleImageFromCustomPickup(activity, resultCode)
            }
        }


        fun dispatchSingleImageFromCustomPickup(activity: Activity, resultCode: Int) {
            activity.startActivityForResult(Intent(activity, PickUpImagesActivity::class.java), resultCode)
        }


        private fun checkWriteExternalPermission(activity: Activity): Boolean {
            val writePermission = android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            val readPermission = android.Manifest.permission.READ_EXTERNAL_STORAGE
            val writePermissionStatus = activity.checkCallingOrSelfPermission(writePermission)
            val readPermissionStatus = activity.checkCallingOrSelfPermission(readPermission)
            return (writePermissionStatus == PackageManager.PERMISSION_GRANTED && readPermissionStatus == PackageManager.PERMISSION_GRANTED)
        }
    }

}