package com.elshawaf.simpleimagepicker.pickup_images

import android.app.Activity
import android.graphics.Rect
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.PopupWindow
import com.elshawaf.simpleimagepicker.R
import com.elshawaf.simpleimagepicker.pickup_images.adapter.GalleryUtilites
import com.elshawaf.simpleimagepicker.pickup_images.adapter.SpaceItemDecoration
import com.elshawaf.simpleimagepicker.pickup_images.model.FolderModel
import com.elshawaf.simpleimagepicker.pickup_images.model.GalleryModel
import com.elshawaf.simpleimagepicker.pickup_images.utilites.CommonStrings
import com.elshawaf.simpleimagepicker.pickup_images.utilites.SnacksManager
import com.google.gson.Gson
import kotlinx.android.synthetic.main.frag_pickup.*
import kotlinx.android.synthetic.main.popup_folders_view.*
import kotlinx.android.synthetic.main.popup_folders_view.view.*
import kotlinx.android.synthetic.main.toolbar_lay.*
import sa.waqood.hakeem.ui.pickup_images.adapter.FoldersListAdapter
import sa.waqood.hakeem.ui.pickup_images.adapter.GalGridAdapter
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


class PickUpImagesActivity : AppCompatActivity() {

    private var fragmentManager: FragmentManager? = null
    private var fragmentTransaction: FragmentTransaction? = null
    var selectedImages: MutableList<GalleryModel> = ArrayList()
    private val pickUpFragmentTag = "pickup_fragment_tag"
    private var currentFragment = pickUpFragmentTag
    val selectedImageResult: String = "selectedImageResult"

