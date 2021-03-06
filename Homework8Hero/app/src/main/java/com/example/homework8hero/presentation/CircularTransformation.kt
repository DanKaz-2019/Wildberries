package com.example.homework8hero.presentation

import android.graphics.*
import com.squareup.picasso.Transformation


class CircularTransformation(private val mRadius: Int = 10) : Transformation {

    override fun transform(source: Bitmap): Bitmap? {
        val output = Bitmap.createBitmap(
            source.width, source.height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(output)
        val paint = Paint()
        val rect = Rect(0, 0, source.width, source.height)
        paint.isAntiAlias = true
        paint.isFilterBitmap = true
        paint.isDither = true
        canvas.drawARGB(0, 0, 0, 0)
        paint.color = Color.parseColor("#BAB399")
        if (mRadius == 0) {
            canvas.drawCircle(
                source.width / 2 + 0.7f, source.height / 2 + 0.7f,
                source.width / 2 - 1.1f, paint
            )
        } else {
            canvas.drawCircle(
                source.width / 2 + 0.7f, source.height / 2 + 0.7f,
                mRadius.toFloat(), paint
            )
        }
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(source, rect, rect, paint)
        if (source != output) {
            source.recycle()
        }
        return output
    }

    override fun key(): String {
        return "circular$mRadius"
    }
}