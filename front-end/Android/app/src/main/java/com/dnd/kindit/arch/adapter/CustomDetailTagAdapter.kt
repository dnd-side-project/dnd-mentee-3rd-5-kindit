package com.dnd.kindit.arch.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dnd.kindit.R
import com.dnd.kindit.arch.model.CustomDetailTagItem

class CustomDetailTagAdapter(val context: Context, val items: ArrayList<CustomDetailTagItem>) : RecyclerView.Adapter<CustomDetailTagAdapter.CustomDetailTagViewHolder>() {

    inner class CustomDetailTagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ccdTagName = itemView.findViewById(R.id.ccd_tv_tag) as TextView

        fun bind(item: CustomDetailTagItem, context: Context) {
            ccdTagName.text = item.tagName
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CustomDetailTagViewHolder, position: Int) {
        holder.bind(items[position], context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomDetailTagViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_custom_detail_tag, parent, false)
        return CustomDetailTagViewHolder(view)
    }

    public fun getTagsList(): List<CustomDetailTagItem> {
        return items
    }
}