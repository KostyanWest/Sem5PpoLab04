package com.example.game

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.view.MotionEvent
import java.lang.Exception


class GameplayScene() : Scene {
    private val r = Rect()
    private val player = RectPlayer(Rect(100, 100, 200, 200), Color.rgb(255, 0, 0))
    private var playerPoint: Point
    private var obstacleManager: ObstacleManager
    private var movingPlayer = false
    private var gameOver = false
    private var gameOverTime: Long = 0
    private val orientationData: OrientationData
    private var frameTime: Long
    fun reset() {
        playerPoint = Point(Constants.SCREEN_WIDTH / 2, 3 * Constants.SCREEN_HEIGHT / 4)
        player.update(playerPoint)
        obstacleManager = ObstacleManager(200, 350, 75, Color.BLACK)
        movingPlayer = false
    }

    override fun terminate() {
        SceneManager.ACTIVE_SCENE = 0
    }

    override fun recieveTouch(event: MotionEvent?) {
        when (event!!.action) {
            MotionEvent.ACTION_DOWN -> {
                if (!gameOver && player.getRectangle().contains(event.x.toInt(),
                        event.y.toInt())
                ) movingPlayer = true
                if (gameOver && System.currentTimeMillis() - gameOverTime >= 2000) {
                    reset()
                    gameOver = false
                    orientationData.newGame()
                }
            }
            MotionEvent.ACTION_MOVE -> if (!gameOver && movingPlayer) playerPoint[event.x.toInt()] =
                event.y.toInt()
            MotionEvent.ACTION_UP -> movingPlayer = false
        }
    }

    override fun draw(canvas: Canvas?) {
        canvas!!.drawColor(Color.WHITE)
        player.draw(canvas)
        obstacleManager.draw(canvas!!)
        if (gameOver) {
            throw GameOverException(obstacleManager.score.toString())
        }
    }

    override fun update() {
        if (!gameOver) {
            if (frameTime < Constants.INIT_TIME) frameTime = Constants.INIT_TIME
            val elapsedTime = (System.currentTimeMillis() - frameTime).toInt()
            frameTime = System.currentTimeMillis()
            if (orientationData.orientation != null && orientationData.startOrientation != null) {
                val pitch = orientationData.orientation[1] - orientationData.startOrientation!![1]
                val roll = orientationData.orientation[2] - orientationData.startOrientation!![2]
                val xSpeed = 2 * roll * Constants.SCREEN_WIDTH / 1000f
                val ySpeed = pitch * Constants.SCREEN_HEIGHT / 1000f
                playerPoint.x += if (Math.abs(xSpeed * elapsedTime) > 5) xSpeed.toInt() * elapsedTime else 0
                playerPoint.y -= if (Math.abs(ySpeed * elapsedTime) > 5) ySpeed.toInt() * elapsedTime else 0
            }
            if (playerPoint.x < 0) playerPoint.x =
                0 else if (playerPoint.x > Constants.SCREEN_WIDTH) playerPoint.x =
                Constants.SCREEN_WIDTH
            if (playerPoint.y < 0) playerPoint.y =
                0 else if (playerPoint.y > Constants.SCREEN_HEIGHT) playerPoint.y =
                Constants.SCREEN_HEIGHT
            player.update(playerPoint)
            obstacleManager.update()
            if (obstacleManager.playerCollide(player)) {
                gameOver = true
                gameOverTime = System.currentTimeMillis()
            }
        }
    }

    private fun drawCenterText(canvas: Canvas, paint: Paint, text: String) {
        paint.textAlign = Paint.Align.LEFT
        canvas.getClipBounds(r)
        val cHeight = r.height()
        val cWidth = r.width()
        paint.getTextBounds(text, 0, text.length, r)
        val x = cWidth / 2f - r.width() / 2f - r.left
        val y = cHeight / 2f + r.height() / 2f - r.bottom
        canvas.drawText(text, x, y, paint)
    }

    init {
        playerPoint = Point(Constants.SCREEN_WIDTH / 2, 3 * Constants.SCREEN_HEIGHT / 4)
        player.update(playerPoint)
        obstacleManager = ObstacleManager(200, 350, 75, Color.BLACK)
        orientationData = OrientationData()
        orientationData.register()
        frameTime = System.currentTimeMillis()
    }
}