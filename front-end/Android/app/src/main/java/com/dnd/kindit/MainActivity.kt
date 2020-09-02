package com.dnd.kindit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.dnd.kindit.arch.view.SearchFragment
import com.dnd.kindit.config.CommunityFragment
import com.dnd.kindit.main.MainFragment
import com.dnd.kindit.main.MyselfFragment
import com.dnd.kindit.arch.view.ProfileActivity
import com.dnd.kindit.view.encyclopedia.EncyclopediaFragment
import com.google.android.material.navigation.NavigationView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.header.view.*


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val TAG = this.javaClass.toString()
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //첫화면 지정
        MainFragment().let {
            supportFragmentManager.beginTransaction().add(R.id.contentContainer, it)
                .commitAllowingStateLoss()
        }

        val headerView = nav_view.getHeaderView(0)
        val profileName = headerView.findViewById(R.id.tv_profileName) as TextView
        profileName.text = "김페페님"

        headerView.menu_close.setOnClickListener {
            drawer_layout.closeDrawer(GravityCompat.START)
        }

        // 프로필 수정하기 위한 페이지로 이동 header 클릭시 이동
        headerView.layout_header.setOnClickListener {
            startActivity(Intent(this, ProfileActivity::class.java))
        }

        nav_view.setNavigationItemSelectedListener(this)
        nav_view.bringToFront()
        setSupportActionBar(toolbar)
        toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar, R.string.open, R.string.close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        bottomNavigation.setOnNavigationItemSelectedListener { item ->

            val myselfFragment = MyselfFragment()
            val encyclopediaFragment = EncyclopediaFragment()
            val mainFragment = MainFragment()
            val searchFragment = SearchFragment()
            val communityFragment = CommunityFragment()
            val transaction = supportFragmentManager.beginTransaction()

            when (item.itemId) {
                R.id.myselfItem -> transaction.replace(R.id.contentContainer, myselfFragment)
                    .commitAllowingStateLoss()
                R.id.encyclopediaItem -> transaction.replace(
                    R.id.contentContainer,
                    encyclopediaFragment
                ).commitAllowingStateLoss()
                R.id.homeItem -> transaction.replace(R.id.contentContainer, mainFragment)
                    .commitAllowingStateLoss()
                R.id.searchItem -> transaction.replace(R.id.contentContainer, searchFragment)
                    .commitAllowingStateLoss()
                R.id.communityItem -> transaction.replace(R.id.contentContainer, communityFragment)
                    .commitAllowingStateLoss()
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else super.onBackPressed()
    }

    //drawable 클릭이벤트
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_bookmark -> Log.e(TAG, "북마크")
            R.id.menu_settings -> Log.e(TAG, "설정")
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return super.onOptionsItemSelected(item)
    }
}