package com.dnd.kindit.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dnd.kindit.R

class PagerRecyclerAdapter(private val items: List<Int>) : RecyclerView.Adapter<PagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder =
        PagerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_banner, parent, false))

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
    }

    override fun getItemCount(): Int = items.size
}

class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

}