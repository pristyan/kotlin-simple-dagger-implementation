package id.cocoba.offlinestore.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import java.text.NumberFormat
import java.util.*


/**
 * Created by Chandra on 24/05/18.
 * Need some help?
 * Contact me at y.pristyan.chandra@gmail.com
 */
object Util {
    fun nominalFormatter(input: String?): String = try {
        if (input == null || input == "" || input == "null") "0"
        else {
            val inp = input.toLong()
            val numberFormat = NumberFormat.getNumberInstance(Locale.US)
            numberFormat.format(inp)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        "0"
    }

    fun hideSoftKeyboard(activity: Activity, view: View) {
        val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.applicationWindowToken, 0)
    }
}