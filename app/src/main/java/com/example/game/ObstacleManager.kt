package com.example.game

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint

class ObstacleManager(
    private val playerGap: Int,
    private val obstacleGap: Int,
    private val obstacleHeight: Int,
    private val color: Int,
) {
    //higher index = lower on screen = higher y value
    private val obstacles: ArrayList<Obstacle>
    private var startTime: Long
    private val initTime: Long
    var score = 0
    fun playerCollide(player: RectPlayer?): Boolean {
        for (ob in obstacles) {
            if (ob.playerCollide(player!!)) return true
        }
        return false
    }

    private fun populateObstacles() {
        var currY = -5 * Constants.SCREEN_HEIGHT / 4
        while (currY < 0) {
            val xStart = (Math.random() * (Constants.SCREEN_WIDTH - playerGap)).toInt()
            obstacles.add(Obstacle(obstacleHeight, color, xStart, currY, playerGap))
            currY += obstacleHeight + obstacleGap
        }
    }

    fun update() {
        if (startTime < Constants.INIT_TIME) startTime = Constants.INIT_TIME
        val elapsedTime = (System.currentTimeMillis() - startTime).toInt()
        startTime = System.currentTimeMillis()
        val speed = Math.sqrt(1 + (startTime - initTime) / 2000.0)
            .toFloat() * Constants.SCREEN_HEIGHT / 10000.0f
        for (ob in obstacles) {
            ob.incrementY(speed * elapsedTime)
        }
        if (obstacles[obstacles.size - 1].getRectangle().top >= Constants.SCREEN_HEIGHT) {
            val xStart = (Math.random() * (Constants.SCREEN_WIDTH - playerGap)).toInt()
            obstacles.add(0, Obstacle(obstacleHeight,
                color, xStart, obstacles[0].getRectangle().top - obstacleHeight - obstacleGap,
                playerGap))
            obstacles.removeAt(obstacles.size - 1)
            score++
        }
    }

    fun draw(canvas: Canvas) {
        for (ob in obstacles) ob.draw(canvas)
        val paint = Paint()
        paint.textSize = 100f
        paint.color = Color.MAGENTA
        canvas.drawText("" + score, 50f, 50 + paint.descent() - paint.ascent(), paint)
    }

    init {
        initTime = System.currentTimeMillis()
        startTime = initTime
        obstacles = ArrayList()
        populateObstacles()
    }
}