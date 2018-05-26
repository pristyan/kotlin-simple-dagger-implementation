package id.cocoba.offlinestore

import android.support.v7.app.AppCompatActivity
import android.view.View

/**
 * Created by Chandra on 25/05/18.
 * Need some help?
 * Contact me at y.pristyan.chandra@gmail.com
 */
abstract class BaseActivity: AppCompatActivity() {

    fun View.visible() {
        visibility = View.VISIBLE
    }

    fun View.gone() {
        visibility = View.GONE
    }
}