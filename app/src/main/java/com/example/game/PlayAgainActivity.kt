package com.example.game

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class PlayAgainActivity : AppCompatActivity() {

    lateinit var winNickname: EditText
    lateinit var winName: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_again)


       winNickname = findViewById<EditText>(R.id.win_nickname)
       winName = findViewById<EditText>(R.id.win_name)



        findViewById<Button>(R.id.no_btn).setOnClickListener{
            save()
            var intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        findViewById<Button>(R.id.yes_btn).setOnClickListener{
            save()
            var intent = Intent(this, PlayActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }
    }


    private fun save(){
        if(winName.text.isNotEmpty() && winNickname.text.isNotEmpty()){
            var db = DatabaseHandler(this)
            var u = UserInfo(winNickname.text.toString(), winName.text.toString(), 0)
            u = db.getOrCreateUser(u)
            u.score = Rubbish.score
            //db.update(u)
        }
    }
}