package com.example.ccd

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class ProdileActivity : AppCompatActivity() {
    lateinit var firstname : TextView
    lateinit var lastname : TextView
    lateinit var Email : TextView
    lateinit var address : TextView
    lateinit var provinces : TextView
    lateinit var postalcode : TextView
    lateinit var userfn : String
    lateinit var userlastname : String
    lateinit var useremail : String
    lateinit var useraddress : String
    lateinit var userpro : String
    lateinit var userpc : String
    lateinit var  email : String
    lateinit var DB: DBHelper


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prodile)
        firstname = findViewById(R.id.fname)
        lastname = findViewById(R.id.lname)
        Email = findViewById(R.id.uemail)
        address = findViewById(R.id.address)
        provinces = findViewById(R.id.provinces)
        postalcode =  findViewById(R.id.postalcode)
        DB = DBHelper(this)
  //      email = intent.extras!!.getString("email").toString()
      //    sqldata()
         getdata()


    }

    private fun sqldata() {
        val c = DB.searchData(email!!)
        c!!.moveToNext()
//
     firstname.setText(c.getString(0).toString())
        lastname.setText(c.getString(1).toString())
        Email.setText(c.getString(2).toString())
        address.setText(c.getString(3).toString())
        provinces.setText(c.getString(4).toString())
        postalcode.setText(c.getString(5).toString())
    }

    private fun getdata() {
        try {
            val shareper = getSharedPreferences("myperf", MODE_PRIVATE)
            userfn = shareper.getString("first_name",null).toString()
            userlastname = shareper.getString("last_name",null).toString()
            useremail = shareper.getString("email_id",null).toString()
            useraddress = shareper.getString("address",null).toString()
            userpro = shareper.getString("provision",null).toString()
            userpc = shareper.getString("postalcode",null).toString()

            firstname.setText(userfn)
            lastname.setText(userlastname)
            Email.setText(useremail)
            address.setText(useraddress)
            provinces.setText(userpro)
            postalcode.setText(userpc)


        }
        catch (ex:Exception){

        }
    }
}