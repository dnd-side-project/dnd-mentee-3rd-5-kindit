package com.dnd.kindit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.dnd.kindit.config.ConfigFragment
import com.dnd.kindit.main.MainFragment
import com.dnd.kindit.recommend.RecommendFragment
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

            val mainFragment = MainFragment()
            val searchFragment = SearchFragment()
            val recommendFragment = RecommendFragment()
            val configFragment = ConfigFragment()
            val transaction = supportFragmentManager.beginTransaction()

            when(item.itemId){
                R.id.mainItem -> transaction.replace(R.id.contentContainer,mainFragment).commitAllowingStateLoss()
                R.id.searchItem -> transaction.replace(R.id.contentContainer, searchFragment).commitAllowingStateLoss()
                R.id.recommendItem -> transaction.replace(R.id.contentContainer, recommendFragment).commitAllowingStateLoss()
                R.id.configItem -> transaction.replace(R.id.contentContainer, configFragment).commitAllowingStateLoss()
            }
            return@setOnNavigationItemSelectedListener true
        }


    }
}