package com.example.retrofitapiweather.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.retrofitapiweather.R
import com.example.retrofitapiweather.databinding.FragmentDetailBinding
import com.example.retrofitapiweather.utils.RetrofitInstance
import com.squareup.picasso.Picasso
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import kotlin.math.roundToInt


class DetailFragment : Fragment() {

    private lateinit var binding: FragmentDetailBinding
    private var position: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentDetailBinding.inflate(inflater, container, false)
        position = arguments?.getString("position")

        getCurrentWeather()

        return binding.root
    }

    @SuppressLint("SimpleDateFormat")
    private fun getCurrentWeather() {
        GlobalScope.launch(Dispatchers.IO) {
            val response = try {

                position?.let {
                    RetrofitInstance.api.getCurrentWeatherAboutCity(
                        it, "metric", requireContext().getString(R.string.api_key)
                    )
                }

            } catch (e: IOException) {
                Toast.makeText(
                    requireContext(), "app error ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
                return@launch
            } catch (e: HttpException) {
                Toast.makeText(
                    requireContext(), "http error ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
                return@launch
            }
            if (response != null) {
                if (response.isSuccessful && response.body() != null) {
                    withContext(Dispatchers.Main) {
                        val data = response.body()
                        binding.detailFragmentCityNameTextViewTV.text = data!!.name

                        binding.detailFragmentMainTemperatureTextViewTV.text =
                            "${data.main.temp.roundToInt()}\u00B0"
                        Log.d("@@@", "Main temp ${data.main.temp.roundToInt()}")

                        binding.detailFragmentWeatherConditionDescriptionTextViewTV.text =
                            data.weather[0].main

                        when(data.weather[0].main){
                            "Rain" -> binding.detailFragmentMainWeatherImageViewIV
                                .setImageResource(R.drawable.rainy)
                            "Snow" -> binding.detailFragmentMainWeatherImageViewIV
                                .setImageResource(R.drawable.snow)
                            "Clouds" -> binding.detailFragmentMainWeatherImageViewIV
                                .setImageResource(R.drawable.cloudy)
                            "Clear" -> binding.detailFragmentMainWeatherImageViewIV
                                .setImageResource(R.drawable.sunny)
                            "Thunderstorm" -> binding.detailFragmentMainWeatherImageViewIV
                                .setImageResource(R.drawable.storm)
                            "Atmosphere" -> binding.detailFragmentMainWeatherImageViewIV
                                .setImageResource(R.drawable.clear)
                            "Drizzle" -> binding.detailFragmentMainWeatherImageViewIV
                                .setImageResource(R.drawable.rainy)
                            else ->
                                binding.detailFragmentMainWeatherImageViewIV.setImageResource(R.drawable.sunny)
                        }

                        val convertPressure = (data.main.pressure / 1.33).toInt()
                        binding.detailFragmentPressureTextViewTV.text = "$convertPressure кПа"

                        binding.detailFragmentMainTempMinTV.text =
                            data.main.temp_min.roundToInt().toString()


                        binding.detailFragmentMainTempMaxTV.text =
                            data.main.temp_max.roundToInt().toString()

                        val dateFormatSunrise = SimpleDateFormat("hh:mm")
                        val timeSunrise = dateFormatSunrise.format(data.sys.sunrise)
                        binding.detailFragmentSunriseTimeTextViewTV.text = timeSunrise

                        val dateFormatSunset = SimpleDateFormat("hh:mm")
                        val timeSunset = dateFormatSunset.format(data.sys.sunset)
                        binding.detailFragmentSunsetTimeTextViewTV.text = timeSunset

                        binding.detailFragmentHumidityTextViewTV.text = "${data.main.humidity} %"
                        binding.detailFragmentWindSpeedTextViewTV.text = "${data.wind.speed} м/с"

                        val dateFormat = SimpleDateFormat("dd/M hh:mm")
                        val currentDate = dateFormat.format(Date())
                        binding.detailFragmentCurrentDateTextViewTV.text = currentDate.toString()

                        Log.d("@@@","Weather in $position is ${data.weather}")
                    }
                }
            }
        }
    }

}