package com.example.game

import android.content.Intent
import android.graphics.Canvas
import android.view.SurfaceHolder
import java.lang.Exception


class MainThread(private val surfaceHolder: SurfaceHolder, private val gamePanel: GamePanel) :
    Thread() {
    private var averageFPS = 0.0
    private var running = false
    fun setRunning(running: Boolean) {
        this.running = running
    }

    override fun run() {
        var startTime: Long
        var timeMillis = (1000 / MAX_FPS).toLong()
        var waitTime: Long
        var frameCount = 0
        var totalTime: Long = 0
        val targetTime = (1000 / MAX_FPS).toLong()
        while (running) {
            startTime = System.nanoTime()
            canvas = null
            try {
                canvas = surfaceHolder.lockCanvas()
                synchronized(surfaceHolder) {
                    gamePanel.update()
                    gamePanel.draw(canvas!!)
                }
            } catch (e: Exception) {
                if(e is GameOverException){
                    Rubbish.score = e.message!!.toInt()
                    running = false
                    var intent = Intent(Constants.CURRENT_CONTEXT, PlayAgainActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
                    Constants.CURRENT_CONTEXT!!.startActivity(intent)
                }
                else
                    e.printStackTrace()
            } finally {
                if (canvas != null) {
                    try {
                        surfaceHolder.unlockCanvasAndPost(canvas)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
            timeMillis = (System.nanoTime() - startTime) / 1000000
            waitTime = targetTime - timeMillis
            try {
                if (waitTime > 0)  sleep(waitTime)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            totalTime += System.nanoTime() - startTime
            frameCount++
            if (frameCount == MAX_FPS) {
                averageFPS = (1000 / (totalTime / frameCount / 1000000)).toDouble()
                frameCount = 0
                totalTime = 0
                println(averageFPS)
            }
        }
    }

    companion object {
        const val MAX_FPS = 60
        var canvas: Canvas? = null
    }
}
