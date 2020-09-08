package com.dnd.kindit.view.encyclopedia

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.dnd.kindit.R
import kotlinx.android.synthetic.main.fragment_encyclopedia_object.view.*

class EncyclopediaObjectFragment : Fragment() {

    private val ARG_OBJECT = "object"
    var EncyclopediaList = arrayListOf<EncyclopediaCardList>(
        EncyclopediaCardList("샌드위치1","김페페","1,200")
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val encyclopediaListView: View = inflater.inflate(R.layout.fragment_encyclopedia_object, container, false)

        val layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        encyclopediaListView.rv_encyclopediaCard.let {
            it.layoutManager = layoutManager
            it.itemAnimator = DefaultItemAnimator()
            it.adapter = EncyclopediaListAdapter(EncyclopediaList)
        }
        return  encyclopediaListView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            Log.e(tag,getInt(ARG_OBJECT).toString())
        }
    }
}