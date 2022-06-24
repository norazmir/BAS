package com.norazmir.bas.ui.student

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.norazmir.bas.R
import com.norazmir.bas.databinding.ActivityStudentDetailBinding
import com.norazmir.bas.ui.main.MainActivity

class StudentDetailsActivity : AppCompatActivity() {
    private lateinit var binding : ActivityStudentDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityStudentDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConfirm.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}