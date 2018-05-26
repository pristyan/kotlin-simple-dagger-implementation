package id.cocoba.offlinestore.adapter

import android.annotation.SuppressLint
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import id.cocoba.offlinestore.R
import id.cocoba.offlinestore.model.CategoryModel
import id.cocoba.offlinestore.util.ImageUtil
import kotlinx.android.synthetic.main.item_category.view.*

/**
 * Created by Chandra on 24/05/18.
 * Need some help?
 * Contact me at y.pristyan.chandra@gmail.com
 */
class CategoryAdapter(private var listener: ItemListener) : RecyclerView.Adapter<CategoryAdapter.Holder>() {

    private var models: ArrayList<CategoryModel> = ArrayList()

    fun update(models: ArrayList<CategoryModel>) {
        this.models = models
        notifyDataSetChanged()
    }

    fun get() = models

    fun getSelectedId(): String? {
        var id: String? = null
        (0 until models.size)
                .filter { models[it].selected }
                .forEach { id = models[it].id }
        return id
    }

    fun select(position: Int) {
        (0 until models.size).forEach { models[it].selected = it == position }
        notifyDataSetChanged()
    }

    fun unselect(position: Int) {
        (0 until models.size)
                .filter { position == it }
                .forEach {
                    models[it].selected = false
                    notifyItemChanged(it)
                }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            Holder(this, LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false))

    override fun getItemCount(): Int = models.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(models[position], listener, position)
    }

    class Holder(private var adapter: CategoryAdapter, view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bind(item: CategoryModel, listener: ItemListener, position: Int) = with(itemView) {
            Glide.with(context).load(item.image)
                    .apply(ImageUtil.requestOptions())
                    .into(img_category)

            if (item.selected)
                Glide.with(context).load(R.drawable.ic_category_selected)
                        .apply(ImageUtil.requestOptions())
                        .into(img_selected)
            else Glide.with(context).load(R.drawable.ic_category_default)
                    .apply(ImageUtil.requestOptions())
                    .into(img_selected)

            txt_name.text = item.name
            itemView.setOnClickListener {
                if (item.selected) {
                    adapter.unselect(position)
                    listener.selectCategory(null, position)
                } else {
                    adapter.select(position)
                    listener.selectCategory(item, position)
                }
            }
        }
    }

    interface ItemListener {
        fun selectCategory(item: CategoryModel?, position: Int)
    }
}