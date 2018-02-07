package sa.waqood.hakeem.ui.pickup_images

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.elshawaf.simpleimagepicker.R
import com.elshawaf.simpleimagepicker.pickup_images.adapter.GalleryUtilites
import com.elshawaf.simpleimagepicker.pickup_images.adapter.SpaceItemDecoration
import com.elshawaf.simpleimagepicker.pickup_images.model.GalleryModel
import kotlinx.android.synthetic.main.frag_pickup.*
import sa.waqood.hakeem.ui.pickup_images.adapter.GalGridAdapter

/**
 * Created by Shawaf on 1/3/2017.
 */

class PickUpImagesFragment : Fragment() {
    lateinit var mainView: View

    private var gridLayout: GridLayoutManager? = null
    private val imgPath: String? = null
    private var mainActivity: PickUpImagesActivity? = null
    private var galGridAdapter: GalGridAdapter? = null
    private var isMultipleChoiseActive = false

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mainView = inflater!!.inflate(R.layout.frag_pickup, container, false)
        return mainView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI()
        initGalleryGridRV()
        fillImages()
        mainActivity!!.setUpToolbar()
    }

    private fun initUI() {
       // pickup_multiple_images_lay.setOnClickListener { activeMultipleChoise() }
    }

    private fun initGalleryGridRV() {
        gridLayout = GridLayoutManager(activity, 4)
        gallery_pickup_rv.setHasFixedSize(true)
        gallery_pickup_rv.addItemDecoration(SpaceItemDecoration(4))
        gallery_pickup_rv.layoutManager = gridLayout
    }

    private fun fillImages() {
        val imgPaths = GalleryUtilites.getAllImages(activity)
        galGridAdapter = GalGridAdapter(activity, imgPaths)
        gallery_pickup_rv.adapter = galGridAdapter
    }

    private fun setSelectedImageToView(imageUrl: String) {
//        Glide.with(activity)
//                .load(imageUrl).centerCrop().crossFade()
//                .into(pickup_selected_image_iv)
    }

    private fun showImagesCOunt(count: Int) {
//        if (count == 0) {
//            pickup_selected_count_tv.text = "0"
//            pickup_selected_count_tv.visibility = View.GONE
//        } else {
//            pickup_selected_count_tv.visibility = View.VISIBLE
//            pickup_selected_count_tv.text = "" + count
//        }
    }

    fun onSelectImage(galaryItemModels: List<GalleryModel>?) {
        if (galaryItemModels != null && galaryItemModels.size != 0) {
            showImagesCOunt(galaryItemModels.size)
            setSelectedImageToView(galaryItemModels[galaryItemModels.size - 1].path)
        } else {
            showImagesCOunt(0)
        }
    }

    private fun updateMultipleChoiseUI() {
        if (isMultipleChoiseActive == false) {
            isMultipleChoiseActive = true
//            pickup_multiple_images_lay.setBackgroundResource(R.drawable.btn_rounded_corner)
        } else {
            isMultipleChoiseActive = false
//            pickup_multiple_images_lay.setBackgroundResource(R.drawable.muliple_images_bg)
//            pickup_selected_count_tv.visibility = View.GONE
        }
    }

    private fun activeMultipleChoise() {
        updateMultipleChoiseUI()
        if (galGridAdapter != null) {
            galGridAdapter!!.selectMultipleChoise()
            galGridAdapter!!.notifyDataSetChanged()
        }
    }


}
