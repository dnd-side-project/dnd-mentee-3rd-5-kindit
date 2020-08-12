package com.dnd.kindit.main
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.dnd.kindit.R
import com.dnd.kindit.view.PagerRecyclerAdapter
import kotlinx.android.synthetic.main.card_bookmark.view.*
import kotlinx.android.synthetic.main.fragment_main.view.*
import kotlinx.android.synthetic.main.layout_hamburger.*
import me.relex.circleindicator.CircleIndicator3


class MainFragment : Fragment() {

    private lateinit var layoutBookmark : LinearLayout
    private lateinit var layoutHamburger : DrawerLayout
    private lateinit var mIndicator : CircleIndicator3
    private var mDrawerToggle : ActionBarDrawerToggle? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val homeView: View = inflater.inflate(R.layout.fragment_main,container,false)

//        layoutHamburger = homeView.findViewById(R.id.drawer_layout)
        layoutBookmark = homeView.findViewById(R.id.layout_bookmark)
        setbookmarkContents()

//        mDrawerToggle = ActionBarDrawerToggle(activity, layoutHamburger, toolbar, R.string.open, R.string.close)
//        mDrawerToggle!!.syncState()

        val items = listOf(Color.red(10),Color.blue(20),Color.green(20))
        homeView.pager.adapter = PagerRecyclerAdapter(items)
        homeView.pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        mIndicator = homeView.findViewById(R.id.indicator)
        mIndicator.setViewPager(homeView.pager)
        mIndicator.createIndicators(3,0)

        return homeView
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun setbookmarkContents(){
        val cardView : View = layoutInflater.inflate(R.layout.card_bookmark,null)

        cardView.iv_bookmark.setImageResource(R.drawable.sample_sandwitch)
        layoutBookmark.addView(cardView)
    }

    fun update(view : View){
        tv_content.text = (view as TextView).text
    }
}