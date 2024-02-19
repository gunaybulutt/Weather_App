package com.gunay.weather_app.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gunay.weather_app.api.model.WeatherData
import com.gunay.weather_app.api.service.WeatherAPI
import com.gunay.weather_app.util.Utils
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class StatePageViewModel: ViewModel() {

    private lateinit var weatherAPI: WeatherAPI
    private var job : Job? = null

    var cityData: MutableLiveData<WeatherData?> = MutableLiveData(null)


    fun refleshData(city : String){

        getDataFromAPI(city)

    }



    fun getDataFromAPI(city: String){
        weatherAPI = Retrofit.Builder()
            .baseUrl(Utils.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherAPI::class.java)

        job = CoroutineScope(Dispatchers.IO).launch {
            val response = weatherAPI.getWeather(city, Utils.API_KEY)

            withContext(Dispatchers.Main + exceptionHandler){
                if(response.isSuccessful){
                    response.body()?.let {
                        cityData.value = it

                    }
                }
            }
        }
    }

    fun kelvinToCelcious(weatherData: WeatherData) : String{
        val celcious = weatherData.main.temp - 273.15
        val string_celcious = String.format("%.1f", celcious)

        return string_celcious
    }

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("Eror: ${throwable.localizedMessage}")
    }

}

