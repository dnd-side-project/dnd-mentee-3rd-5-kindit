package com.dnd.kindit.view.myself

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.dnd.kindit.R
import kotlinx.android.synthetic.main.fragment_myself_menu_object.view.*

class MyselfObjectFragment : Fragment() {

    private val ARG_OBJECT = "object"
    private var menuList = arrayListOf<String>("애그마요","햄","이탈리안 BMT","샐러드","애그마요","햄","이탈리안 BMT","샐러드")

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val objectView: View = inflater.inflate(R.layout.fragment_myself_menu_object, container, false)

        val layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        objectView.rv_myself_menu.let {
            it.layoutManager = layoutManager
            it.itemAnimator = DefaultItemAnimator()
            it.adapter = MyselfMenuListAdapter(activity!!,menuList)
        }
        return objectView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            Log.e(tag,getInt(ARG_OBJECT).toString())
        }
    }
}