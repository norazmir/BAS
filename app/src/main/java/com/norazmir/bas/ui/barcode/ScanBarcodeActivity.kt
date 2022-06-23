package com.norazmir.bas.ui.barcode

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.database.sqlite.SQLiteOpenHelper
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
import com.norazmir.bas.data.student.StudentDao
import com.norazmir.bas.ui.student.StudentDetailsActivity
import com.norazmir.bas.utils.LoadingDialog
import kotlinx.android.synthetic.main.activity_scan_barcode.*
import java.nio.file.attribute.AclEntry

private const val CAMERA_REQUEST_CODE = 101

@Suppress("DEPRECATION")
class ScanBarcodeActivity : AppCompatActivity() {

    private lateinit var codeScanner: CodeScanner
    var dbHandler:SQLiteHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_barcode)


        setupPersmissions()
        codeScanner()
    }

    private fun codeScanner() {
        val loading = LoadingDialog(this)
        var builder = AlertDialog.Builder(this)
        dbHandler = SQLiteHelper(this)


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
                    var studentID = tv_textview.text.toString()
                    loading.startLoading()
                    Handler(Looper.myLooper()!!).postDelayed({
                        loading.isDismiss()
                        if (studentID == "2016595951"){
                            builder.setTitle("Validation Result")
                                .setMessage("Student ID Validation success !")
                                .setPositiveButton("OK") { _, _ ->
                                    startActivity(
                                        Intent(
                                            applicationContext,
                                            StudentDetailsActivity::class.java
                                        )
                                    )
                                }.show()
                        } else{
                            builder.setTitle("Validation Result")
                                .setMessage("Invalid Student ID")
                                .setPositiveButton("OK") { _, _ ->

                                }.show()
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