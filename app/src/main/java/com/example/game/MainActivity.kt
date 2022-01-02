package com.example.game

import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        Constants.SCREEN_WIDTH = dm.widthPixels
        Constants.SCREEN_HEIGHT = dm.heightPixels

        setContentView(R.layout.activity_main)


        findViewById<Button>(R.id.play_btn).setOnClickListener{
            var intent = Intent(this, PlayActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        findViewById<Button>(R.id.score_btn).setOnClickListener{
            var intent = Intent(this, ScoreActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
    }
}