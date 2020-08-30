package com.dnd.kindit.view.encyclopedia
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.dnd.kindit.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.fragment_encyclopedia.view.*

class EncyclopediaFragment : Fragment() {

    private lateinit var encyclopediaAdapter: EncyclopediaAdapter
    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val encyclopediaView: View = inflater.inflate(R.layout.fragment_encyclopedia, container, false)
        encyclopediaAdapter = EncyclopediaAdapter(this)
        viewPager = encyclopediaView.findViewById(R.id.pager)
        viewPager.adapter = encyclopediaAdapter

       TabLayoutMediator(encyclopediaView.tabLayout, viewPager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> { tab.text = "전체" }
                    1 -> { tab.text = "서브웨이" }
                    2 -> { tab.text = "스타벅스" }
                    3 -> { tab.text = "베스킨라빈스" }
                    4 -> { tab.text = "공차" }
                }
            }).attach()

        return encyclopediaView
    }
}
