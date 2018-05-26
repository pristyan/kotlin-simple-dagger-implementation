package id.cocoba.offlinestore.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import id.cocoba.offlinestore.R
import id.cocoba.offlinestore.model.GoodsModel
import id.cocoba.offlinestore.util.ImageUtil
import id.cocoba.offlinestore.util.Util
import kotlinx.android.synthetic.main.item_goods.view.*

/**
 * Created by Chandra on 24/05/18.
 * Need some help?
 * Contact me at y.pristyan.chandra@gmail.com
 */
class GoodsAdapter(private var listener: ItemListener) : RecyclerView.Adapter<GoodsAdapter.Holder>() {

    private var models: ArrayList<GoodsModel> = ArrayList()

    fun update(models: ArrayList<GoodsModel>) {
        this.models = models
        notifyDataSetChanged()
    }

    fun get() = models

    fun plus(position: Int) {
        models[position].total += 1
        notifyItemChanged(position)
    }

    fun minus(position: Int) {
        if (models[position].total > 0) {
            models[position].total -= 1
            notifyItemChanged(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            Holder(this, LayoutInflater.from(parent.context).inflate(R.layout.item_goods, parent, false))

    override fun getItemCount(): Int = models.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(models[position], position, listener)
    }

    class Holder(private var adapter: GoodsAdapter, view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bind(item: GoodsModel, position: Int, listener: ItemListener) = with(itemView) {
            Glide.with(context).load(item.image)
                    .apply(ImageUtil.requestOptions())
                    .into(img_item)

            txt_title.text = item.name
            txt_total.text = item.total.toString()
            txt_price.text = "Rp ${Util.nominalFormatter(item.price.toString())}"

            btn_plus.setOnClickListener {
                adapter.plus(position)
                listener.add(item)
            }
            btn_minus.setOnClickListener {
                adapter.minus(position)
                listener.minus(item)
            }
        }
    }

    interface ItemListener {
        fun add(item: GoodsModel)
        fun minus(item: GoodsModel)
    }
}