package com.dnd.kindit.arch.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.dnd.kindit.R
import com.dnd.kindit.arch.adapter.SearchAdapter
import com.dnd.kindit.arch.model.SearchItem
import com.dnd.kindit.arch.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {

    private val TAG = this.javaClass.toString()

    private lateinit var layout: GridLayoutManager
    private lateinit var list: ArrayList<SearchItem>
    private lateinit var searchViewModel : SearchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search,container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        init()

        initViewModelFun()

        getData()

        initListener()

    }

    private fun init() {
        layout = GridLayoutManager(context, 2)
        fs_rcv_items.layoutManager = layout

        searchViewModel = ViewModelProviders.of(this.activity!!).get(SearchViewModel::class.java)

        searchViewModel.getMenuList(this.context!!)
    }

    private fun initViewModelFun() {
        searchViewModel.searchItemList.observe(this, Observer {
            list = ArrayList()
            for(item in it.data){
                list.add(SearchItem(name=item.title, viewCount = item.hits, userName = item.writer, starCount = item.rating, imgPic = item.uploadImage))
            }
            fs_rcv_items.adapter = SearchAdapter(list)
            Toast.makeText(this.context, it.message, Toast.LENGTH_SHORT).show()
        })
    }

    private fun getData() {
    }

    private fun initListener() {
        fs_btn_search.setOnClickListener {
            searchViewModel.getMenuListBySearch(this.activity!!, fs_edt_search.text.toString())
        }
    }
}