package com.dnd.kindit.arch.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dnd.kindit.R
import com.dnd.kindit.arch.model.SearchItem

class SearchAdapter(items: ArrayList<SearchItem>): RecyclerView.Adapter<SearchAdapter.SearchViewHolder>(){

    private var items : List<SearchItem> = items

    class SearchViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var fsiIvPic = view.findViewById<View>(R.id.fsi_iv_pic) as ImageView
        var fsiTvName = view.findViewById<View>(R.id.fsi_tv_name) as TextView
        var fsiTvViewCount = view.findViewById<View>(R.id.fsi_tv_viewCount) as TextView
        var fsiTvUserName = view.findViewById<View>(R.id.fsi_tv_userName) as TextView
        var fsiRatingBar = view.findViewById<View>(R.id.fsi_rb) as RatingBar
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        // Glide 적용 holder.fsiIvPic
        holder.fsiTvName.text = items[position].name
        holder.fsiTvViewCount.text = "${items[position].viewCount}회"
        holder.fsiTvUserName.text = "by ${items[position].userName}"
        holder.fsiRatingBar.rating = items[position].starCount.toFloat()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_search, parent, false)
        return SearchViewHolder(view)
    }

    public fun getTagsList(): List<SearchItem> {
        return items
    }
}