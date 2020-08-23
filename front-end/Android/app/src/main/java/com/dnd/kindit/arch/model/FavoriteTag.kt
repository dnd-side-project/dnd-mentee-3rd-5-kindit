package com.dnd.kindit.arch.model

class FavoriteTag(var tag: String, var isSelected: Boolean) {

    override fun toString(): String {
        return "FavoriteTag(tag='$tag', isSelected=$isSelected)"
    }
}