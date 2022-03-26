package com.example.app


sealed class MenuListItem(val viewType: ViewType) {
    enum class ViewType {
        CELL,
        TAIL,
    }

    data class Cell(val menu: Menu) : MenuListItem(ViewType.CELL)
    object Tail : MenuListItem(ViewType.TAIL)
}