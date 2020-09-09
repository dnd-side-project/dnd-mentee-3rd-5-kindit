package com.dnd.kindit.arch.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.dnd.kindit.R
import com.dnd.kindit.arch.viewmodel.CommunityDetailViewModel
import com.dnd.kindit.arch.viewmodel.CustomDetailsViewModel
import com.dnd.kindit.util.CommonUtils
import kotlinx.android.synthetic.main.activity_community_detail.*

class CommunityDetailActivity : AppCompatActivity() {
    private val TAG = this.javaClass.toString()

    private var communityId : Int? = 0

    private lateinit var communityDetailViewModel: CommunityDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_community_detail)

        init()

        initViewModelFun()

        getData()

        initListener()
    }

    private fun init() {
        communityId = intent.extras?.getInt("id")

        communityDetailViewModel = ViewModelProviders.of(this).get(CommunityDetailViewModel::class.java)
        communityDetailViewModel.getCommunityDetail(this, communityId!!)
    }

    private fun initViewModelFun() {
        communityDetailViewModel.detailCommunity.observe(this, Observer {
            Glide.with(this).load(CommonUtils.BASE_URL + it.data.uploadImage).into(ccd_img_menu)
            ccd_tv_nickname.text = "${it.data.writer}님"
            ccd_tv_date.text = it.data.createdDate
            ccd_tv_content.text = it.data.content
            ccd_tv_like.text = "좋아요${it.data.likesCount}"
            ccd_tv_reply.text = "댓글${it.data.comments}"
            ccd_tv_view_count.text = "조회수${it.data.hits}"
        })
    }

    private fun getData() {

    }

    private fun initListener() {
        cmd_img_back.setOnClickListener {
            finish()
        }
    }
}