package com.cs4520.assignment4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cs4520.assignment4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //    var binding: ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}