package com.example.game

import android.graphics.Canvas
import android.view.MotionEvent


interface Scene {
    fun update()
    fun draw(canvas: Canvas?)
    fun terminate()
    fun recieveTouch(event: MotionEvent?)
}
