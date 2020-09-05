package com.dnd.kindit.arch.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dnd.kindit.MainActivity
import com.dnd.kindit.R
import com.dnd.kindit.arch.adapter.FavoriteInitTagAdapter
import com.dnd.kindit.arch.model.FavoriteTag
import com.dnd.kindit.arch.model.SearchItem
import com.dnd.kindit.arch.viewmodel.FavoriteIntiTagViewModel
import com.dnd.kindit.arch.viewmodel.ProfileViewModel
import com.dnd.kindit.util.PreferenceManager
import com.dnd.kindit.view.account.KindItLoginActivity
import kotlinx.android.synthetic.main.activity_favorite_init_tag.*

class FavoriteInitTagActivity : AppCompatActivity() {
    private val TAG = this.javaClass.toString()

    private lateinit var layout: GridLayoutManager
    private lateinit var list: ArrayList<FavoriteTag>
    private lateinit var favoriteIntiTagViewModel: FavoriteIntiTagViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite_init_tag)

        init()

        initViewModelFun()

        getData()

        initListener()
    }

    private fun initViewModelFun() {
        favoriteIntiTagViewModel.favoriteTags.observe(this, Observer {
            list = ArrayList()
            for(item in it.data){
                list.add(FavoriteTag(id = item.id, tag = item.name, isSelected = false))
            }
            fit_rcv_tags.adapter = FavoriteInitTagAdapter(list)
        })
        
        favoriteIntiTagViewModel.successInsertTags.observe(this, Observer { 
            if(it){
                Toast.makeText(this, "정상적으로 등록 되었습니다.", Toast.LENGTH_SHORT).show()
                startActivity(Intent(applicationContext, MainActivity::class.java))
                PreferenceManager.setBoolean(applicationContext, "fav_tag_check", true)
                finish()
            }else{
                Toast.makeText(this, "등록 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getData() {

    }

    private fun init() {
        layout = GridLayoutManager(this, 3)
        fit_rcv_tags.layoutManager = layout

        favoriteIntiTagViewModel = ViewModelProviders.of(this).get(FavoriteIntiTagViewModel::class.java)

        favoriteIntiTagViewModel.getTags(this)
    }

    private fun initListener() {
        // 백버튼
        fit_img_back.setOnClickListener {
            startActivity(Intent(applicationContext, KindItLoginActivity::class.java))
            finish()
        }
        // 건너뛰기
        fit_tv_skip.setOnClickListener {
            startActivity(Intent(applicationContext, MainActivity::class.java))
            PreferenceManager.setBoolean(applicationContext, "fav_tag_check", true)
            finish()
        }

        // 확인 및 MainPage로 넘어가기
        fit_btn_ok.setOnClickListener {
            favoriteIntiTagViewModel.insertTags(applicationContext, list)
        }
    }
}