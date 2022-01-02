package com.example.game

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHandler(context: Context?) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_CONTACTS + "("
                + KEY_NICKNAME + " TEXT PRIMARY KEY," + KEY_NAME + " TEXT,"
                + KEY_SCORE + " INTEGER" + ")")
        db.execSQL(CREATE_CONTACTS_TABLE)
    }


    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS)
        onCreate(db)
    }


    fun getOrCreateUser(user: UserInfo): UserInfo {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(KEY_NAME, user.name)
        values.put(KEY_NICKNAME, user.nickname)
        values.put(KEY_SCORE, user.score)


        val ex = db.insert(TABLE_CONTACTS, null, values)
        db.close()

        if (ex == (-1).toLong()) {
            val selectQuery = "SELECT  * FROM " + TABLE_CONTACTS + " WHERE ${KEY_NICKNAME} = ${user.nickname}"
            val db = this.writableDatabase
            val cursor: Cursor = db.rawQuery(selectQuery, null)

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    return UserInfo(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2).toInt())
                } while (cursor.moveToNext())
            }
        }
        return user
    }



    val allUsers: List<UserInfo>
        get() {
            val users: MutableList<UserInfo> = ArrayList<UserInfo>()
            // Select All Query
            val selectQuery = "SELECT  * FROM " + TABLE_CONTACTS
            val db = this.writableDatabase
            val cursor: Cursor = db.rawQuery(selectQuery, null)

            // looping through all rows and adding to list
            if (cursor.moveToFirst()) {
                do {
                    val user = UserInfo(cursor.getString(0), cursor.getString(1),
                        cursor.getString(2).toInt())
                    users.add(user)
                } while (cursor.moveToNext())
            }
            return users
        }


    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "contactsManager"
        private const val TABLE_CONTACTS = "scores"
        private const val KEY_NAME = "name"
        private const val KEY_NICKNAME = "nickname"
        private const val KEY_SCORE = "score"
    }
}