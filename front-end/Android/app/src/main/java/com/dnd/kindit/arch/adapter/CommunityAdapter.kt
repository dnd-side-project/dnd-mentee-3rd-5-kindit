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
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.dnd.kindit.R
import com.dnd.kindit.arch.model.CommunityItem
import com.dnd.kindit.util.CommonUtils
import com.dnd.kindit.arch.view.CommunityDetailActivity
import com.google.android.material.card.MaterialCardView

class CommunityAdapter(val context: Context, val items: ArrayList<CommunityItem>) : RecyclerView.Adapter<CommunityAdapter.CommunityViewHolder>() {

    inner class CommunityViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var cciTvNickname = itemView.findViewById(R.id.cci_tv_nickname) as TextView
        var cciTvDate = itemView.findViewById(R.id.cci_tv_date) as TextView
        var cciTvContent = itemView.findViewById(R.id.cci_tv_content) as TextView
        var cciTvLikeCount = itemView.findViewById(R.id.cci_tv_like) as TextView
        var cciTvReply = itemView.findViewById(R.id.cci_tv_reply) as TextView
        var cciTvViewCount = itemView.findViewById(R.id.cci_tv_view_count) as TextView
        var cciImgMenuPic = itemView.findViewById(R.id.cci_img_menu) as ImageView
        var cciItem = itemView.findViewById(R.id.cci_item) as MaterialCardView

        fun bind(communityItem: CommunityItem, context: Context) {
            val requestOptions = RequestOptions().transform(CenterCrop(), RoundedCorners(16))
            Glide.with(context).load(CommonUtils.BASE_URL + communityItem.menuPic).apply(requestOptions).into(cciImgMenuPic)
            cciTvNickname.text = "${communityItem.nickname}님"
            cciTvDate.text = communityItem.date
            cciTvContent.text = communityItem.content
            cciTvLikeCount.text = "좋아요${communityItem.lickCount}"
            cciTvReply.text = "댓글${communityItem.replyCount}"
            cciTvViewCount.text = "조회수${communityItem.viewCount}"

            cciItem.setOnClickListener {
                val intent = Intent(context, CommunityDetailActivity::class.java)
                intent.putExtra("id", communityItem.id)
                intent.flags  = Intent.FLAG_ACTIVITY_NEW_TASK
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

    override fun onBindViewHolder(holder: CommunityViewHolder, position: Int) {
        holder.bind(items[position], context)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommunityViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_community_item, parent, false)
        return CommunityViewHolder(view)
    }
}