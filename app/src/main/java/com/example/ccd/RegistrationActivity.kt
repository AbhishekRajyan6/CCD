package com.example.ccd

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Email
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class RegistrationActivity : AppCompatActivity() {
    lateinit var DB: DBHelper
    lateinit var fn : TextInputLayout
    lateinit var ln : TextInputLayout
    lateinit var Ad : TextInputLayout
    lateinit var em : TextInputLayout
    lateinit var pro: TextInputLayout
    lateinit var pc : TextInputLayout
    lateinit var pass : TextInputLayout
    lateinit var cpass : TextInputLayout
    lateinit var login : AppCompatButton
    lateinit var firstname : String
    lateinit var lastname : String
    lateinit var Email : String
    lateinit var Address : String
    lateinit var provision : String
    lateinit var postalcode : String
    lateinit var password : String
    lateinit var confirmpassword : String
    private lateinit var mAuth: FirebaseAuth
    private lateinit var refUser: DatabaseReference
    private  var firebaseuserID : String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        fn = findViewById(R.id.Firstname)
        ln = findViewById(R.id.lastname)
        Ad = findViewById(R.id.address)
        em = findViewById(R.id.email)
        pro = findViewById(R.id.provision)
        pc = findViewById(R.id.pcode)
        pass = findViewById(R.id.password)
        cpass = findViewById(R.id.condirmpassowrd)
        login = findViewById(R.id.signin)
        mAuth = FirebaseAuth.getInstance()
        DB = DBHelper(this)



        login.setOnClickListener(View.OnClickListener {

            firstname = fn.getEditText()?.getText().toString()
            lastname = ln.getEditText()?.getText().toString()
            Email = em.getEditText()?.getText().toString()
            Address = Ad.getEditText()?.getText().toString()
            provision = pro.getEditText()?.getText().toString()
            postalcode = pc.getEditText()?.getText().toString()
            password = pass.getEditText()?.getText().toString()
            confirmpassword = cpass.getEditText()?.getText().toString()

            try {
              if(firstname.isEmpty()){
                  Toast.makeText(applicationContext,"Please write your First name",Toast.LENGTH_LONG).show()
              }
              else if (lastname.isEmpty()){
                  Toast.makeText(applicationContext,"Please write your Last name",Toast.LENGTH_LONG).show()

              }
                else if (Email.isEmpty()){
                  Toast.makeText(applicationContext,"Please write your Email",Toast.LENGTH_LONG).show()
              }
              else if (Address.isEmpty()){
                  Toast.makeText(applicationContext,"Please write your Address",Toast.LENGTH_LONG).show()
              }
              else  if (provision.isEmpty()){
                  Toast.makeText(applicationContext,"Please write your Provision",Toast.LENGTH_LONG).show()
              }
              else if (postalcode.isEmpty()){
                  Toast.makeText(applicationContext,"Please write your Postal code",Toast.LENGTH_LONG).show()
              }
              else if (password.isEmpty()){
                  Toast.makeText(applicationContext,"Please write your password",Toast.LENGTH_LONG).show()
              }
              else if (confirmpassword.isEmpty()){
                  Toast.makeText(applicationContext,"Please write your password",Toast.LENGTH_LONG).show()
              }
              else if (!password.equals(confirmpassword)){
                  Toast.makeText(applicationContext,"Password does't match",Toast.LENGTH_LONG).show()
              }
              else{
               //   sqldata()
               Passdata()
              //    Register()
              }

            }
            catch (ex:Exception){
                Toast.makeText(applicationContext,"Something is wrong NEW",Toast.LENGTH_LONG).show()
            }
        })

    }

    @SuppressLint("SuspiciousIndentation")
    private fun Register() {
        mAuth.createUserWithEmailAndPassword(Email,password)
            .addOnCompleteListener{task ->
                if (task.isSuccessful){
                 firebaseuserID = mAuth.currentUser!!.uid
                    refUser = FirebaseDatabase.getInstance().reference.child("Users").child(firebaseuserID)
                    val userhashmap = HashMap<String, Any>()
                    userhashmap["UID"] = firebaseuserID
                    userhashmap["FirstName"] = firstname
                    userhashmap["LastName"] = lastname
                    userhashmap["Email"] = Email
                    userhashmap["Address"] = Address
                    userhashmap["Province"] = provision
                    userhashmap["PostalCode"] = postalcode
                    userhashmap["Password"] = password
                    userhashmap["UserType"] = 0
                    refUser.updateChildren(userhashmap)
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful){
                                Toast.makeText(this@RegistrationActivity, "Signup Successful!", Toast.LENGTH_SHORT).show()
                              val intent = Intent(this@RegistrationActivity, LoginActivity::class.java)
                                 intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
                                startActivity(intent)
                                finish()

                            }
                            else{

                            }
                        }
                }
                else{
                    Toast.makeText(this@RegistrationActivity, "Signup Failed!" + task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun sqldata() {
        try {
            val insert = DB.insertData(firstname,lastname,Email,Address,provision,postalcode,password)
            if (insert == true) {
                Toast.makeText(this@RegistrationActivity, "Signup Successful!", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@RegistrationActivity, LoginActivity::class.java))
            } else {
                Toast.makeText(this@RegistrationActivity, "Signup Failed!", Toast.LENGTH_SHORT).show()
            }
        }
        catch (e:Exception){
            Toast.makeText(this@RegistrationActivity, "Something Went wrong!", Toast.LENGTH_SHORT).show()
        }
    }

    public fun Passdata() {

        val shareper = getSharedPreferences("myperf", MODE_PRIVATE)
        val editor = shareper.edit()


         editor.apply {
             putString("first_name", firstname)
             putString("last_name", lastname)
             putString("email_id", Email)
             putString("address", Address)
             putString("provision", provision)
             putString("postalcode", postalcode)
             putString("password", password)
             apply()
         }
        val i = Intent(this, LoginActivity::class.java)
        startActivity(i)
    }
}
