package com.dnd.kindit.arch.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dnd.kindit.R
import com.dnd.kindit.arch.adapter.CustomDetailOptionAdapter
import com.dnd.kindit.arch.adapter.CustomDetailTagAdapter
import com.dnd.kindit.arch.model.CustomDetailOptionItem
import com.dnd.kindit.arch.model.CustomDetailTagItem
import com.dnd.kindit.arch.viewmodel.CustomDetailsViewModel
import com.dnd.kindit.arch.viewmodel.SearchViewModel
import com.dnd.kindit.util.CommonUtils
import kotlinx.android.synthetic.main.activity_custom_details.*

class CustomDetailsActivity : AppCompatActivity() {
    private val TAG = this.javaClass.toString()

    private var menuId : Int? = 0
    private lateinit var optionLayout: LinearLayoutManager
    private lateinit var optionList: ArrayList<CustomDetailOptionItem>
    private lateinit var tagLayout: GridLayoutManager
    private lateinit var tagList: ArrayList<CustomDetailTagItem>
    private lateinit var customDetailsViewModel: CustomDetailsViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_details)

        init()

        initViewModelFun()

        getData()

        initListener()
    }

    private fun init() {
        menuId = intent.extras?.getInt("id")

        optionLayout = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        cd_rcv_options.layoutManager = optionLayout

        tagLayout = GridLayoutManager(this, 4)
        cd_rcv_tags.layoutManager = tagLayout

        customDetailsViewModel = ViewModelProviders.of(this).get(CustomDetailsViewModel::class.java)
        customDetailsViewModel.getMenuDetail(this, menuId!!)
    }

    private fun initViewModelFun() {
        customDetailsViewModel.detailMenu.observe(this, Observer {
            Glide.with(this).load(CommonUtils.BASE_URL + it.data.uploadImage).into(cd_img_menu)
            cd_tv_title.text = it.data.title
            cd_tv_writer.text = "by ${it.data.writer}"
            cd_tv_price.text = "${it.data.price}원"
            cd_tv_tip.text = it.data.tip
            cd_rating_bar.rating = it.data.rating

            optionList = ArrayList()
            tagList = ArrayList()

            for(item in it.data.ingredient){
                optionList.add(CustomDetailOptionItem(item, item))
            }
            for(item in it.data.tags){
                tagList.add(CustomDetailTagItem(item))
            }
            cd_rcv_options.adapter = CustomDetailOptionAdapter(this, optionList)
            cd_rcv_tags.adapter = CustomDetailTagAdapter(this, tagList)
        })
    }

    private fun getData() {
    }

    private fun initListener() {
        cd_img_back.setOnClickListener {
            finish()
        }

        ccd_img_eval.setOnClickListener {
            Toast.makeText(this, "불편을 드려서 죄송합니다.\n곧 여러분을 찾아 뵙겠습니다!", Toast.LENGTH_LONG).show()
        }
    }
}