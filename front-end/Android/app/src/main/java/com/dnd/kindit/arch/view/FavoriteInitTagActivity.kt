package com.dnd.kindit.arch.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dnd.kindit.R
import com.dnd.kindit.arch.adapter.FavoriteInitTagAdapter
import com.dnd.kindit.arch.model.FavoriteTag
import com.dnd.kindit.arch.viewmodel.FavoriteIntiTagViewModel
import kotlinx.android.synthetic.main.activity_favorite_init_tag.*

class FavoriteInitTagActivity : AppCompatActivity() {
    private lateinit var list: ArrayList<FavoriteTag>
    private lateinit var favoriteIntiTagViewModel: FavoriteIntiTagViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_init_tag)

        val layout = GridLayoutManager(this, 3)
        fit_rcv_tags.layoutManager = layout

       list = ArrayList()
        for (i in 0..22) {
            list.add(FavoriteTag("test$i", false))
        }
        fit_rcv_tags.adapter = FavoriteInitTagAdapter(list)

        init()

        initListener()
    }

    private fun init() {

    }

    private fun initListener() {
        // 백버튼
        fit_img_back.setOnClickListener {
            finish()
        }
        // 건너뛰기
        fit_tv_skip.setOnClickListener {
            finish()
        }

        // 확인
        fit_btn_ok.setOnClickListener {
            for(fav in list){
                if(fav.isSelected)
                    Log.d("test", fav.toString())
            }
        }
    }
}