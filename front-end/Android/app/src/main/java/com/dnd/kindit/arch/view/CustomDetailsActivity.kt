package com.dnd.kindit.arch.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dnd.kindit.R

class CustomDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_custom_details)

        Toast.makeText(this, "${intent.extras?.getInt("id")}", Toast.LENGTH_SHORT).show()
    }
}