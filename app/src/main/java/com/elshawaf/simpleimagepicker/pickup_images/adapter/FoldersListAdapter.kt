package sa.waqood.hakeem.ui.pickup_images.adapter

import android.app.Activity
import android.os.Build
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.elshawaf.simpleimagepicker.R
import com.elshawaf.simpleimagepicker.pickup_images.PickUpImagesActivity
import com.elshawaf.simpleimagepicker.pickup_images.model.FolderModel
import com.elshawaf.simpleimagepicker.pickup_images.utilites.CommonMethods
import kotlinx.android.synthetic.main.item_folder_view.view.*
import kotlinx.android.synthetic.main.item_gal_grid.view.*
import java.util.*

/**
 * Created by Shawaf on 7/14/2016.
 */
class FoldersListAdapter(val activity: Activity, private val foldersList: List<FolderModel>, val itemClick: (FolderModel) -> Unit) : RecyclerView.Adapter<FoldersListAdapter.HomeGridViewHolders>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeGridViewHolders {
        val layoutView = LayoutInflater.from(parent.context).inflate(R.layout.item_folder_view, null)
        return HomeGridViewHolders(layoutView, itemClick)
    }


    override fun onBindViewHolder(holder: HomeGridViewHolders, position: Int) {
        holder.bindData(foldersList[position])
    }

    override fun getItemCount(): Int {
        return this.foldersList.size
    }

    inner class HomeGridViewHolders(itemView: View, val itemClick: (FolderModel) -> Unit) : RecyclerView.ViewHolder(itemView) {
        fun bindData(folder: FolderModel) {
            itemView.item_folder_title_tv.layoutParams.width = CommonMethods.getScreenWidth(activity) / 3 - 8
            itemView.item_folder_title_tv.text = folder.name
            selectFolder(folder.selected)

            itemView.item_folder_title_tv.setOnClickListener {
                itemClick(folder)
//                folder.selected = !folder.selected
//                resetFolderSelection()
//                selectFolder(folder.selected)
            }

        }

        private fun resetFolderSelection() {
            foldersList.forEach {
                it.selected = false
                notifyDataSetChanged()
            }
        }

        private fun selectFolder(selected: Boolean) {
            if (selected) {
                itemView.item_folder_title_tv.setBackgroundResource(R.drawable.choise_bg_filled)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    itemView.item_folder_title_tv.setTextColor(activity.getColor(R.color.white))
                } else {
                    itemView.item_folder_title_tv.setTextColor(activity.resources.getColor(R.color.white))
                }
            } else {
                itemView.item_folder_title_tv.setBackgroundResource(R.drawable.choise_bg_unfilled)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    itemView.item_folder_title_tv.setTextColor(activity.getColor(R.color.dark_grey))
                } else {
                    itemView.item_folder_title_tv.setTextColor(activity.resources.getColor(R.color.dark_grey))
                }
            }
        }
    }

}
