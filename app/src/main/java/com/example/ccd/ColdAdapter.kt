package com.example.ccd

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class ColdAdapter(val context: Context, val Coldlist : ArrayList<products>) :
    RecyclerView.Adapter<ColdAdapter.Holder>() {
    inner class Holder(view: View) : RecyclerView.ViewHolder(view!!) {
        val ProductImage = view.findViewById<CircleImageView>(R.id.pro_img)
        val ProductName  = view.findViewById<TextView>(R.id.prod_name)
        val ProductDescription  = view.findViewById<TextView>(R.id.prod_des)
        val ProductPrice = view.findViewById<TextView>(R.id.prod_price)
        fun bind(Cold: products,context: Context){

            ProductName.text = Cold.productName
            ProductDescription.text = Cold.productDescription
            ProductPrice.text = "$ "+Cold.productPrice

            Glide.with(context)
                .load(Cold.productImg)
                .into(ProductImage)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.productitem, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(Coldlist[position], context)
    }

    override fun getItemCount(): Int {
        return Coldlist.size
    }
}