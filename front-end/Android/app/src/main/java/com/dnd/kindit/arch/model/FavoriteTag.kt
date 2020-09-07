package com.dnd.kindit.arch.model

class FavoriteTag(var id: Int, var tag: String, var isSelected: Boolean) {

    override fun toString(): String {
        return "FavoriteTag(id=$id, tag='$tag', isSelected=$isSelected)"
    }
}