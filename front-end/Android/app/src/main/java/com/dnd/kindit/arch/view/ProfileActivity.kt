package com.dnd.kindit.arch.view

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.dnd.kindit.R
import com.dnd.kindit.arch.model.SearchItem
import com.dnd.kindit.arch.viewmodel.ProfileViewModel
import com.dnd.kindit.arch.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_profile.*

class ProfileActivity : AppCompatActivity() {

    private val TAG = this.javaClass.toString()

    private lateinit var profileViewModel: ProfileViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        init()

        initViewModelFun()

        getData()

        initListener()
    }

    private fun init() {
        // 여기서 뷰 모델 연결
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel::class.java)
    }

    private fun initViewModelFun() {
        profileViewModel.user.observe(this, Observer {
            pf_edt_nickname.setText(it.user.nickname)
        })
    }

    private fun getData() {
        profileViewModel.getProfile(this)
    }

    private fun initListener() {

    }
}
