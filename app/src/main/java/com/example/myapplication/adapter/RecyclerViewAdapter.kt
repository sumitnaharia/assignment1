package com.example.myapplication.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.databinding.DataBindingUtil
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableList
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.BR
import com.example.myapplication.binder.ItemBinder
import com.example.myapplication.clickHandler.CheckedChangeListner
import com.example.myapplication.clickHandler.ClickHandler
import com.example.myapplication.clickHandler.RecuclerChildClickHandler
import java.lang.ref.WeakReference

class RecyclerViewAdapter<T>(private val itemBinder: ItemBinder<T>, items: Collection<T>?) :
    RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>(), View.OnClickListener {
    var items: ObservableList<T>? = null
        private set
    private var inflater: LayoutInflater? = null
    private val onListChangedCallback: WeakReferenceOnListChangedCallback<T>
    private var clickHandler: ClickHandler<T>? = null
    private var chieldClickHandler: RecuclerChildClickHandler<T>? = null
    private var checkChangedListner: CheckedChangeListner<T>? = null

    var parentPosition: String? = null


    init {
        this.onListChangedCallback = WeakReferenceOnListChangedCallback(this)
        setItems(items)
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        if (items != null) {
            items!!.removeOnListChangedCallback(onListChangedCallback)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerViewAdapter.ViewHolder {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.context)
        }
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater!!, viewType, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items!![position]
        holder.binding.setVariable(itemBinder.getBindingVariable(item), item)
        if (chieldClickHandler != null) {
            holder.binding.setVariable(BR.position, position)
            holder.binding.setVariable(BR.chieldClickHandler, chieldClickHandler)
            //            viewHolder.binding.setVariable(itemBinder.getBindingVariable(item), position);
            //            viewHolder.binding.setVariable(itemBinder.getBindingVariable(item), chieldClickHandler);
        }
        if (checkChangedListner != null) {
            holder.binding.setVariable(BR.checkedChangListner, checkChangedListner)
        }
        holder.binding.root.setTag(ITEM_MODEL, item)
        holder.binding.root.setOnClickListener(this)
        holder.binding.executePendingBindings()
    }

    fun setItems(items: Collection<T>?) {
        if (this.items === items) {
            return
        }

        if (this.items != null) {
            this.items!!.removeOnListChangedCallback(onListChangedCallback)
            notifyItemRangeRemoved(0, this.items!!.size)
        }

        if (items is ObservableList<*>) {
            this.items = items as ObservableList<T>?
            notifyItemRangeInserted(0, this.items!!.size)
            this.items!!.addOnListChangedCallback(onListChangedCallback)
        } else if (items != null) {
            this.items = ObservableArrayList()
            this.items!!.addOnListChangedCallback(onListChangedCallback)
            this.items!!.addAll(items)
            notifyDataSetChanged()
        } else {
            this.items = null
        }
    }


    override fun getItemCount(): Int {
        return if (items == null) 0 else items!!.size
    }

    override fun getItemViewType(position: Int): Int {
        return itemBinder.getLayoutRes(items!![position])
    }

    class ViewHolder internal constructor(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onClick(v: View) {

        if (clickHandler != null) {
            val item = v.getTag(ITEM_MODEL) as T
            clickHandler!!.onClick(item, v)
        }
    }


    fun setClickHandler(clickHandler: ClickHandler<T>) {
        this.clickHandler = clickHandler
    }


    fun setChieldClickHandler(chieldClickHandler: RecuclerChildClickHandler<T>) {
        this.chieldClickHandler = chieldClickHandler
    }

    fun setChangedCheckListner(checkedChangeListener: CheckedChangeListner<T>) {
        this.checkChangedListner = checkedChangeListener
    }


    private class WeakReferenceOnListChangedCallback<T>(bindingRecyclerViewAdapter: RecyclerViewAdapter<T>) :
        ObservableList.OnListChangedCallback<ObservableList<T>>() {


        private val adapterReference: WeakReference<RecyclerViewAdapter<T>>

        init {
            this.adapterReference = WeakReference(bindingRecyclerViewAdapter)
        }

        override fun onChanged(sender: ObservableList<T>) {
            val adapter = adapterReference.get()
            adapter?.notifyDataSetChanged()
        }

        override fun onItemRangeChanged(
            sender: ObservableList<T>,
            positionStart: Int,
            itemCount: Int
        ) {
            val adapter = adapterReference.get()
            adapter?.notifyItemRangeChanged(positionStart, itemCount)
        }

        override fun onItemRangeInserted(
            sender: ObservableList<T>,
            positionStart: Int,
            itemCount: Int
        ) {
            val adapter = adapterReference.get()
            adapter?.notifyItemRangeInserted(positionStart, itemCount)
        }

        override fun onItemRangeMoved(
            sender: ObservableList<T>,
            fromPosition: Int,
            toPosition: Int,
            itemCount: Int
        ) {
            val adapter = adapterReference.get()
            adapter?.notifyItemMoved(fromPosition, toPosition)
        }

        override fun onItemRangeRemoved(
            sender: ObservableList<T>,
            positionStart: Int,
            itemCount: Int
        ) {
            val adapter = adapterReference.get()
            adapter?.notifyItemRangeRemoved(positionStart, itemCount)
        }
    }

    companion object {
        private val ITEM_MODEL = -115
        private val ITEM_HEADER = -116
    }

}



