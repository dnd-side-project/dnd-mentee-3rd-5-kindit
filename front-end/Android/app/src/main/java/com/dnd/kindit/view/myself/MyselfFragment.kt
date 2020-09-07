package com.dnd.kindit.view.myself

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.dnd.kindit.R
import com.dnd.kindit.view.account.SignUpActivity
import kotlinx.android.synthetic.main.fragment_myself.*
import kotlin.math.abs

class MyselfFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_myself,container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val items: List<String> = listOf<String>("공차", "서브웨이","스타벅스", "베스킨 라빈스")
        pager.adapter = MyselfPagerRecyclerAdapter(
            context!!,
            items
        )
        pager.offscreenPageLimit = 4
        pager.setCurrentItem(1, false)
        pager.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        indicator.setViewPager(pager)
        indicator.createIndicators(4, 1)

        CompositePageTransformer().let{
            it.addTransformer(MarginPageTransformer(0))
            it.addTransformer { view: View, position: Float ->
                val r = 1 - abs(position)
                view.scaleY = 0.95f + r * 0.05f
            }
            pager.setPageTransformer(it)
        }

        btn_my_next.setOnClickListener {
            startActivity(Intent(context!!, MyselfMenuActivity::class.java))
        }
    }
}