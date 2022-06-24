package com.norazmir.bas.ui.barcode

import android.Manifest
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.*
import com.norazmir.bas.R
import com.norazmir.bas.data.student.SQLiteHelper
import com.norazmir.bas.databinding.ActivityScanBarcodeBinding
import com.norazmir.bas.databinding.ActivitySplashBinding
import com.norazmir.bas.ui.main.MainActivity
import com.norazmir.bas.ui.student.StudentDetailsActivity
import com.norazmir.bas.utils.LoadingDialog
import kotlinx.android.synthetic.main.activity_scan_barcode.*

private const val CAMERA_REQUEST_CODE = 101

@Suppress("DEPRECATION")
class ScanBarcodeActivity : AppCompatActivity() {

    private val TAG = "ScanBarcodeActivity"
    private lateinit var binding: ActivityScanBarcodeBinding
    private lateinit var codeScanner: CodeScanner
    private val operator = "OPERATOR"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBarcodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var isOperator = intent.getBooleanExtra(operator, false)
        setupPersmissions()
        codeScanner(isOperator)
    }

    private fun codeScanner(isOperator:Boolean) {
        val loading = LoadingDialog(this)
        var builder = AlertDialog.Builder(this)
        Log.d(TAG, "codeScanner isOperator: $isOperator" )

        codeScanner = CodeScanner(this, scanner_view)
        codeScanner.apply {
            camera = CodeScanner.CAMERA_BACK
            formats = CodeScanner.ALL_FORMATS

            autoFocusMode = AutoFocusMode.SAFE
            scanMode = ScanMode.SINGLE
            isAutoFocusEnabled = true
            isFlashEnabled = false

            decodeCallback = DecodeCallback {
                runOnUiThread {
                    tv_textview.text = it.text
                    var id = tv_textview.text.toString()
                    loading.startLoading()
                    Handler(Looper.myLooper()!!).postDelayed({
                        loading.isDismiss()
                        when {
                            isOperator -> {
                                when (id) {
                                    "2022595951" -> {
                                        builder.setTitle("Validation Result")
                                            .setMessage("Operator Sign On Success !")
                                            .setPositiveButton("OK") { _, _ ->
                                                startActivity(Intent(this@ScanBarcodeActivity, MainActivity::class.java))
                                            }
                                            .show()
                                    }
                                    else -> {
                                        builder.setTitle("Validation Result")
                                            .setMessage("Invalid Operator")
                                            .setPositiveButton("OK") { dialog, _ ->
                                                dialog.dismiss()
                                                codeScanner.startPreview()
                                            }
                                            .show()
                                    }
                                }
                            } else -> {
                                when (id) {
                                    "2022111111" -> {
                                        builder.setTitle("Validation Result")
                                            .setMessage("Student ID Validation success !")
                                            .setPositiveButton("OK") { _, _ ->
                                                startActivity(
                                                    Intent(
                                                        applicationContext,
                                                        StudentDetailsActivity::class.java
                                                    )
                                                )
                                                finish()
                                            }
                                            .show()
                                    } else -> {
                                        builder.setTitle("Validation Result")
                                            .setMessage("Invalid ID")
                                            .setPositiveButton("OK") { dialog, _ ->
                                                dialog.dismiss()
                                                codeScanner.startPreview()
                                            }
                                            .show()
                                    }
                                }
                            }
                        }
                    }, 2000)
                }
            }

            errorCallback = ErrorCallback {
                runOnUiThread {
                    Log.e("Main", "Camera initialization error: ${it.message}")
                }
            }

            scanner_view.setOnClickListener {
                codeScanner.startPreview()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        super.onPause()
        codeScanner.releaseResources()
    }


    private fun setupPersmissions() {
        val permission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.CAMERA
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            makeRequest()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.CAMERA),
            CAMERA_REQUEST_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (grantResults.isEmpty() || grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(
                        this,
                        "You need the camera permission to be able to use the app!",
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    //successful
                }
            }
        }
    }
}