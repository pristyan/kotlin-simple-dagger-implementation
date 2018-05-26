package id.cocoba.offlinestore.data

import id.cocoba.offlinestore.R
import id.cocoba.offlinestore.model.CategoryModel
import javax.inject.Inject

/**
 * Created by Chandra on 26/05/18.
 * Need some help?
 * Contact me at y.pristyan.chandra@gmail.com
 */
class Categories @Inject constructor() {

    companion object {
        const val FOOD = "1"
        const val DESSERT = "2"
        const val DRINK = "3"
    }

    fun get(): ArrayList<CategoryModel> {
        val models = ArrayList<CategoryModel>()

        var item = CategoryModel()
        item.id = FOOD
        item.name = "Food"
        item.image = R.drawable.img_main_course
        models.add(item)

        item = CategoryModel()
        item.id = DESSERT
        item.name = "Dessert"
        item.image = R.drawable.img_dessert
        models.add(item)

        item = CategoryModel()
        item.id = DRINK
        item.name = "Drink"
        item.image = R.drawable.img_drinks
        models.add(item)

        return models
    }
}