package com.example.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.app.databinding.MenuItemBinding
import com.example.app.databinding.TailItemBinding

class MenuListWithTailAdapter(menus: List<Menu>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var items: List<MenuListItem> = mutableListOf(*menus.map { MenuListItem.Cell(it) }.toTypedArray(), MenuListItem.Tail)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (MenuListItem.ViewType.CELL.ordinal == viewType) {
            val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            MenuViewHolder(binding)
        } else {
            val binding = TailItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            TailViewHolder(binding)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MenuViewHolder) {
            (items[position] as? MenuListItem.Cell)?.menu?.let { holder.bind(it) }
        }
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType.ordinal
    }

    fun onItemMove(fromPosition: Int, toPosition: Int) {
        val newMenus = items.toMutableList().apply {
            val tempItem = this[toPosition]
            this[toPosition] = this[fromPosition]
            this[fromPosition] = tempItem
        }
        val diff = MenuListDiff(items, newMenus)
        val diffResult = DiffUtil.calculateDiff(diff)
        items = newMenus
        diffResult.dispatchUpdatesTo(this)
    }
}
