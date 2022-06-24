package com.norazmir.bas.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.norazmir.bas.databinding.ActivityMainBinding
import com.norazmir.bas.ui.barcode.ScanBarcodeActivity

//
// Created by Azmir on 23/6/2022.
//
class MainActivity : AppCompatActivity() {
    private val operator = "OPERATOR"
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnScan.setOnClickListener {
            Intent(this@MainActivity, ScanBarcodeActivity::class.java).also {
                it.putExtra(operator, false)
                startActivity(it)
            }
        }

        binding.btnNextStop.setOnClickListener {
            // defined later
        }

        binding.btnSchedules.setOnClickListener {
            // defined later
        }
    }
}