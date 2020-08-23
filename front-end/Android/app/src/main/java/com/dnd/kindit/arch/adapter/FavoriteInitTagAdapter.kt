package com.dnd.kindit.arch.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dnd.kindit.R
import com.dnd.kindit.arch.model.FavoriteTag


class FavoriteInitTagAdapter(items: ArrayList<FavoriteTag>) :
    RecyclerView.Adapter<FavoriteInitTagAdapter.FavoriteInitTagViewHolder>() {
    private var items: List<FavoriteTag> = items

    class FavoriteInitTagViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tag: TextView = view.findViewById<View>(R.id.ct_tv_item) as TextView
        var isSelected: Boolean = false
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteInitTagViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_tags, parent, false)
        return FavoriteInitTagViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: FavoriteInitTagViewHolder, position: Int) {
        holder.tag.text = items[position].tag
        holder.isSelected = items[position].isSelected

        holder.tag.setOnClickListener {
            if (holder.isSelected) {
                items[position].isSelected = false
                holder.tag.setBackgroundColor(Color.WHITE)
                holder.isSelected = false
            } else {
                items[position].isSelected = true
                holder.tag.setBackgroundColor(Color.YELLOW)
                holder.isSelected = true
            }
        }
    }

    public fun getTagsList(): List<FavoriteTag> {
        return items
    }
}