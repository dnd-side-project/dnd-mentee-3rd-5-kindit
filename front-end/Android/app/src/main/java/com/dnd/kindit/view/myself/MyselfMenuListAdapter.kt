package com.dnd.kindit.view.myself

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.dnd.kindit.R
import kotlinx.android.synthetic.main.card_menus.view.*

class MyselfMenuListAdapter (private val context: Context, private val MyselfMenuList: ArrayList<String>) : RecyclerView.Adapter<MyselfMenuListAdapter.ViewHolder>() {

    var selectPosition : Int = -1
    override fun getItemCount(): Int = MyselfMenuList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tv_menuTitle.text = MyselfMenuList[position]

        holder.cv_menu.setOnClickListener {

            //실패.. 보류..
//            if(selectPosition != position){
//                holder.cv_menu.setBackgroundResource(R.drawable.card_view_border)

//              intent.putExtra("result_TireModel", resultTireModel)
                context.startActivity(Intent(context, MyselfUploadActivity::class.java))
//            }
            selectPosition = position
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_menus, parent, false))

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view){
        val tv_menuTitle: TextView = view.tv_menuTitle
        val cv_menu: CardView = view.cv_menu
    }


}
