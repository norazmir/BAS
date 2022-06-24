package com.norazmir.bas.ui.login

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.norazmir.bas.databinding.ActivitySplashBinding
import com.norazmir.bas.ui.barcode.ScanBarcodeActivity

//
// Created by Azmir on 23/6/2022.
//
class SplashActivity : AppCompatActivity(){

    private lateinit var binding: ActivitySplashBinding
    private val operator = "OPERATOR"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignOn.setOnClickListener {
            Intent(this, ScanBarcodeActivity::class.java).also {
                it.putExtra("DATA", "hello")
                it.putExtra(operator, true)
                startActivity(it)
            }
        }
    }

    override fun onStart() {
        Log.d(TAG, "onStart: ")
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}