package com.example.memorymyfriend

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.friend_list.view.*

class MainActivity : AppCompatActivity() {
    var listFrind = ArrayList<Friend>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener {
        startActivity(Intent(this@MainActivity,Addfriend::class.java))
        }

        LoadQuery("%")
    }

    override fun onResume() {
        super.onResume()
        LoadQuery("%")
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    private fun LoadQuery(title: String) {
        val dbStudent = DB(this)
        val projections = arrayOf("ID", "Name", "Tel" , "Email")
        val selectionArgs = arrayOf(title)
        val cursor = dbStudent.Query(projections, "Name like ?", selectionArgs, "Name")
        listFrind.clear()
        if (cursor.moveToFirst()) {
            do {
                val ID = cursor.getInt(cursor.getColumnIndex("ID"))
                val Name = cursor.getString(cursor.getColumnIndex("Name"))
                val Tel = cursor.getString(cursor.getColumnIndex("Tel"))
                val Email = cursor.getString(cursor.getColumnIndex("Email"))
                listFrind.add(Friend(ID,Name,Tel,Email))
            } while (cursor.moveToNext())
        }

        val myNotesAdapter = MyFrindAdapter(this, listFrind)
        lv_frind.adapter = myNotesAdapter

    }
    inner class MyFrindAdapter(context: Context, private var listFriendAdapter: ArrayList<Friend>) :
        BaseAdapter() {
        private var context: Context? = context
        @SuppressLint("ViewHolder", "InflateParams")
        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val myView = layoutInflater.inflate(R.layout.friend_list, null)
            val myFriend = listFriendAdapter[position]
            myView.tv_namefriend.text = myFriend.Name
            myView.tv_tel.text = myFriend.Tel
            myView.tv_email.text = myFriend.Email
            myView.deleteBtn.setOnClickListener {
                val dbManager = DB(this.context!!)
                val selectionArgs = arrayOf(myFriend.ID.toString())
                dbManager.delete("ID=?", selectionArgs)
                LoadQuery("%")
            }
            myView.updateBtn.setOnClickListener {
                GoToUpdate(myFriend)
            }
            return myView
        }

        override fun getItem(position: Int): Any {
            return listFriendAdapter[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getCount(): Int {
            return listFriendAdapter.size
        }
    }
    private fun GoToUpdate(myStudent: Friend) {
        val intent = Intent(this, Addfriend::class.java)
        intent.putExtra("ID", myStudent.ID)
        intent.putExtra("Name", myStudent.Name)
        intent.putExtra("Tel", myStudent.Tel)
        intent.putExtra("Email", myStudent.Email)
        startActivity(intent)
    }

}
