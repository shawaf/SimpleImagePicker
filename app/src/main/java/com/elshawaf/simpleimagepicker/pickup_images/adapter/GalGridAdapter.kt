package sa.waqood.hakeem.ui.pickup_images.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.elshawaf.simpleimagepicker.R
import com.elshawaf.simpleimagepicker.pickup_images.PickUpImagesActivity
import com.elshawaf.simpleimagepicker.pickup_images.utilites.CommonMethods
import kotlinx.android.synthetic.main.item_gal_grid.view.*
import java.util.*

/**
 * Created by Shawaf on 7/14/2016.
 */
class GalGridAdapter(context: Activity, private var imgPaths: List<String>) : RecyclerView.Adapter<GalGridAdapter.HomeGridViewHolders>() {
    private val context: PickUpImagesActivity
    private val selectedPositions = ArrayList<Int>()
    private var isMultipleChoise = false

    init {
        this.context = context as PickUpImagesActivity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeGridViewHolders {
        val layoutView = LayoutInflater.from(parent.context).inflate(R.layout.item_gal_grid, null)
        return HomeGridViewHolders(layoutView)
    }


    override fun onBindViewHolder(holder: HomeGridViewHolders, position: Int) {
        holder.bindData(imgPaths[position])
    }

    private fun onMultipleChoiseSelect(position: Int, checkIV: ImageView) {
        if (selectedPositions.contains(position)) {
            checkIV.visibility = View.GONE
            selectedPositions.removeAt(selectedPositions.indexOf(position))
        } else {
            checkIV.visibility = View.VISIBLE
            selectedPositions.add(position)
        }
    }

    private fun onSingleChoiseSelect(position: Int) {
        selectedPositions.clear()
        selectedPositions.add(position)
    }

    override fun getItemCount(): Int {
        return this.imgPaths.size
    }

    inner class HomeGridViewHolders
    (itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            itemView.gal_iv.layoutParams.width = CommonMethods.getScreenWidth(context) / 4 - 8

        }

        fun bindData(imagePath: String) {
            Glide.with(context)
                    .load(imagePath).centerCrop()
                    .into(itemView.gal_iv)
            if (selectedPositions.contains(adapterPosition))
                itemView.item_gal_grid_chick_iv.visibility = View.VISIBLE
            else
                itemView.item_gal_grid_chick_iv.visibility = View.GONE


            if (isMultipleChoise == false)
                itemView.item_gal_grid_chick_lay.visibility = View.GONE
            else
                itemView.item_gal_grid_chick_lay.visibility = View.VISIBLE

            itemView.gal_iv.setOnClickListener(View.OnClickListener {
                if (isMultipleChoise) {
                    context.onSelectMultipleImage(imagePath)
                    onMultipleChoiseSelect(adapterPosition, itemView.item_gal_grid_chick_iv)
                } else {
                    context.onSelectSingleImage(imagePath)
                    onSingleChoiseSelect(adapterPosition)
                }
            })
        }
    }


    fun selectMultipleChoise() {
        if (this.isMultipleChoise == false) {
            this.isMultipleChoise = true
        } else {
            this.isMultipleChoise = false
            clearSelectedPositions()
        }
    }

    fun clearSelectedPositions() {
        selectedPositions.clear()
        context.clearSelectedImages()
    }

    fun updatesList(newImagesPathsList: List<String>){
        this.imgPaths=newImagesPathsList
        notifyDataSetChanged()
    }


}
