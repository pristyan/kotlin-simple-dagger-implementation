package id.cocoba.offlinestore.data

import id.cocoba.offlinestore.model.GoodsModel
import javax.inject.Inject

/**
 * Created by Chandra on 24/05/18.
 * Need some help?
 * Contact me at y.pristyan.chandra@gmail.com
 */
class Goods @Inject constructor() {

    fun get(): ArrayList<GoodsModel> {
        val models = ArrayList<GoodsModel>()

        var item = GoodsModel()
        item.id = "11"
        item.category_id = Categories.FOOD
        item.name = "Ayam Bakar Tawon"
        item.price = 20000
        item.total = 0
        item.image = "https://selerasa.com/images/daging/ayam-bakar_madu.jpg"
        models.add(item)

        item = GoodsModel()
        item.id = "12"
        item.category_id = Categories.FOOD
        item.name = "Gurame Kecut Legi"
        item.price = 35000
        item.total = 0
        item.image = "http://restojambon.com/wp-content/uploads/2016/05/697662bb232c0b13c774965ef8558f0fbd6155e68079126ac457d6372c6154c905094.jpg"
        models.add(item)

        item = GoodsModel()
        item.id = "13"
        item.category_id = Categories.FOOD
        item.name = "Nasi Goreng Jeliteng"
        item.price = 27000
        item.total = 0
        item.image = "http://www.kulinersehat.info/wp-content/uploads/2016/04/Resep-Nasi-Goreng-Hitam-yang-Maknyuss-dan-Mantap.jpeg"
        models.add(item)

        item = GoodsModel()
        item.id = "14"
        item.category_id = Categories.FOOD
        item.name = "Nasi Pecel"
        item.price = 15000
        item.total = 0
        item.image = "http://www.pengusahasukses.com/wp-content/uploads/2016/06/Peluang-Bisnis-Nasi-Pecel-dan-Analisa-Usahanya.jpg"
        models.add(item)

        item = GoodsModel()
        item.id = "21"
        item.category_id = Categories.DESSERT
        item.name = "Puding Soklat Oreo"
        item.price = 15000
        item.total = 0
        item.image = "http://kissfmmedan.com/wp-content/uploads/2017/10/puding-coklat-oreo.jpg"
        models.add(item)

        item = GoodsModel()
        item.id = "22"
        item.category_id = Categories.DESSERT
        item.name = "Salad Buah"
        item.price = 17000
        item.total = 0
        item.image = "https://foodal.com/wp-content/uploads/2015/05/Best-Fruit-Salad-Yogurt-Parfait-Recipe.jpg"
        models.add(item)

        item = GoodsModel()
        item.id = "31"
        item.category_id = Categories.DRINK
        item.name = "Es Teh Panas"
        item.price = 3500
        item.total = 0
        item.image = "http://cdn2.tstatic.net/palembang/foto/bank/images/es-teh-manis_20170907_143242.jpg"
        models.add(item)

        item = GoodsModel()
        item.id = "32"
        item.category_id = Categories.DRINK
        item.name = "Es Jeruk Purut"
        item.price = 5000
        item.total = 0
        item.image = "https://cdns.klimg.com/vemale.com/headline/650x325/2015/07/sajian-segar-buka-puasa-es-timun-jeruk-nipis-khas-aceh.jpg"
        models.add(item)

        item = GoodsModel()
        item.id = "33"
        item.category_id = Categories.DRINK
        item.name = "Es Blewah"
        item.price = 7000
        item.total = 0
        item.image = "http://assets.akurat.co/images/uploads/akurat_20170526_1B21e0.jpg"
        models.add(item)

        return models
    }
}