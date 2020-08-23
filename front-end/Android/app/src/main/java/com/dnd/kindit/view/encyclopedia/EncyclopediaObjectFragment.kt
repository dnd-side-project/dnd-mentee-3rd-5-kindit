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
    private var encyclopediaCardList = arrayListOf<EncyclopediaCardList>(
        EncyclopediaCardList("샌드위치1","김명석","1,200"),
        EncyclopediaCardList("샌드위치2","백경민","36"),
        EncyclopediaCardList("샌드위치3","당현아","2,000"),
        EncyclopediaCardList("샌드위치4","박현채","100"),
        EncyclopediaCardList("샌드위치5","장재우","0"),
        EncyclopediaCardList("샌드위치6","김씨","20"),
        EncyclopediaCardList("샌드위치7","박씨","30")
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        val encyclopediaListView: View = inflater.inflate(R.layout.fragment_encyclopedia_object, container, false)

        val layoutManager = GridLayoutManager(activity, 2, GridLayoutManager.VERTICAL, false)
        encyclopediaListView.rv_encyclopediaCard.let {
            it.layoutManager = layoutManager
            it.itemAnimator = DefaultItemAnimator()
            it.adapter = EncyclopediaListAdapter( encyclopediaCardList)
        }
        return  encyclopediaListView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        arguments?.takeIf { it.containsKey(ARG_OBJECT) }?.apply {
            Log.e(tag,getInt(ARG_OBJECT).toString())
        }
    }
}