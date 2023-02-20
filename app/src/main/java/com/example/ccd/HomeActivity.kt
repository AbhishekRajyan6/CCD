package com.example.ccd

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val imageSlider = findViewById<ImageSlider>(R.id.imageslider)
        val imageList = ArrayList<SlideModel>()
        imageList.add(SlideModel(R.drawable.offer1,ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.offer2,ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.offer3,ScaleTypes.FIT))
        imageList.add(SlideModel(R.drawable.offer4,ScaleTypes.CENTER_CROP))
        imageSlider.setImageList(imageList,ScaleTypes.FIT);
    }
}