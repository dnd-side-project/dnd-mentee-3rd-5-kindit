package com.dnd.kindit.arch.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.dnd.kindit.R
import com.dnd.kindit.arch.model.SearchItem
import com.dnd.kindit.arch.viewmodel.SearchViewModel
import kotlinx.android.synthetic.main.fragment_search.*

class SearchFragment : Fragment() {
    private val TAG = this.javaClass.toString()

    private lateinit var list: ArrayList<SearchItem>
    private lateinit var searchViewModel : SearchViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_search,container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val layout = GridLayoutManager(context, 2)
        fs_rcv_items.layoutManager = layout

        list = ArrayList()
        for(i in 0..5){
            list.add(SearchItem("$i", i+0L, "$i", i+0L))
        }
    }
}