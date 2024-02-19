package com.gunay.weather_app.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.gunay.weather_app.databinding.FragmentStatePageBinding
import com.gunay.weather_app.viewmodel.StatePageViewModel


class StatePage : Fragment() {

    private lateinit var binding: FragmentStatePageBinding
    private lateinit var viewModel: StatePageViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStatePageBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = requireContext().getSharedPreferences("mySharedPreferences", Context.MODE_PRIVATE)
        val myValue = sharedPref.getString("myKey", "defaultValue").toString()



        viewModel = ViewModelProvider(this).get(StatePageViewModel::class.java)
        viewModel.refleshData(myValue)
        val editor = sharedPref.edit()
        editor.clear()
        editor.apply()
        observeLiveData()


        binding.backButton.setOnClickListener {
            val action = StatePageDirections.actionStatePageToSearchPage()
            Navigation.findNavController(it).navigate(action)
        }


    }

    private fun observeLiveData(){
        viewModel.cityData.observe(viewLifecycleOwner, Observer {
            it?.let{
                binding.textViewCity.text = it.name
                binding.textViewTemperature.text = "${viewModel.kelvinToCelcious(it)}°C"
                binding.textState.text = it.weather.firstOrNull()?.main
                val iconUrl = "https://openweathermap.org/img/w/${it.weather.firstOrNull()?.icon}.png"
                Glide.with(this)
                    .load(iconUrl)
                    .into(binding.imageViewWeatherIcon)

            }
        })
    }

}