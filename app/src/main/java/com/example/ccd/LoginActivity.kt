package com.example.ccd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isEmpty
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class LoginActivity : AppCompatActivity() {
    lateinit var DB: DBHelper
    lateinit var email: TextInputLayout
    lateinit var password : TextInputLayout
    lateinit var login : AppCompatButton
    lateinit var signup : TextView
    lateinit var datauseremail : String
    lateinit var datauserpassword : String
    lateinit var useremail : String
    lateinit var userpassword : String
    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUser: DatabaseReference
    private  var firebaseuserID : String = ""

       lateinit var UserType : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
         email = findViewById(R.id.email)
        password = findViewById(R.id.password)
        login = findViewById(R.id.signin)
        signup = findViewById(R.id.signup)
        DB = DBHelper(this)
        mAuth = FirebaseAuth.getInstance()
        login.setOnClickListener(View.OnClickListener {
              // sqlloginmethod()
             //  loginmethod()
            Loginuser()
        })





    }

    private fun Loginuser() {

        datauseremail = email.getEditText()?.getText().toString()
        datauserpassword = password.getEditText()?.getText().toString()
        if(email.isEmpty()){

        }
        else if (password.isEmpty()){

        }
        else{
            mAuth.signInWithEmailAndPassword(datauseremail,datauserpassword)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful){
                        firebaseuserID = mAuth.currentUser!!.uid
                        refUser = FirebaseDatabase.getInstance().getReference("Users")
                        refUser.child(firebaseuserID).get().addOnSuccessListener {
                            if (it.exists()){
                                UserType = it.child("UserType").value.toString()

                                if (UserType == "0"){
                                    Toast.makeText(this@LoginActivity, "UserLogin!", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                    finish()
                                }
                                else if (UserType == "1"){
                                    val intent = Intent(this@LoginActivity, AdminActivity::class.java)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                    startActivity(intent)
                                    finish()
                                    Toast.makeText(this@LoginActivity, "Admin Login!", Toast.LENGTH_SHORT).show()
                                }

                                else{
                                    Toast.makeText(this@LoginActivity, "Login Failed data 1 !", Toast.LENGTH_SHORT).show()
                                }

                            }

                            else    {
                                Toast.makeText(this@LoginActivity, "Login Failed data!", Toast.LENGTH_SHORT).show()
                            }

                        }


                    }
                    else    {
                        Toast.makeText(this@LoginActivity, "Login Failed!", Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }

    private fun sqlloginmethod() {
        datauseremail = email.getEditText()?.getText().toString()
        datauserpassword = password.getEditText()?.getText().toString()
         if(email.isEmpty()){

         }
        else if (password.isEmpty()){

         }
        else{
             val checkuserpass: Boolean = DB.checkEmailNamePass(datauseremail, datauserpassword)!!
             if (checkuserpass == true) {
                 val i = Intent(this@LoginActivity, ProdileActivity::class.java)
                 i.putExtra("email", datauseremail)
                 Toast.makeText(this@LoginActivity, "Login Successful!", Toast.LENGTH_SHORT).show()
                 startActivity(i)
             } else {
                 Toast.makeText(this@LoginActivity, "Login Failed!", Toast.LENGTH_SHORT).show()
             }
         }

    }

    private fun loginmethod() {
        val shareper = getSharedPreferences("myperf", MODE_PRIVATE)

        useremail = shareper.getString("email_id",null).toString()
        userpassword = shareper.getString("password",null).toString()

        datauseremail = email.getEditText()?.getText().toString()
        datauserpassword = password.getEditText()?.getText().toString()

        try {
            if (datauseremail.equals(useremail) && datauserpassword.equals(userpassword)){

                val intent = Intent(this, ProdileActivity::class.java)
                startActivity(intent)
            }
            else{
                Toast.makeText(applicationContext,"Invalid Email and Passowrd",Toast.LENGTH_LONG).show()
            }
        }
        catch (ex:Exception){
            Toast.makeText(applicationContext,"Something went wrong",Toast.LENGTH_LONG).show()

        }
    }
}