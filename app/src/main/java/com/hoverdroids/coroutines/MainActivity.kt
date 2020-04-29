package com.hoverdroids.coroutines

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    //ADD TO MANIFEST!
    fun onClickExample1(view: View) = startActivity(Intent(this, Example1::class.java))
    fun onClickExample2(view: View) = startActivity(Intent(this, Example2::class.java))
}
