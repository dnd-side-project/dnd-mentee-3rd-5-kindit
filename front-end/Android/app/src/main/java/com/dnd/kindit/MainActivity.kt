package com.dnd.kindit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dnd.kindit.config.CommunityFragment
import com.dnd.kindit.main.MainFragment
import com.dnd.kindit.main.MyselfFragment
import com.dnd.kindit.recommend.EncyclopediaFragment
import com.dnd.kindit.search.SearchFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //첫화면 지정
        MainFragment().let {
            supportFragmentManager.beginTransaction().add(R.id.contentContainer, it).commitAllowingStateLoss()
        }

        bottomNavigation.setOnNavigationItemSelectedListener { item ->

            val myselfFragment = MyselfFragment()
            val encyclopediaFragment = EncyclopediaFragment()
            val mainFragment = MainFragment()
            val searchFragment = SearchFragment()
            val communityFragment = CommunityFragment()
            val transaction = supportFragmentManager.beginTransaction()

            when(item.itemId){
                R.id.myselfItem -> transaction.replace(R.id.contentContainer, myselfFragment).commitAllowingStateLoss()
                R.id.encyclopediaItem -> transaction.replace(R.id.contentContainer, encyclopediaFragment).commitAllowingStateLoss()
                R.id.homeItem -> transaction.replace(R.id.contentContainer,mainFragment).commitAllowingStateLoss()
                R.id.searchItem -> transaction.replace(R.id.contentContainer, searchFragment).commitAllowingStateLoss()
                R.id.communityItem -> transaction.replace(R.id.contentContainer, communityFragment).commitAllowingStateLoss()
            }
            return@setOnNavigationItemSelectedListener true
        }


    }
}