package com.dnd.kindit.arch.adapter

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dnd.kindit.arch.model.SearchItem

class SearchAdapter(items: ArrayList<SearchItem>): RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(){

    class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    }


    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

}