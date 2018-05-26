package id.cocoba.offlinestore.model

import java.io.Serializable

/**
 * Created by Chandra on 26/05/18.
 * Need some help?
 * Contact me at y.pristyan.chandra@gmail.com
 */
data class CategoryModel(
        var id: String? = null,
        var name: String? = null,
        var image: Int? = null,
        var selected: Boolean = false): Serializable