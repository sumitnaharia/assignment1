package com.example.myapplication.binder

import android.widget.CompoundButton
import androidx.databinding.BindingAdapter
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.adapter.RecyclerViewAdapter
import com.example.myapplication.clickHandler.CheckedChangeListner
import com.example.myapplication.clickHandler.ClickHandler
import com.example.myapplication.clickHandler.RecuclerChildClickHandler


object RecyclerViewBinding {

    private val KEY_ITEMS = -123
    private val KEY_CLICK_HANDLER = -124
    private val KEY_LONG_CLICK_HANDLER = -125
    private val KEY_CHIELD_CLICK_HANDLER = -126
    private val KEY_INNER_CHIELD_CLICK_HANDLER = -127
    private val KEY_INNER_PARRENT_POSITION = -128
    private val KEY_CHANGED_LISTNER = -129


    //    bind:clickHandler="@{view.clickHandler}"
    //    bind:longClickHandler="@{view.longClickHandler}"

    @BindingAdapter("items")
    @JvmStatic
    fun <T> setItems(recyclerView: RecyclerView, items: Collection<T>) {
        val adapter = recyclerView.adapter as RecyclerViewAdapter<T>?
        if (adapter != null) {
            adapter!!.setItems(items!!)
        } else {
            recyclerView.setTag(KEY_ITEMS, items)
        }
    }


    @BindingAdapter("childClickHandler")
    @JvmStatic
    fun <T> setChildClickHandler(
        recyclerView: RecyclerView,
        chieldClickHandler: RecuclerChildClickHandler<T>
    ) {

        val adapter = recyclerView.adapter as RecyclerViewAdapter<T>?
        if (adapter != null) {
            adapter!!.setChieldClickHandler(chieldClickHandler)
        } else {
            recyclerView.setTag(KEY_CHIELD_CLICK_HANDLER, chieldClickHandler)
        }
    }


    @BindingAdapter("parentPosition")
    @JvmStatic
    fun <T> setChildClickHandler(recyclerView: RecyclerView, parentPosition: String) {
        val adapter = recyclerView.adapter as RecyclerViewAdapter<T>?
//        if (adapter != null) {
//            adapter!!.parentPosition = parentPosition
//        } else {
//            recyclerView.setTag(KEY_INNER_PARRENT_POSITION, parentPosition)
//        }
    }

    @BindingAdapter("clickHandler")
    @JvmStatic
    fun <T> setHandler(recyclerView: RecyclerView, handler: ClickHandler<T>) {
        val adapter = recyclerView.adapter as RecyclerViewAdapter<T>?
        if (adapter != null) {
            adapter!!.setClickHandler(handler)
        } else {
            recyclerView.setTag(KEY_CLICK_HANDLER, handler)
        }
    }

    @BindingAdapter("layoutManager")
    @JvmStatic
    fun setLayoutManager(recyclerView: RecyclerView, layoutManager: RecyclerView.LayoutManager) {
        recyclerView.layoutManager = layoutManager

    }


    @BindingAdapter("itemViewBinder")
    @JvmStatic
    fun <T> setItemViewBinder(recyclerView: RecyclerView, itemViewMapper: ItemBinder<T>) {
        var items = recyclerView.getTag(KEY_ITEMS)

        var clickHandler = recyclerView.getTag(KEY_CLICK_HANDLER) as ClickHandler<T>
        var checkedListner =
            recyclerView.getTag(KEY_CHANGED_LISTNER) as CheckedChangeListner<T>

        var chieldClickHandler =
            recyclerView.getTag(KEY_CHIELD_CLICK_HANDLER) as RecuclerChildClickHandler<T>

        var adapter = RecyclerViewAdapter(itemViewMapper, items as Collection<T>? ?: ArrayList())
        recyclerView.adapter = adapter
        if (clickHandler != null) {
            adapter.setClickHandler(clickHandler)
        }
        if (chieldClickHandler != null) {
            adapter.setChieldClickHandler(chieldClickHandler)
        }
        if (checkedListner != null) {
            adapter.setChieldClickHandler(chieldClickHandler)
        }


    }

    @BindingAdapter("checkChangedListner")
    @JvmStatic
    fun <T> setCheckedListner(
        recyclerView: RecyclerView,
        checkedListner: CheckedChangeListner<T>
    ) {
        recyclerView.setTag(KEY_CHANGED_LISTNER, checkedListner)

    }

}


