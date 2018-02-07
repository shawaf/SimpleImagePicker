package sa.waqood.hakeem.ui.pickup_images

import android.content.Intent
import android.support.v4.app.Fragment

/**
 * Created by mohamedelshawaf on 1/15/18.
 */
class PickUpImageUtilites {

    companion object {
        //Getting Image From Device
        val singleImage: Int = 0
        val multipleImage: Int = 1
        fun chooseProfilePicImage(fragment: Fragment, resultCode: Int, selectType: Int) {
            if (CommonMethods.shouldAskPermission()) {
                val permissions = arrayOf("android.permission.WRITE_EXTERNAL_STORAGE", "android.permission.READ_EXTERNAL_STORAGE")
                fragment.requestPermissions(permissions, 200)
            } else {
//                if (selectType == singleImage)
//                    dispatchSingleImageFromGalleryIntent(fragment, resultCode)
//                else
//                    dispatchMultipleImageFromGalleryIntent(fragment, resultCode)
                dispatchSingleImageFromCustomPickup(fragment, resultCode)
            }
        }

        //Pick image from device
        fun dispatchSingleImageFromGalleryIntent(fragment: Fragment, resultCode: Int) {
            val intent = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
            }
            fragment.startActivityForResult(Intent.createChooser(intent, "Select Image"), resultCode)
        }

        fun dispatchSingleImageFromCustomPickup(fragment: Fragment, resultCode: Int) {
            val intent = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
            }
            fragment.startActivityForResult(Intent(fragment.activity, PickUpImagesActivity::class.java), resultCode)
        }

        //Pick multiple image from device
        fun dispatchMultipleImageFromGalleryIntent(fragment: Fragment, resultCode: Int) {
            val intent = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
                putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
            }
            fragment.startActivityForResult(Intent.createChooser(intent, "Select Image"), resultCode)
        }
    }

}