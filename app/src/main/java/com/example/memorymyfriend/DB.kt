package com.example.memorymyfriend

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.database.sqlite.SQLiteQueryBuilder
import android.widget.Toast

class DB(context: Context) {

    var dbName = "MyFriend"

    var dbTable = "Friend"

    var colID = "ID"

    var colName = "Name"

    var colTel= "Tel"

    var colEmail= "Email"

    var dbVersion = 3

    var sqlDB: SQLiteDatabase? =null

    init {
        val db = DatabaseHelperStudent(context)
        sqlDB = db.writableDatabase
    }

    inner class DatabaseHelperStudent(private var context: Context) :
        SQLiteOpenHelper(context, dbName, null, dbVersion) {


        override fun onCreate(db: SQLiteDatabase?) {
            val sqlCreateTable = "CREATE TABLE IF NOT EXISTS $dbTable ($colID INTEGER PRIMARY KEY,$colName TEXT, $colTel TEXT, $colEmail TEXT);"
            db!!.execSQL(sqlCreateTable)
            Toast.makeText(this.context, "Add Memmory ", Toast.LENGTH_SHORT).show()
        }

        override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
            db!!.execSQL("Drop table if Exists$dbTable")
        }
    }

    fun insert(values: ContentValues): Long {
        return sqlDB!!.insert(dbTable, "", values)
    }

    fun Query(projection: Array<String>, selection: String, selectionArgs: Array<String>, sorOrder: String): Cursor {
        val qb = SQLiteQueryBuilder()
        qb.tables = dbTable
        return qb.query(sqlDB, projection, selection, selectionArgs, null, null, sorOrder)
    }

    fun delete(selection: String, selectionArgs: Array<String>): Int {
        return sqlDB!!.delete(dbTable, selection, selectionArgs)
    }

    fun update(values: ContentValues, selection: String, selectionArgs: Array<String>): Int {
        return sqlDB!!.update(dbTable, values, selection, selectionArgs)
    }
}