package com.elshawaf.simpleimagepicker.pickup_images

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.view.WindowManager
import com.elshawaf.simpleimagepicker.R
import com.elshawaf.simpleimagepicker.pickup_images.model.GalleryModel
import com.elshawaf.simpleimagepicker.pickup_images.utilites.CommonStrings
import com.elshawaf.simpleimagepicker.pickup_images.utilites.SnacksManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.toolbar_lay.*
import java.util.*


class PickUpImagesActivity : AppCompatActivity() {

    private var fragmentManager: FragmentManager? = null
    private var fragmentTransaction: FragmentTransaction? = null
    var selectedImages: MutableList<GalleryModel> = ArrayList()
    private val pickUpFragmentTag = "pickup_fragment_tag"
    private var currentFragment = pickUpFragmentTag
    private var pickUpImagesFragment: PickUpImagesFragment? = null
    val selectedImageResult: String = "selectedImageResult"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_pick_up)

        initUI()
        // setUpStatusBar()
        setUpToolbar()
        initFragment()
    }

    private fun initUI() {
        toolbar_right_btn_icon_lay.setOnClickListener { checkSelectedImages() }
        toolbar_right_btn_icon_iv.setOnClickListener { checkSelectedImages() }
    }

//    private fun setUpStatusBar() {
//        window.addFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN)
//        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            val window = window
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
//            window.statusBarColor = ContextCompat.getColor(this, R.color.light_grey)
//        }
//    }

    fun setUpToolbar() {
        currentFragment = pickUpFragmentTag

        toolbar_title_tv.visibility = View.VISIBLE
        toolbar_back_lay.setOnClickListener { onBackPressed() }
        toolbar_right_btn_icon_lay.setOnClickListener {}

        toolbar_title_tv.text = "Gallery"
    }

    private fun initFragment() {
        fragmentManager = supportFragmentManager
        fragmentTransaction = fragmentManager!!.beginTransaction()

        pickUpImagesFragment = PickUpImagesFragment()
        fragmentTransaction!!.replace(R.id.gallery_fragholder, pickUpImagesFragment, pickUpFragmentTag)
        fragmentTransaction!!.commit()
    }

    private fun isSelectedImageAdded(url: String): Boolean {
        if (selectedImages.size != 0) {
            for (i in selectedImages.indices) {
                if (selectedImages[i].path == url) {
                    Log.e("GalleryActivity", "Selected Image")
                    return true
                }
            }
        }
        return false
    }

    private fun removeImageFromList(url: String) {
        if (selectedImages.size != 0) {
            for (i in selectedImages.indices - 1) {
                if (selectedImages[i].path == url)
                    selectedImages.removeAt(i)
            }
        }
    }

    private fun assignToFragment() {
        pickUpImagesFragment = supportFragmentManager.findFragmentByTag(pickUpFragmentTag) as PickUpImagesFragment
        if (pickUpImagesFragment != null && pickUpImagesFragment!!.isVisible)
            pickUpImagesFragment!!.onSelectImage(selectedImages)
    }

    fun onSelectMultipleImage(url: String) {
        if (isSelectedImageAdded(url)) {
            removeImageFromList(url)
        } else {
            selectedImages.add(GalleryModel(url))
        }
        assignToFragment()
    }

    fun onSelectSingleImage(url: String) {
        selectedImages.clear()
        selectedImages.add(GalleryModel(url))
        assignToFragment()
    }

    fun clearSelectedImages() {
        selectedImages.clear()
    }

    private fun checkSelectedImages() {

        Log.e("Pickup View", "Selected Images : " + selectedImages.size)
        if (selectedImages.size != 0) {
            // pickUpImagesFragment!!.publishImages(getSelectedImages())
            backWithSelectedImages()
        } else {
            SnacksManager.showSnack(this, "Nothing has been choosed")
        }
    }

    fun backWithSelectedImages() {
        val intent = intent
        intent.putExtra(CommonStrings.selectedImages, Gson().toJson(selectedImages))
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
