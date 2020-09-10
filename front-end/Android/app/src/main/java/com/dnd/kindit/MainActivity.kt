package com.dnd.kindit

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.dnd.kindit.arch.view.CommunityFragment
import com.dnd.kindit.arch.view.ProfileActivity
import com.dnd.kindit.arch.view.SearchFragment
import com.dnd.kindit.main.MainFragment
import com.dnd.kindit.retrofit.RetrofitClient
import com.dnd.kindit.retrofit.domain.response.CommonResponse
import com.dnd.kindit.retrofit.domain.response.UserResponse
import com.dnd.kindit.util.PreferenceManager
import com.dnd.kindit.view.encyclopedia.EncyclopediaFragment
import com.dnd.kindit.view.myself.MyselfFragment
import com.google.android.material.navigation.NavigationView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.header.*
import kotlinx.android.synthetic.main.header.view.*
import retrofit2.Call
import retrofit2.Response


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

        headerView.menu_close.setOnClickListener {
            drawer_layout.closeDrawer(GravityCompat.START)
        }

        // 프로필 수정하기 위한 페이지로 이동 header 클릭시 이동
        headerView.ibtn_modify.setOnClickListener {
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

        setData()
    }

    private fun setData() {
        val token = PreferenceManager.getString(this, "kindit_token").toString()

        val profileService = RetrofitClient.kindItAccountService()
        val profileCall = profileService.getUserProfile(token)

        profileCall.enqueue(object : retrofit2.Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody?.result == "success") {
                        tv_profileName?.text = "${responseBody.user.nickname}님"
                    }
                } else {
                    val responseErrorBody = GsonBuilder().create()
                        .fromJson(response.errorBody()?.string(), CommonResponse::class.java)
                    Log.d(TAG, responseErrorBody.toString())
                }
            }
        })
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else super.onBackPressed()
    }

    //drawable 클릭이벤트
    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.menu_bookmark -> Snackbar.make(contentContainer, "제작중인 서비스입니다 TT", Snackbar.LENGTH_SHORT).setAction("확인"){
            }.show()
            R.id.menu_settings -> Snackbar.make(contentContainer, "제작중인 서비스입니다 TT", Snackbar.LENGTH_SHORT).setAction("확인"){
            }.show()
            R.id.menu_write -> Snackbar.make(contentContainer, "제작중인 서비스입니다 TT", Snackbar.LENGTH_SHORT).setAction("확인"){
            }.show()
            R.id.menu_recipe -> Snackbar.make(contentContainer, "제작중인 서비스입니다 TT", Snackbar.LENGTH_SHORT).setAction("확인"){
            }.show()
            R.id.menu_logout ->
                finish()
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return super.onOptionsItemSelected(item)
    }
}