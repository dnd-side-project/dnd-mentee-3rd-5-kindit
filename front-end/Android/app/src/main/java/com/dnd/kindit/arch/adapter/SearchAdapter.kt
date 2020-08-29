package com.dnd.kindit.arch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dnd.kindit.R
import com.dnd.kindit.arch.model.SearchItem

class SearchAdapter(items: ArrayList<SearchItem>): RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(){

    class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_tags, parent, false)
        return FavoriteInitTagAdapter.FavoriteInitTagViewHolder(view)
    }

    override fun getItemCount(): Int {

    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {

    }

}