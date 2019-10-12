package com.example.memorymyfriend

import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.memorymyfriend.DB
import com.example.memorymyfriend.R
import kotlinx.android.synthetic.main.add_friend.*
import kotlinx.android.synthetic.main.friend_list.*
import java.lang.Exception

class Addfriend : AppCompatActivity() {

    var id = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_friend)

        try {
            val bundle:Bundle = intent.extras!!
            id = bundle.getInt("ID",0)
            if (id!=0){
                edt_name.setText(bundle.getString("friendname"))
                edt_tel.setText(bundle.getString("friendtel"))
                edt_email.setText(bundle.getString("friendemail"))
            }
        }catch (ex:Exception){}

    }

    fun addFriend(view: View){
        val dbManager = DB(this)
        val values = ContentValues()
        values.put("Name", edt_name.text.toString())
        values.put("Tel", edt_tel.text.toString())
        values.put("Email", edt_tel.text.toString())


        if (id ==0){
            val ID = dbManager.insert(values)
            if (ID > 0){
                Toast.makeText(this, "Friend is added", Toast.LENGTH_SHORT).show()
                finish()
            }
            else{
                Toast.makeText(this, "Error adding friend", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            val selectionArgs = arrayOf(id.toString())
            val ID = dbManager.update(values, "ID=?", selectionArgs)
            if (ID>0){
                Toast.makeText(this, "Friend is added", Toast.LENGTH_SHORT).show()
                finish()
            }
            else{
                Toast.makeText(this, "Error adding friend", Toast.LENGTH_SHORT).show()
            }
        }
    }
}