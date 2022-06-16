package com.norazmir.bas.model

import android.graphics.Bitmap
import android.graphics.Color
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import java.io.Serializable

class BarcodeGenerator {

    private var bitmap: Bitmap? = null

    fun generateBarcode(height: Int, width: Int, barcodeText: String?): Bitmap? {
        val multiFormatWriter = MultiFormatWriter()
        try {
            val bitMatrix =
                multiFormatWriter.encode(barcodeText, BarcodeFormat.CODE_128, width, height)
            bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565)
            for (i in 0 until width) {
                for (j in 0 until height) {
                    with(bitmap) { this?.setPixel(i, j, if (bitMatrix[i, j]) Color.BLACK else Color.WHITE) }
                }
            }
        } catch (e: WriterException) {
            e.printStackTrace()
        }
        return bitmap
    }

}