package com.example.ccd

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

var fragmentView : View? = null
var firedatabase : FirebaseDatabase? = null
var Hotlist : ArrayList<products> ? = null
var ref : DatabaseReference? = null
var mRecyclerView : RecyclerView? =null
/**
 * A simple [Fragment] subclass.
 * Use the [HotFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HotFragment : Fragment() {
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
        fragmentView= LayoutInflater.from(activity).inflate(R.layout.fragment_hot, container, false)
      //  val  view = inflater.inflate(R.layout.fragment_hot, container, false)
        firedatabase = FirebaseDatabase.getInstance()
        mRecyclerView = fragmentView?.findViewById(R.id.recycleview)
        mRecyclerView?.setHasFixedSize(true)
        mRecyclerView?.layoutManager = LinearLayoutManager(context)


        Hotlist = arrayListOf<products>()
        ref = FirebaseDatabase.getInstance().getReference("Category").child("HotBeverages")
        ref?.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {


                if(p0!!.exists()){

                    for (h in p0.children){
                        val bal = h.getValue(products::class.java)
                        Hotlist ?.add(bal!!)
                    }
                    val adapter = HotAdapter(context!!, Hotlist!!)
                  //  val adapter = BalAdapter(context!!,BalList = ArrayList<BalInputDTO>())
                    mRecyclerView?.setAdapter(adapter)

                }

            }
        })
        return fragmentView
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HotFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HotFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}