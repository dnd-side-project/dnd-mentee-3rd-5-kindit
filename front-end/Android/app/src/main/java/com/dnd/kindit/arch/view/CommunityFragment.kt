package com.dnd.kindit.arch.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.dnd.kindit.R
import com.dnd.kindit.arch.adapter.CommunityAdapter
import com.dnd.kindit.arch.model.CommunityItem
import com.dnd.kindit.arch.viewmodel.CommunityViewModel
import com.dnd.kindit.arch.viewmodel.SearchViewModel
import com.dnd.kindit.view.community.CommunityWriteActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_community.*

class CommunityFragment : Fragment() {

    private val TAG = this.javaClass.toString()

    private lateinit var layout: GridLayoutManager
    private lateinit var list: ArrayList<CommunityItem>
    private lateinit var communityViewModel : CommunityViewModel

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
        layout = GridLayoutManager(context, 1)
        fc_rcv_items.layoutManager = layout

        communityViewModel = ViewModelProviders.of(this.activity!!).get(CommunityViewModel::class.java)
        communityViewModel.getCommunityList(this.context!!)
    }

    private fun initViewModelFun() {
        communityViewModel.communityItemList.observe(this, Observer {
            if(it.result == "success"){
                list = ArrayList()
                for(item in it.data){
                    list.add(CommunityItem(id = item.id, nickname = item.writer, content = item.content, menuPic = item.uploadImage, date = item.createdDate, lickCount = item.likesCount, replyCount = item.comments, viewCount = item.hits))
                }
                fc_rcv_items.adapter = CommunityAdapter(this.context!!.applicationContext, list)
            }else{
                Snackbar.make(fc_ll_main, it.message, Snackbar.LENGTH_SHORT).show()
            }
        })
    }

    private fun getData() {
    }

    private fun initListener() {
        fc_btn_write.setOnClickListener {
            startActivity(Intent(this.activity, CommunityWriteActivity::class.java))
        }
    }
}