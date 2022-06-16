package com.norazmir.bas.model

import java.io.Serializable

class BarcodeModel(var id: Int, private var text: String) : Serializable {
    fun getText(): String {
        return text
    }

    fun setText(band: String?) {
        text = text
    }
}