package co.cafecode.recycler_view_view_types

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.adapter_header.view.*
import kotlinx.android.synthetic.main.adapter_item.view.*


const val TYPE_HEADER = 0
const val TYPE_ITEM = 1

class ItemAdapter(private val items: Array<Any>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_header, parent, false)
                HeaderViewHolder(view)
            }
            TYPE_ITEM -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.adapter_item, parent, false)
                ItemViewHolder(view)
            }
            else -> {
                super.createViewHolder(parent, viewType)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position % 3 == 0) {
            TYPE_HEADER
        } else {
            TYPE_ITEM
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> {
                holder.itemView.header_title_text.text = "${items[position]}"
            }
            is ItemViewHolder -> {
                holder.itemView.item_title_text.text = "${items[position]}"
            }
        }
    }
}

class HeaderViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

}

class ItemViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {

}