package com.example.ccd

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context,DATABASE_NAME,null, DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "user_info"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL("create table users(firstname TEXT, lastname TEXT, email TEXT, address TEXT, province TEXT, postalcode TEXT, password TEXT)")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("drop table if exists users")
    }
    fun insertData(
        firstname: String?,
        lastname: String?,
        email: String?,
        address: String?,
        province: String?,
        postalcode: String?,
        password: String?
    ): Boolean? {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("firstname", firstname)
        values.put("lastname", lastname)
        values.put("email", email)
        values.put("address", address)
        values.put("province", province)
        values.put("postalcode", postalcode)
        values.put("password", password)
        val result = db.insert("users", null, values)
        return if (result == -1L) false else true
    }

    fun updateData(firstname: String, lastname: String?, email: String?, address: String? ,province: String? , postalcode: String?, password: String?): Boolean? {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put("firstname", firstname)
        values.put("lastname", lastname)
        values.put("email", email)
        values.put("address", address)
        values.put("province", province)
        values.put("postalcode", postalcode)
        values.put("password", password)
        val result = db.update("users", values, "email=?", arrayOf(email)).toLong()
        return if (result == -1L) false else true
    }

    fun checkEmailNamePass(email: String?, password: String): Boolean? {
        val db = this.writableDatabase
        val cursor = db.rawQuery(
            "select * from users where email=? and password=?",
            arrayOf(email, password)
        )
        return if (cursor.count > 0) true else false
    }

    fun searchData(email: String?): Cursor? {
        val db = this.writableDatabase
        return db.rawQuery("select * from users where email=?", arrayOf(email))
    }

}