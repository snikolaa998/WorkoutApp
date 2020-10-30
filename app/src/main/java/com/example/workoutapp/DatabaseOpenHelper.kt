package com.example.workoutapp

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import com.example.workoutapp.model.HistoryModel

class DatabaseOpenHelper(context: Context, factory: SQLiteDatabase.CursorFactory?) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "Workout.db"
        private const val TABLE_HISTORY = "history"
        private const val COLUMN_ID = "_id"
        private const val COLUMN_COMPLETED_DATE = "completed_date"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val sqlCreate = ("CREATE TABLE "
                + TABLE_HISTORY + "(" + COLUMN_ID + " INTEGER PRIMARY KEY,"
                + COLUMN_COMPLETED_DATE + " TEXT)")
        db?.execSQL(sqlCreate)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_HISTORY")
        onCreate(db)
    }

    fun addDate(date: String) {
        val contentValues = ContentValues()
        contentValues.put(COLUMN_COMPLETED_DATE, date)
        val db = this.writableDatabase
        db.insert(TABLE_HISTORY, null, contentValues)
        db.close()
    }

    fun getHistory(): ArrayList<HistoryModel> {
        val historyList = ArrayList<HistoryModel>()
        val selectQuery = "SELECT * FROM $TABLE_HISTORY"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        while (cursor.moveToNext()) {
            val dateValue = (cursor.getString(cursor.getColumnIndex(COLUMN_COMPLETED_DATE)))
            val id = (cursor.getInt(cursor.getColumnIndex(COLUMN_ID)))
            val data = HistoryModel(id, dateValue)
            historyList.add(data)
        }
        cursor.close()
        db.close()
        return historyList
    }
}