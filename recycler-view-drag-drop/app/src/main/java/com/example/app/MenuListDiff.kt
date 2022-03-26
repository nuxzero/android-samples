package com.example.app

import androidx.recyclerview.widget.DiffUtil


class MenuListDiff(private val oldMenus: List<MenuListItem>, private val newMenus: List<MenuListItem>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldMenus.size
    }

    override fun getNewListSize(): Int {
        return newMenus.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldMenus[oldItemPosition] == newMenus[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return (oldMenus[oldItemPosition] as? MenuListItem.Cell)?.menu?.id == (newMenus[newItemPosition] as? MenuListItem.Cell)?.menu?.id
    }
}
