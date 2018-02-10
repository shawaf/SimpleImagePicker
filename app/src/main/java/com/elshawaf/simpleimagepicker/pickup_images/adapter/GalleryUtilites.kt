package com.elshawaf.simpleimagepicker.pickup_images.adapter

import android.app.Activity
import android.database.Cursor
import android.os.Environment
import android.provider.MediaStore
import android.util.Log

import java.util.ArrayList
import java.util.Collections

/**
 * Created by Shawaf on 12/12/2016.
 */

object GalleryUtilites {


    //Check if SD card is available so we get pics from internal or external memory
    val isSDAvailable: Boolean
        get() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

    // Get all images on the devices
    fun getAllImages(activity: Activity): HashMap<String, String> {
        val columns = arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
        val orderBy = MediaStore.Images.Media.DATE_ADDED

        //Stores all the images from the gallery in Cursor
        val cursor: Cursor?
        if (isSDAvailable) {
            Log.i("GalleryUtilities", "Getting Images from SD Card")
            cursor = activity.contentResolver.query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns,
                    null, null, orderBy)
        } else {
            Log.i("GalleryUtilities", "Getting Images from Internal Storage")
            cursor = activity.contentResolver.query(
                    MediaStore.Images.Media.INTERNAL_CONTENT_URI, columns, null, null, orderBy)
        }
        //Total number of images
        val count = cursor!!.count
        //Create an array to store path to all the images
        val arrPaths = HashMap<String, String>()

        for (i in 0 until count) {
            cursor.moveToPosition(i)
            val dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA)
            val bucketColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
            //Store the path of the image
            arrPaths.put(cursor.getString(dataColumnIndex),cursor.getString(bucketColumnIndex))

        }

        return arrPaths
    }

}
