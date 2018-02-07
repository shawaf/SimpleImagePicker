package com.elshawaf.simpleimagepicker.pickup_images.utilites

import android.app.Activity
import android.content.Context
import android.os.Build
import android.util.DisplayMetrics
import android.view.inputmethod.InputMethodManager
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by mohamedelshawaf on 1/15/18.
 */
class CommonMethods {

    companion object {
        //Check the version of sdk is installed
        fun shouldAskPermission(): Boolean {
            return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1
        }

        //Close Opened Keyboard
        fun closeKeyboard(context: Activity) {
            val cview = context.currentFocus
            if (cview != null) {
                val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(cview.windowToken, 0)
            }
        }

        fun getScreenWidth(context: Activity): Int {
            val metrics = DisplayMetrics()
            context.windowManager.defaultDisplay.getMetrics(metrics)
            return metrics.widthPixels
        }

        //Check the vlidity of email
        fun isValidMail(email2: String): Boolean {
            val check: Boolean
            val EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"
            val p = Pattern.compile(EMAIL_STRING)
            val m = p.matcher(email2)
            check = m.matches()
            return check
        }
    }


}