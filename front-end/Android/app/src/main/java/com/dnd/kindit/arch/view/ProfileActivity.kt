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

        pf_img_back.setOnClickListener {
            finish()
        }
        pf_btn_save.setOnClickListener{
            profileViewModel.modifyProfile(this, pf_edt_nickname.text.toString())
        }
    }

    private fun initViewModelFun() {
        profileViewModel.user.observe(this, Observer {
            pf_edt_nickname.setText(it.user.nickname)
        })
        profileViewModel.check.observe(this, Observer {
            if(it){
                Toast.makeText(this, "성공적으로 변경 되었습니다.", Toast.LENGTH_SHORT).show()
                finish()
            }else{
                Toast.makeText(this, "회원 정보 수정 중 오류가 발생했습니다.", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun getData() {
        profileViewModel.getProfile(this)
    }

    private fun initListener() {

    }
}
