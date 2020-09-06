package com.dnd.kindit.arch.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd.kindit.R
import com.dnd.kindit.arch.model.SearchItem
import com.dnd.kindit.arch.view.CustomDetailsActivity
import com.dnd.kindit.util.CommonUtils
import com.google.android.material.card.MaterialCardView

class SearchAdapter(val context: Context, val items: ArrayList<SearchItem>) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var fsiIvPic = itemView.findViewById(R.id.fsi_iv_pic) as ImageView
        var fsiTvName = itemView.findViewById(R.id.fsi_tv_name) as TextView
        var fsiTvViewCount = itemView.findViewById(R.id.fsi_tv_viewCount) as TextView
        var fsiTvUserName = itemView.findViewById(R.id.fsi_tv_userName) as TextView
        var fsiRatingBar = itemView.findViewById(R.id.fsi_rb) as RatingBar
        var fsiItem = itemView.findViewById(R.id.fsi_item) as MaterialCardView

        fun bind(searchItem: SearchItem, context: Context) {
            Glide.with(context).load(CommonUtils.BASE_URL + searchItem.imgPic).into(fsiIvPic)
            fsiTvName.text = searchItem.name
            fsiTvViewCount.text = "${searchItem.viewCount}회"
            fsiTvUserName.text = "by ${searchItem.userName}"
            fsiRatingBar.rating = searchItem.starCount

            fsiItem.setOnClickListener {
                val intent = Intent(context, CustomDetailsActivity::class.java)
                intent.putExtra("id", searchItem.id)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(intent)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(items[position], context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_search, parent, false)
        return SearchViewHolder(view)
    }

    public fun getTagsList(): List<SearchItem> {
        return items
    }
}