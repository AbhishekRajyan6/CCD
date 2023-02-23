package com.example.ccd

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Base64.*
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.AppCompatButton
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.io.ByteArrayOutputStream
import java.util.Base64.Encoder
import java.util.Base64

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddProductFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddProductFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private lateinit var r1: RadioButton
    private lateinit var r2: RadioButton
    private lateinit var r3: RadioButton
    private lateinit var insertdata: AppCompatButton
    private lateinit var selectimg: AppCompatButton
    lateinit var radiodata : String
    lateinit var productname : TextInputLayout
    lateinit var productdescription : TextInputLayout
    lateinit var productprice : TextInputLayout
    lateinit var product : ImageView
    lateinit var productnames : String
    lateinit var productdescriptions : String
    lateinit var productprices : String
    var productimg : String? = ""
    lateinit var db : DatabaseReference

    private var imageUri : Uri? = null

    private val selectImage = registerForActivityResult(ActivityResultContracts.GetContent()){
        imageUri =it
         product.setImageURI(imageUri)
    }
    val option = arrayOf("Hot Beverages","Cold Beverages","Breakfast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_product, container, false)
       r1 = view.findViewById(R.id.hb)
        r2 = view.findViewById(R.id.cb)
        r3 = view.findViewById(R.id.breakf)
        productname = view.findViewById(R.id.product_name)
        productdescription = view.findViewById(R.id.product_description)
        productprice = view.findViewById(R.id.product_price)
        product = view.findViewById(R.id.product_img)
        insertdata = view.findViewById(R.id.add_product)
        selectimg = view.findViewById(R.id.chooseimg)

        r1.setOnCheckedChangeListener{buttonView,isChecked ->
            radiodata = r1.text.toString()
        }
        r2.setOnCheckedChangeListener{buttonView,isChecked ->
            radiodata = r2.text.toString()
            Toast.makeText(activity,radiodata,Toast.LENGTH_SHORT).show()
        }
        r3.setOnCheckedChangeListener{buttonView,isChecked ->
            radiodata = r3.text.toString()
        }

        insertdata.setOnClickListener(View.OnClickListener {
            productnames = productname.getEditText()?.getText().toString()
            productdescriptions = productdescription.getEditText()?.getText().toString()
            productprices = productprice.getEditText()?.getText().toString()

        val storageref = FirebaseStorage.getInstance().getReference("image").child(radiodata).child(productnames)

                storageref.putFile(imageUri!!)
                .addOnCompleteListener {
                    storageref.downloadUrl.addOnSuccessListener {
                        storedata(it)
                    }.addOnFailureListener {
                       Toast.makeText(activity,it.message,Toast.LENGTH_LONG).show()
                    }
                }.addOnFailureListener{
                        Toast.makeText(activity,it.message,Toast.LENGTH_LONG).show()
                    }

        })

        selectimg.setOnClickListener(View.OnClickListener {
            selectImage.launch("image/*")

        })




        return view
    }

    private fun storedata(imageUrl: Uri?) {
        db = FirebaseDatabase.getInstance().getReference("Category").child(radiodata).child(productnames)
        productimg = imageUrl.toString()
        val item = products(productnames,productdescriptions,productprices,productimg)
        val databaseReference = FirebaseDatabase.getInstance().reference
        val id = databaseReference.push().key
        db.setValue(item).addOnCompleteListener {
            Toast.makeText(activity,"Product Added",Toast.LENGTH_LONG).show()
        }.addOnFailureListener {
            Toast.makeText(activity,"Failed!",Toast.LENGTH_LONG).show()
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private val ActivityResultLauncher = registerForActivityResult<Intent,ActivityResult>(
        ActivityResultContracts.StartActivityForResult()
    ){result:ActivityResult ->
        if (result.resultCode==RESULT_OK){
            val uri = result.data!!.data
            try{
                val inputStream = activity?.contentResolver?.openInputStream(uri!!)
                val myBitmap = BitmapFactory.decodeStream(inputStream)
                val stream = ByteArrayOutputStream()
                myBitmap.compress(Bitmap.CompressFormat.JPEG,100,stream)
                val bytes = stream.toByteArray()
              //  productimg = Base64.getEncoder().encodeToString(bytes)
                product.setImageBitmap(myBitmap)
                inputStream!!.close()
            }
            catch (ex: Exception){

            }
        }

    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment AddProductFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}