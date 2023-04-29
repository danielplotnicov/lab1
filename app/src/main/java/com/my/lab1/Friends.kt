package com.my.lab1

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class Friends(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "friends.db"
        const val DATABASE_VERSION = 1
        const val TABLE_NAME = "friends"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PHONE = "phone"
        const val COLUMN_BIRTHDAY = "birthday"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME (" +
                "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$COLUMN_NAME TEXT NOT NULL, " +
                "$COLUMN_PHONE TEXT NOT NULL, " +
                "$COLUMN_BIRTHDAY TEXT NOT NULL" +
                ");"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }

    // Search for a friend by name
    fun searchFriendByName(name: String): Cursor? {
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME WHERE $COLUMN_NAME LIKE ?", arrayOf("%$name%"))
        return cursor
    }

    // Add a new friend to the database
    fun addFriend(name: String, phone: String, birthday: String): Long {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_PHONE, phone)
            put(COLUMN_BIRTHDAY, birthday)
        }
        val id = db.insert(TABLE_NAME, null, values)
        db.close()
        return id
    }

    // Update a friend's information
    fun updateFriend(id: Long, name: String, phone: String, birthday: String): Int {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_PHONE, phone)
            put(COLUMN_BIRTHDAY, birthday)
        }
        val rowsUpdated = db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
        return rowsUpdated
    }

    // Delete a friend from the database
    fun deleteFriend(id: Long): Int {
        val db = this.writableDatabase
        val rowsDeleted = db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(id.toString()))
        db.close()
        return rowsDeleted
    }

    // Delete all friends from the database
    fun deleteAllFriends(): Int {
        val db = this.writableDatabase
        val rowsDeleted = db.delete(TABLE_NAME, null, null)
        db.close()
        return rowsDeleted
    }

    fun viewAllFriends(): ArrayList<Friend> {
        val friendList: ArrayList<Friend> = ArrayList()

        val selectQuery = "SELECT  * FROM ${Friend.TABLE_NAME}"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)

        if (cursor.moveToFirst()) {
            do {
                val friend = Friend(
                    cursor.getInt(cursor.getColumnIndexOrThrow(Friend.COLUMN_NO)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Friend.COLUMN_NAME)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Friend.COLUMN_PHONE)),
                    cursor.getString(cursor.getColumnIndexOrThrow(Friend.COLUMN_BIRTHDAY))
                )
                friendList.add(friend)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return friendList
    }
}

class Friend(var no: Int, var name: String, var phone: String, var birthday: String) {
    companion object {
        const val TABLE_NAME = "friends"
        const val COLUMN_NO = "no"
        const val COLUMN_NAME = "name"
        const val COLUMN_PHONE = "phone"
        const val COLUMN_BIRTHDAY = "birthday"
    }

}
