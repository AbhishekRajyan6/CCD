package com.example.ccd

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [AdminAddUserFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminAddUserFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
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
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_admin_add_user, container, false)
        fn = view.findViewById(R.id.Firstname)
        ln = view.findViewById(R.id.lastname)
        Ad = view.findViewById(R.id.address)
        em = view.findViewById(R.id.email)
        pro = view.findViewById(R.id.provision)
        pc = view.findViewById(R.id.pcode)
        pass = view.findViewById(R.id.password)
        cpass = view.findViewById(R.id.condirmpassowrd)
        login = view.findViewById(R.id.signin)
        mAuth = FirebaseAuth.getInstance()

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
                    Toast.makeText(activity,"Please write your First name", Toast.LENGTH_LONG).show()
                }
                else if (lastname.isEmpty()){
                    Toast.makeText(activity,"Please write your Last name", Toast.LENGTH_LONG).show()

                }
                else if (Email.isEmpty()){
                    Toast.makeText(activity,"Please write your Email", Toast.LENGTH_LONG).show()
                }
                else if (Address.isEmpty()){
                    Toast.makeText(activity,"Please write your Address", Toast.LENGTH_LONG).show()
                }
                else  if (provision.isEmpty()){
                    Toast.makeText(activity,"Please write your Provision", Toast.LENGTH_LONG).show()
                }
                else if (postalcode.isEmpty()){
                    Toast.makeText(activity,"Please write your Postal code", Toast.LENGTH_LONG).show()
                }
                else if (password.isEmpty()){
                    Toast.makeText(activity,"Please write your password", Toast.LENGTH_LONG).show()
                }
                else if (confirmpassword.isEmpty()){
                    Toast.makeText(activity,"Please write your password", Toast.LENGTH_LONG).show()
                }
                else if (!password.equals(confirmpassword)){
                    Toast.makeText(activity,"Password does't match", Toast.LENGTH_LONG).show()
                }
                else{

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
                                userhashmap["UserType"] = 1
                                refUser.updateChildren(userhashmap)
                                    .addOnCompleteListener { task ->
                                        if (task.isSuccessful){
                                            Toast.makeText(activity, "Signup Successful!", Toast.LENGTH_SHORT).show()

                                        }
                                        else{

                                        }
                                    }
                            }
                            else{
                                Toast.makeText(activity, "Signup Failed!" + task.exception!!.message.toString(), Toast.LENGTH_SHORT).show()
                            }
                        }

                }

            }
            catch (ex:Exception){
                Toast.makeText(activity,"Something is wrong NEW", Toast.LENGTH_LONG).show()
            }
        })
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AdminAddUserFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AdminAddUserFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}