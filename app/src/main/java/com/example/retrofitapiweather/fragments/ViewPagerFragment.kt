package com.example.retrofitapiweather.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.retrofitapiweather.StartActivity
import com.example.retrofitapiweather.databinding.FragmentViewPagerBinding
import com.example.retrofitapiweather.models.Picture

class ViewPagerFragment : Fragment() {

    private lateinit var binding: FragmentViewPagerBinding
    private var check = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentViewPagerBinding.inflate(inflater, container, false)

        val viewPageItem = arguments?.getSerializable("vp") as Picture
        binding.viewPagerImageView.setImageResource(viewPageItem.imageView)
        check = viewPageItem.checkButton

        if (!check) {
            binding.viewPagerButton.visibility = View.VISIBLE
            binding.viewPagerButton.setOnClickListener {
                startActivity((Intent(activity, StartActivity::class.java)))
            }
        }

        return binding.root
    }

}