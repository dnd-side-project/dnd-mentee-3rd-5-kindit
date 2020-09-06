package com.dnd.kindit.arch.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd.kindit.R
import com.dnd.kindit.arch.model.CustomDetailOptionItem
import com.dnd.kindit.arch.view.CustomDetailsActivity
import com.dnd.kindit.util.CommonUtils

class CustomDetailOptionAdapter(val context: Context, val items: ArrayList<CustomDetailOptionItem>) : RecyclerView.Adapter<CustomDetailOptionAdapter.CustomDetailOptionViewHolder>() {

    inner class CustomDetailOptionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ccdOptionPic = itemView.findViewById(R.id.ccd_img_menu) as ImageView
        var ccdOptionName = itemView.findViewById(R.id.ccd_tv_menu) as TextView

        fun bind(item: CustomDetailOptionItem, context: Context) {
            Glide.with(context).load(CommonUtils.BASE_URL + item.optionPic).into(ccdOptionPic)
            ccdOptionName.text = item.optionName
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: CustomDetailOptionViewHolder, position: Int) {
        holder.bind(items[position], context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomDetailOptionViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_custom_detail_option, parent, false)
        return CustomDetailOptionViewHolder(view)
    }

    public fun getTagsList(): List<CustomDetailOptionItem> {
        return items
    }
}