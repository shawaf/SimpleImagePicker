package com.elshawaf.simpleimagepicker

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.elshawaf.simpleimagepicker.pickup_images.model.GalleryModel
import com.elshawaf.simpleimagepicker.pickup_images.utilites.CommonStrings
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*
import sa.waqood.hakeem.ui.pickup_images.PickUpImageUtilites

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()
    }


    fun initUI() {
        opengallery_btn.setOnClickListener { PickUpImageUtilites.chooseProfilePicImage(this, CommonStrings.PICKUP_IMAGE_REQUEST_CODE, CommonStrings.PICKUP_IMAGE_RESULT_CODE) }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int,
                                  imageReturnedIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent)

        when (requestCode) {
            CommonStrings.PICKUP_IMAGE_RESULT_CODE -> if (resultCode == Activity.RESULT_OK) {
                val gson = Gson()
                val type = object : TypeToken<List<GalleryModel>>() {}.type
                val imagesAsString = imageReturnedIntent!!.getStringExtra(CommonStrings.selectedImages)
                selectedimages_links_tv.text = imagesAsString

            }
        }
    }

    override fun onRequestPermissionsResult(permsRequestCode: Int, permissions: Array<String>, grantResults: IntArray) {

        when (permsRequestCode) {
            200 -> {
                val writeAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED
                if (writeAccepted == true) {
                    PickUpImageUtilites.dispatchSingleImageFromCustomPickup(this, CommonStrings.PICKUP_IMAGE_RESULT_CODE)
                }
            }
        }
    }


    fun getSelectedGalleryModelsAsString(selectedIDImages: List<GalleryModel>): String {
        val gson = Gson()
        return gson.toJson(selectedIDImages)
    }

    fun getSelectedImagesStringAsModels(selectedImagesString: String): MutableList<GalleryModel> {
        val gson = Gson()
        val type = object : TypeToken<List<GalleryModel>>() {}.type
        return gson.fromJson(selectedImagesString, type)
    }

}
