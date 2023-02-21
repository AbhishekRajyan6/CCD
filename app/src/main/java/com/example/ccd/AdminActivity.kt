package com.example.ccd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class AdminActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)
        val addProductFragment = AddProductFragment()

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.framelayout,addProductFragment)
            commit();
        }

    }
}