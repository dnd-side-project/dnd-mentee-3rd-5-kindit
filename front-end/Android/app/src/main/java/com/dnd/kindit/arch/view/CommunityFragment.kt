package com.dnd.kindit.arch.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dnd.kindit.MainActivity
import com.dnd.kindit.R
import com.dnd.kindit.view.community.CommunityWriteActivity
import kotlinx.android.synthetic.main.fragment_community.*

class CommunityFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_community,container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()

        initViewModelFun()

        getData()

        initListener()
    }

    private fun init() {
    }

    private fun initViewModelFun() {
    }

    private fun getData() {
    }

    private fun initListener() {
        fc_btn_write.setOnClickListener {
            startActivity(Intent(this.activity, CommunityWriteActivity::class.java))
        }
    }
}