package id.cocoba.offlinestore.model

import java.io.Serializable

/**
 * Created by Chandra on 24/05/18.
 * Need some help?
 * Contact me at y.pristyan.chandra@gmail.com
 */
data class GoodsModel(
        var id: String? = null,
        var name: String? = null,
        var image: String? = null,
        var category_id: String? = null,
        var total: Int = 0,
        var price: Int = 0) : Serializable