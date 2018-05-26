package id.cocoba.offlinestore

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import id.cocoba.offlinestore.adapter.CategoryAdapter
import id.cocoba.offlinestore.adapter.GoodsAdapter
import id.cocoba.offlinestore.adapter.SelectedGoodsAdapter
import id.cocoba.offlinestore.component.DaggerDummyInjector
import id.cocoba.offlinestore.data.Categories
import id.cocoba.offlinestore.data.Goods
import id.cocoba.offlinestore.model.CategoryModel
import id.cocoba.offlinestore.model.GoodsModel
import id.cocoba.offlinestore.util.Util
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.bottom_sheet_view.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast
import javax.inject.Inject

class MainActivity : BaseActivity(), GoodsAdapter.ItemListener, CategoryAdapter.ItemListener {

    private lateinit var adapter: GoodsAdapter
    private lateinit var selectedAdapter: SelectedGoodsAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private lateinit var sheetBehavior: BottomSheetBehavior<*>

    @Inject lateinit var goods: Goods
    @Inject lateinit var categories: Categories

    private var searchingHandler = Handler()
    private var searchingItem = Runnable { doSearch() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        DaggerDummyInjector.create().inject(this)
        toolbar_main.inflateMenu(R.menu.menu_main)

        sheetBehavior = BottomSheetBehavior.from(bottom_sheet)
        sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        sheetBehavior.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onSlide(bottomSheet: View, slideOffset: Float) = Unit

            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> view_margin_sheet.gone()
                    BottomSheetBehavior.STATE_COLLAPSED -> view_margin_sheet.visible()
                }
            }
        })

        txt_search.setOnEditorActionListener(searchActionListener())
        txt_search.addTextChangedListener(textWatcher())
        txt_search.onFocusChangeListener = searchFocusListener()

        categoryAdapter = CategoryAdapter(this)
        rv_category.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rv_category.adapter = categoryAdapter
        categoryAdapter.update(categories.get())

        adapter = GoodsAdapter(this)
        rv_goods.layoutManager = LinearLayoutManager(this)
        rv_goods.adapter = adapter
        adapter.update(goods.get())

        selectedAdapter = SelectedGoodsAdapter()
        rv_selected_goods.layoutManager = LinearLayoutManager(this)
        rv_selected_goods.adapter = selectedAdapter

        view_header_sheet.setOnClickListener {
            if (sheetBehavior.state == BottomSheetBehavior.STATE_EXPANDED)
                sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            else sheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
        }

        btn_order.setOnClickListener {
            alert(R.string.msg_order_confirmation) {
                positiveButton(R.string.btn_yes, {
                    order()
                    it.dismiss()
                })
                negativeButton(R.string.btn_no, { it.dismiss() })
            }.show()
        }
    }

    private fun order() {
        toast(R.string.msg_order_success)
        rv_goods.scrollToPosition(0)
        (0 until goods.get().size).forEach { goods.get()[it].total = 0 }
        adapter.update(goods.get())
        selectedAdapter.update(ArrayList())
        sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    private fun hideMenu() {
        toolbar_main.menu.getItem(0).isVisible = false
        toolbar_main.menu.getItem(1).isVisible = false
    }

    private fun showMenu() {
        toolbar_main.menu.getItem(0).isVisible = true
        toolbar_main.menu.getItem(1).isVisible = true
    }

    private fun textWatcher() = object : TextWatcher {
        override fun afterTextChanged(p0: Editable?) {
            searchingHandler.postDelayed(searchingItem, 300)
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            searchingHandler.removeCallbacks(searchingItem)
        }

    }

    private fun searchFocusListener() = View.OnFocusChangeListener { _, isFocus ->
        if (isFocus) hideMenu()
        else showMenu()
    }

    private fun searchActionListener() = TextView.OnEditorActionListener { _, _, _ ->
        Util.hideSoftKeyboard(this, txt_search)
        txt_search.clearFocus()
        true
    }

    private fun doSearch() {
        val items = ArrayList<GoodsModel>()
        val categoryId = categoryAdapter.getSelectedId()
        (0 until goods.get().size)
                .filter {
                    goods.get()[it].name?.toLowerCase()?.contains(txt_search.text.toString()) == true
                            && (categoryId == null || goods.get()[it].category_id == categoryId)
                }
                .forEach { items.add(goods.get()[it]) }

        rv_goods.scheduleLayoutAnimation()
        adapter.update(items)
    }

    @SuppressLint("SetTextI18n")
    private fun countSummary() {
        var totalItem = 0
        var totalPrice = 0
        if (selectedAdapter.get().size > 0) {
            val items = selectedAdapter.get()
            (0 until items.size).forEach {
                totalItem += items[it].total
                totalPrice += (items[it].total * items[it].price)
            }
        }

        txt_total_amount.text = "Rp ${Util.nominalFormatter(totalPrice.toString())}"
        txt_total_item.text = "$totalItem items"
    }

    override fun add(item: GoodsModel) {
        if (selectedAdapter.get().isEmpty()) selectedAdapter.add(item)
        else {
            val position: Int? = selectedAdapter.getItem(item)
            if (position == null) selectedAdapter.add(item)
            else selectedAdapter.plus(position)
        }

        countSummary()
        sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    override fun minus(item: GoodsModel) {
        if (!selectedAdapter.get().isEmpty()) {
            val position: Int? = selectedAdapter.getItem(item)
            position?.let {
                if (item.total > 0) selectedAdapter.minus(it)
                else selectedAdapter.remove(item)
            }
        }

        countSummary()
        if (selectedAdapter.get().size > 0) sheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
        else sheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    override fun selectCategory(item: CategoryModel?, position: Int) {
        rv_category.smoothScrollToPosition(position)

        if (item == null) doSearch()
        else {
            val filtered = ArrayList<GoodsModel>()
            (0 until goods.get().size)
                    .filter {
                        goods.get()[it].category_id == item.id
                                && goods.get()[it].name?.toLowerCase()?.contains(txt_search.text.toString()) == true
                    }
                    .forEach { filtered.add(goods.get()[it]) }

            rv_goods.scheduleLayoutAnimation()
            adapter.update(filtered)
        }
    }
}
