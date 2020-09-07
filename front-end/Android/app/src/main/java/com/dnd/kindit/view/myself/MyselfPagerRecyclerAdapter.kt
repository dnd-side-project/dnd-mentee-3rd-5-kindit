package com.dnd.kindit.view.myself
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dnd.kindit.R
import com.google.android.material.card.MaterialCardView

class MyselfPagerRecyclerAdapter(private val context: Context,private val items: List<String>) : RecyclerView.Adapter<PagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder =
        PagerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.card_brand, parent, false)
        )

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {

//        if (position != Integer.parseInt(items[position])) holder.card_myself.alpha = 0.4F
        val imageId = context.resources.getIdentifier("logo_$position", "drawable", context.packageName)
        Glide.with(context).load(imageId).fitCenter().into(holder.iv_logo)
        holder.tv_brandName.text = items[position]
    }

    override fun getItemCount(): Int = items.size
}

class PagerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var tv_brandName = itemView.findViewById<View>(R.id.tv_myself_brand) as TextView
    var iv_logo = itemView.findViewById<View>(R.id.iv_myself_logo) as ImageView
    var card_myself = itemView.findViewById<View>(R.id.card_myself) as MaterialCardView
}

