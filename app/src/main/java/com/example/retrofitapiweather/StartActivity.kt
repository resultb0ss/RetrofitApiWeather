package com.example.retrofitapiweather

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.retrofitapiweather.fragments.MainFragment

class StartActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)



        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(R.id.containerID, MainFragment())
                .commit()
        }
    }


}