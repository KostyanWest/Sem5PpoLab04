package com.example.game

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Point
import android.graphics.Rect

class RectPlayer(rectangle: Rect, color: Int) : GameObject {
    private val rectangle: Rect
    private val color: Int
    fun getRectangle(): Rect {
        return rectangle
    }

    override fun draw(canvas: Canvas?) {
        val paint = Paint()
        paint.setColor(color)
        canvas!!.drawRect(rectangle, paint)
    }

    override fun update() {}
    fun update(point: Point) {
        // left, right, top, bottom
        rectangle.set(point.x - rectangle.width() / 2,
            point.y - rectangle.height() / 2,
            point.x + rectangle.width() / 2,
            point.y + rectangle.height() / 2)
    }

    init {
        this.rectangle = rectangle
        this.color = color
    }
}
