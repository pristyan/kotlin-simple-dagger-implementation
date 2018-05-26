package id.cocoba.offlinestore.util

import com.bumptech.glide.request.RequestOptions
import id.cocoba.offlinestore.R

/**
 * Created by Chandra on 24/05/18.
 * Need some help?
 * Contact me at y.pristyan.chandra@gmail.com
 */
object ImageUtil {
    fun requestOptions() = RequestOptions()
            .placeholder(R.drawable.img_placeholder)
            .error(R.drawable.img_placeholder)
}