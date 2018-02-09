package com.elshawaf.simpleimagepicker.pickup_images.utilites

import android.app.Activity
import android.support.design.widget.Snackbar
import android.view.View


/**
 * Created by mohamedelshawaf on 9/23/17.
 */

object SnacksManager {


//    fun showSomethingWrongdSnack(activity: Activity) {
//        Snackbar.make(activity.findViewById<View>(android.R.id.content), activity.getString(R.string.failed_try), Snackbar.LENGTH_LONG).show()
//    }
//
//
//    fun showNotConnectedSnack(activity: Activity) {
//        Snackbar.make(activity.findViewById<View>(android.R.id.content), activity.getString(R.string.connection_error), Snackbar.LENGTH_LONG).show()
//    }


    fun showSnack(activity: Activity, message: String) {
        Snackbar.make(activity.findViewById<View>(android.R.id.content), message, Snackbar.LENGTH_LONG).show()
    }


}
