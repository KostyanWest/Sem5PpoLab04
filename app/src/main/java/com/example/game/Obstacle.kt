package com.example.game

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect

class Obstacle(rectHeight: Int, private val color: Int, xStart: Int, yStart: Int, playerGap: Int) :
    GameObject {
    private val rectangle: Rect
    private val rectangle2: Rect
    fun getRectangle(): Rect {
        return rectangle
    }

    fun incrementY(y: Float) {
        rectangle.top += y.toInt()
        rectangle.bottom += y.toInt()
        rectangle2.top += y.toInt()
        rectangle2.bottom += y.toInt()
    }

    fun playerCollide(player: RectPlayer): Boolean {
        return (Rect.intersects(rectangle, player.getRectangle())
                || Rect.intersects(rectangle, player.getRectangle()))
    }

    override fun draw(canvas: Canvas?) {
        val paint = Paint()
        paint.setColor(color)
        canvas!!.drawRect(rectangle, paint)
        canvas!!.drawRect(rectangle2, paint)
    }

    override fun update() {}

    init {
        // l,t,r,b
        rectangle = Rect(0, yStart, xStart, yStart + rectHeight)
        rectangle2 = Rect(xStart + playerGap, yStart, Constants.SCREEN_WIDTH, yStart + rectHeight)
    }
}