package com.example.app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.app.databinding.MenuItemBinding

class MenuListAdapter(menus: List<Menu>) : RecyclerView.Adapter<MenuViewHolder>() {
    private var _menus: List<MenuListItem.Cell> = menus.map { MenuListItem.Cell(it) }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MenuViewHolder {
        val binding = MenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MenuViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MenuViewHolder, position: Int) {
        holder.bind(_menus[position].menu)
    }

    override fun getItemViewType(position: Int): Int {
        return _menus[position].viewType.ordinal
    }

    override fun getItemCount(): Int = _menus.size

    fun onItemMove(fromPosition: Int, toPosition: Int) {
        val newMenus = _menus.toMutableList().apply {
            val tempItem = this[toPosition]
            this[toPosition] = this[fromPosition]
            this[fromPosition] = tempItem
        }
        val diff = MenuListDiff(_menus, newMenus)
        val diffResult = DiffUtil.calculateDiff(diff)
        _menus = newMenus
        diffResult.dispatchUpdatesTo(this)
    }
}
