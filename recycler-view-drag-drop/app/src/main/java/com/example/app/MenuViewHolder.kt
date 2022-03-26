package com.example.app

import androidx.recyclerview.widget.RecyclerView
import com.example.app.databinding.MenuItemBinding


class MenuViewHolder(private val binding: MenuItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(menu: Menu) {
        binding.menu = menu
    }
}
