package com.dnd.kindit.view.encyclopedia

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dnd.kindit.R
import kotlinx.android.synthetic.main.card_encyclopedia.view.*

class EncyclopediaListAdapter (private val EncyclopediaList: ArrayList<EncyclopediaCardList>) : RecyclerView.Adapter<EncyclopediaListAdapter.ViewHolder>() {

    override fun getItemCount(): Int = EncyclopediaList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val contents = EncyclopediaList[position]

        holder.tv_menuTitle.text = contents.menuTitle
        holder.tv_userName.text = contents.userName
        holder.tv_viewCount.text = contents.viewCount
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_encyclopedia, parent, false))

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tv_menuTitle: TextView = view.tv_menuTitle
        val tv_userName: TextView = view.tv_userName
        val tv_viewCount: TextView = view.tv_viewCount
    }
}
