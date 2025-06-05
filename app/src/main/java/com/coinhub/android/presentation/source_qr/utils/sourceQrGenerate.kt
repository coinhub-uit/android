package com.coinhub.android.presentation.source_qr.utils

import android.graphics.Bitmap
import android.graphics.Color
import androidx.core.graphics.createBitmap
import androidx.core.graphics.set
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter

fun sourceQrGenerate(
    content: String,
    width: Int = 600,
    height: Int = 300,
): Bitmap? {
    return try {
        val bitMatrix = MultiFormatWriter().encode(
            content,
            BarcodeFormat.QR_CODE,
            width,
            height
        )
        val bmp = createBitmap(width, height, Bitmap.Config.RGB_565)
        for (x in 0 until width) {
            for (y in 0 until height) {
                bmp[x, y] = if (bitMatrix[x, y]) Color.BLACK else Color.WHITE
            }
        }
        bmp
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
