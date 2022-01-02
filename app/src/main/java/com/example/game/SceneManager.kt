package com.example.game

import android.content.Context
import android.graphics.Canvas
import android.view.MotionEvent
import java.util.ArrayList

class SceneManager {
    private val scenes = ArrayList<Scene>()
    fun recieveTouch(event: MotionEvent?) {
        scenes[ACTIVE_SCENE].recieveTouch(event)
    }

    fun update() {
        scenes[ACTIVE_SCENE].update()
    }

    fun draw(canvas: Canvas?) {
        scenes[ACTIVE_SCENE].draw(canvas)
    }

    companion object {
        var ACTIVE_SCENE = 0
    }

    init {
        scenes.add(GameplayScene())
    }
}