    //Folder PopUpWindow
    private var isFolderPopupWindowOpened = false
    private var popupWindow: PopupWindow?=null
    private var gridLayout: GridLayoutManager? = null
    private val imgPath: String? = null
    private var mainActivity: PickUpImagesActivity? = null
    private var galGridAdapter: GalGridAdapter? = null
    private var isMultipleChoiseActive = false
    private lateinit var imagesData: HashMap<String, String>
    private lateinit var foldersList: ArrayList<FolderModel>
    private lateinit var folderListAdapter: FoldersListAdapter
    private var allOption="All"
    private lateinit var imgPaths: ArrayList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery_pick_up)


        setUpToolbar()
        initUI()
        initGalleryGridRV()
    }

    fun setUpToolbar() {
        toolbar_title_tv.visibility = View.VISIBLE
        toolbar_back_lay.setOnClickListener { onBackPressed() }
        toolbar_right_btn_icon_lay.setOnClickListener {}
    }


    private fun initUI() {
        toolbar_right_btn_icon_lay.setOnClickListener { checkSelectedImages() }
        toolbar_right_btn_icon_iv.setOnClickListener { checkSelectedImages() }
        toolbar_title_lay.setOnClickListener { toggleFoldersListPopupWindow() }
    }

    private fun initGalleryGridRV() {
        gridLayout = GridLayoutManager(this, 4)
        gallery_pickup_rv.setHasFixedSize(true)
        gallery_pickup_rv.addItemDecoration(SpaceItemDecoration(4))
        gallery_pickup_rv.layoutManager = gridLayout

        //Load Images List From Device
        imagesData = GalleryUtilites.getAllImages(this)
        imgPaths = GalleryUtilites.getImagesPathsArr(imagesData,getString(R.string.all_option))
        galGridAdapter = GalGridAdapter(this, imgPaths)
        galGridAdapter!!.selectMultipleChoise()
        gallery_pickup_rv.adapter = galGridAdapter
    }


    private fun showImagesCOunt(count: Int) {
        if (count == 0) {
            toolbar_selection_count_tv.text = "0"
            toolbar_selection_count_tv.visibility = View.GONE
        } else {
            toolbar_selection_count_tv.visibility = View.VISIBLE
            toolbar_selection_count_tv.text = "" + count
        }
    }

    fun onSelectImage(galaryItemModels: List<GalleryModel>?) {
        if (galaryItemModels != null && galaryItemModels.size != 0) {
            showImagesCOunt(galaryItemModels.size)
        } else {
            showImagesCOunt(0)
        }
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
            (selectedImages.indices - 1)
                    .filter { selectedImages[it].path == url }
                    .forEach { selectedImages.removeAt(it) }
        }
    }

    fun onSelectMultipleImage(url: String) {
        if (isSelectedImageAdded(url)) {
            removeImageFromList(url)
        } else {
            selectedImages.add(GalleryModel(url))
        }
        onSelectImage(selectedImages)
    }

    fun onSelectSingleImage(url: String) {
        selectedImages.clear()
        selectedImages.add(GalleryModel(url))
        onSelectImage(selectedImages)
    }

    fun clearSelectedImages() {
        selectedImages.clear()
    }

    private fun checkSelectedImages() {
        Log.e("Pickup View", "Selected Images : " + selectedImages.size)
        if (selectedImages.size != 0) {
            backWithSelectedImages()
        } else {
            SnacksManager.showSnack(this, "Nothing has been choosed")
        }
    }

    private fun backWithSelectedImages() {
        val intent = intent
        intent.putExtra(CommonStrings.selectedImages, Gson().toJson(selectedImages))
        setResult(Activity.RESULT_OK, intent)
        finish()
    }

    //Show/ Hide Folder Popup Window on CLick of Toolbar Title
    private fun toggleFoldersListPopupWindow() {
        if (isFolderPopupWindowOpened && popupWindow!= null) {
            popupWindow!!.dismiss()
            isFolderPopupWindowOpened = false
            toolbar_title_expand_icon.setImageResource(R.drawable.action_expand_icon)
        } else {
            if (popupWindow == null) showFolderListPopWindow()
            else popupWindow!!.showAsDropDown(toolbar_title_lay, 0, 0, 0)
            isFolderPopupWindowOpened = true
            toolbar_title_expand_icon.setImageResource(R.drawable.unexpand_icon)
        }
    }

    private fun showFolderListPopWindow() {
        Log.e(localClassName,"Init Folders List PopUp")
        val popupView = LayoutInflater.from(this).inflate(R.layout.popup_folders_view, null)
        popupWindow = PopupWindow(popupView, WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)

        popupView.folders_rv.layoutManager = GridLayoutManager(this, 3)
        popupView.folders_rv.setHasFixedSize(true)
        popupView.folders_rv.addItemDecoration(SpaceItemDecoration(8))
        foldersList = getFolderModelsList(GalleryUtilites.getImagesFolderArr(imagesData))
        folderListAdapter = FoldersListAdapter(this, foldersList) {
            toggleFoldersListPopupWindow()
            updatedSelectedFolderUI(it.name)
            updatedOpenedImages(it.name)
        }
        popupView.folders_rv.adapter = folderListAdapter
        popupWindow!!.showAsDropDown(toolbar_title_lay, 0, 0, 0)
        popupWindow!!.setOutsideTouchable(false);

    }

    fun getFolderModelsList(folders: ArrayList<String>): ArrayList<FolderModel> {
        var foldersListModel = ArrayList<FolderModel>()
        foldersListModel.add(FolderModel(getString(R.string.all_option), true))
        folders.forEach {
            foldersListModel.add(FolderModel(it, false))
        }
        return foldersListModel
    }

    fun updatedSelectedFolderUI(selectedFolder: String) {
        foldersList.forEach {
            it.selected = false
            if (it.name.equals(selectedFolder)) it.selected = true
        }
        folderListAdapter.notifyDataSetChanged()
    }

    fun updatedOpenedImages(selectedFolder: String){
        imgPaths=GalleryUtilites.getImagesPathsArr(imagesData,selectedFolder)
        galGridAdapter!!.updatesList(imgPaths)
    }
}
