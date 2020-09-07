package com.dnd.kindit.view.myself
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dnd.kindit.R
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_myself_menu.*

class MyselfMenuActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_myself_menu)

        pager.adapter = MyselfMenuAdapter(this)
        TabLayoutMediator(tabLayout, pager,
            TabLayoutMediator.TabConfigurationStrategy { tab, position ->
                when (position) {
                    0 -> { tab.text = "전체" }
                    1 -> { tab.text = "프리미엄" }
                    2 -> { tab.text = "베스트" }
                    3 -> { tab.text = "클래식" }
                    4 -> { tab.text = "샐러드" }
                }
            }).attach()

        iv_menu_close.setOnClickListener {
            finish()
        }
    }
}