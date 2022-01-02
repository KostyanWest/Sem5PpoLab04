package com.example.game

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ListView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ScoreActivity : AppCompatActivity() {

    private lateinit var mAdapter: DataAdapter
    companion object{
        var load_times = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        mAdapter = DataAdapter(
            applicationContext,
            R.layout.grid
        )

        val db = DatabaseHandler(this)

        DataAdapter.Users = db.allUsers.toMutableList()
        mAdapter.notifyDataSetChanged()

        val g = findViewById<ListView>(R.id.list_item)
        g.adapter = mAdapter


        findViewById<Button>(R.id.returns).setOnClickListener{
            var intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            startActivity(intent)
        }

        findViewById<Button>(R.id.load).setOnClickListener {
            change()
        }

        findViewById<Button>(R.id.button).setOnClickListener(viewClickListener)

        if(load_times != 2){
            var intent = Intent(this, ScoreActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            this.startActivity(intent)
            load_times += 1
        }
        else
            load_times = 0

    }

    var viewClickListener =
        View.OnClickListener { v -> showPopupMenu(v) }

    private fun showPopupMenu(v: View) {
        val popupMenu = PopupMenu(this, v)
        popupMenu.inflate(R.menu.popupmenu)
        popupMenu
            .setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                override fun onMenuItemClick(item: MenuItem): Boolean {
                    return when (item.getItemId()) {
                        R.id.nickname_menu -> {
                            DataAdapter.Users.sortBy { userInfo -> userInfo.nickname }
                            mAdapter.notifyDataSetChanged()
                            true
                        }
                        R.id.name_menu -> {
                            DataAdapter.Users.sortBy { userInfo -> userInfo.name }
                            mAdapter.notifyDataSetChanged()
                            true
                        }
                        R.id.score_menu -> {
                            DataAdapter.Users.sortBy { userInfo -> -userInfo.score }
                            mAdapter.notifyDataSetChanged()
                            true
                        }
                        else -> false
                    }
                }
            })
        popupMenu.setOnDismissListener(object : PopupMenu.OnDismissListener {
            override fun onDismiss(menu: PopupMenu?) {
                Toast.makeText(applicationContext, "onDismiss",
                    Toast.LENGTH_SHORT).show()
            }
        })
        popupMenu.show()
    }



    private fun change(){
        var intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
        this.startActivity(intent)
        finish()
    }

}