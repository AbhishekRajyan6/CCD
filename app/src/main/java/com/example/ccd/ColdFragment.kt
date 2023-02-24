package com.example.ccd

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

var fragmentView2 : View? = null
var firedatabase2 : FirebaseDatabase? = null
var ColdList : ArrayList<products> ? = null
var ref2 : DatabaseReference? = null
var mRecyclerView2 : RecyclerView? =null
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ColdFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null



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
        fragmentView2= LayoutInflater.from(activity).inflate(R.layout.fragment_cold, container, false)
        //  val  view = inflater.inflate(R.layout.fragment_hot, container, false)
        firedatabase2 = FirebaseDatabase.getInstance()
        mRecyclerView2 = fragmentView2?.findViewById(R.id.recycleview2)
        mRecyclerView2?.setHasFixedSize(true)
        mRecyclerView2?.layoutManager = LinearLayoutManager(context)


        ColdList = arrayListOf<products>()
        ref2 = FirebaseDatabase.getInstance().getReference("Category").child("ColdBeverages")
        ref2?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
              //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {


                if(p0!!.exists()){

                    for (h in p0.children){
                        val bal = h.getValue(products::class.java)
                        ColdList ?.add(bal!!)
                    }
                    val adapter = ColdAdapter(context!!, ColdList!!)
                    //  val adapter = BalAdapter(context!!,BalList = ArrayList<BalInputDTO>())
                    mRecyclerView2?.setAdapter(adapter)

                }

            }
        })
        return fragmentView2
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ColdFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ColdFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}