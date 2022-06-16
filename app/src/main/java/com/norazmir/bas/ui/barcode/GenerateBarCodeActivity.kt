package com.norazmir.bas.ui.barcode

import android.content.ContentValues
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.norazmir.bas.R
import com.norazmir.bas.model.BarcodeGenerator
import com.norazmir.bas.model.BarcodeModel

class GenerateBarcodeActivity : AppCompatActivity() {
    private lateinit var barcodeTextEditText: EditText
    private lateinit var barcodeImageView: ImageView
    private lateinit var generateBarcodeButton: Button
    private lateinit var saveBarcodeButton: Button
    private var db: SQLiteDatabase? = null
//    private var dbHelper: DBHelper? = null
    private var values: ContentValues? = null
    private var bitmap: Bitmap? = null
    private var barcodeModel: BarcodeModel? = null
    private var barcodesValuesList: ArrayList<String>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_generate_barcode)
        values = ContentValues()
        barcodeTextEditText = findViewById(R.id.barcode_text_edit_text)
        barcodeImageView = findViewById(R.id.barcode_image_view)
        generateBarcodeButton = findViewById(R.id.generate_barcode_button)
        with(barcodeImageView) {
            this?.setMaxWidth(getWidth())
            this?.setMaxHeight(getHeight())
        }
        if (intent.extras != null) {
            if (intent.extras!!.getStringArrayList(resources.getString(R.string.barcodes_list_variable)) != null) {
                barcodesValuesList =
                    intent.extras!!.getStringArrayList(resources.getString(R.string.barcodes_list_variable))
            }
            if (intent.extras!!.getSerializable("barcodeModel") != null) {
                barcodeModel = intent.extras!!.getSerializable("barcodeModel") as BarcodeModel?
                with(barcodeTextEditText) { this?.setText(getText()) }
                saveBarcodeButton?.setEnabled(false)
            }
        }
        barcodeImageView.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_qr_code))
        generateBarcodeButton.setEnabled(barcodeTextEditText.getText().length != 0)
        barcodeTextEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                generateBarcodeButton.setEnabled(charSequence.length != 0)
            }

            override fun afterTextChanged(editable: Editable) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                generateBarcodeButton.setEnabled(charSequence.length != 0)
            }
        })
        generateBarcodeButton.setOnClickListener(View.OnClickListener {
            bitmap = BarcodeGenerator().generateBarcode(
                barcodeImageView.getHeight(),
                barcodeImageView.getWidth(),
                barcodeTextEditText.getText().toString()
            )
            barcodeImageView.setImageBitmap(bitmap)
        })

    }

    fun makeToastMessage(resource: Int) {
        Toast.makeText(applicationContext, resources.getString(resource), Toast.LENGTH_SHORT).show()
    }
}