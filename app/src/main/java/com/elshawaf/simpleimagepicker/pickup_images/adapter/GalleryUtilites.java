package com.elshawaf.simpleimagepicker.pickup_images.adapter;

import android.app.Activity;
import android.database.Cursor;
import android.provider.MediaStore;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Shawaf on 12/12/2016.
 */

public class GalleryUtilites {


    //Check if SD card is available so we get pics from internal or external memory
    public static boolean isSDAvailable() {
        Boolean isSDPresent = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        return isSDPresent;
    }

    // Get all images on the devices
    public static List<String> getAllImages(Activity activity) {
        final String[] columns = {MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID};
        final String orderBy = MediaStore.Images.Media._ID;
        //Stores all the images from the gallery in Cursor
        Cursor cursor;
        if (isSDAvailable()) {
            Log.i("GalleryUtilities", "Getting Images from SD Card");
            cursor = activity.getContentResolver().query(
                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null,
                    null, orderBy);
        } else {
            Log.i("GalleryUtilities", "Getting Images from Internal Storage");
            cursor = activity.getContentResolver().query(
                    MediaStore.Images.Media.INTERNAL_CONTENT_URI, columns, null,
                    null, orderBy);
        }
        //Total number of images
        int count = cursor.getCount();

        //Create an array to store path to all the images
        List<String> arrPaths = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            cursor.moveToPosition(i);
            int dataColumnIndex = cursor.getColumnIndex(MediaStore.Images.Media.DATA);
            //Store the path of the image
            arrPaths.add(cursor.getString(dataColumnIndex));
        }
        Collections.reverse(arrPaths);
        return arrPaths;
    }
}
