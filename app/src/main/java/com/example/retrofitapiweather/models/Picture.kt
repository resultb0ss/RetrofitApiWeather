package com.example.retrofitapiweather.models

import com.example.retrofitapiweather.R
import java.io.Serializable

class Picture (
    val imageView: Int,
    val checkButton: Boolean
): Serializable{

    companion object {
        val viewPagerList = mutableListOf(
            Picture(R.drawable.one, true),
            Picture(R.drawable.two, true),
            Picture(R.drawable.three, false)
        )
    }
}
