package com.velaphi.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.velaphi.weather.databinding.FragmentCityDetailedWeatherBinding
import com.velaphi.weather.model.WeatherResponse
import com.velaphi.weather.util.dateConverter
import com.velaphi.weather.util.setLayoutBackgroundImage
import com.velaphi.weather.util.timeConverter
import com.velaphi.weather.viewmodel.OtherCitiesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_city_detailed_weather.*

class CityDetailedWeatherFragment : Fragment() {

    private lateinit var viewModel: OtherCitiesViewModel
    private lateinit var dataBinding: FragmentCityDetailedWeatherBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(
                inflater,
                R.layout.fragment_city_detailed_weather,
                container,
                false
            )
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(OtherCitiesViewModel::class.java)
        val cityName =
            CityDetailedWeatherFragmentArgs.fromBundle(requireArguments()).cityName
        viewModel.getWeatherByCityName(cityName)
        setupObserver()
    }

    private fun setupObserver() {
        viewModel.cityDataLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it) {
                    city_loading.visibility = View.VISIBLE
                    city_container.visibility = View.GONE
                } else {
                    city_loading.visibility = View.GONE
                    city_container.visibility = View.VISIBLE
                }
            }
        })
        viewModel.cityData.observe(viewLifecycleOwner, Observer {
            populateData(it)
        })
    }

    private fun populateData(weatherResponse: WeatherResponse) {
        dataBinding.textViewTemperature.text = weatherResponse.main!!.temp.toInt().toString()
        dataBinding.textViewDate.text = dateConverter()
        dataBinding.textViewSunrise.text = timeConverter((weatherResponse.sys!!.sunrise).toLong())
        dataBinding.textViewSunset.text = timeConverter((weatherResponse.sys!!.sunset).toLong())
        dataBinding.imageState.setImageResource(
            resources.getIdentifier(
                "ic_" + weatherResponse.weather?.get(
                    0
                )?.icon, "drawable", view?.context?.packageName
            )
        )
        dataBinding.textViewPressure.text = weatherResponse.main!!.pressure.toString()
        dataBinding.textViewWind.text = weatherResponse.wind?.speed.toString()
        dataBinding.textViewHumidity.text = weatherResponse.main?.humidity.toString()
        dataBinding.textViewVisibility.text = weatherResponse.visibility.toString()
        dataBinding.textViewState.text = weatherResponse.weather?.get(0)?.description
        dataBinding.textViewCityName.text = weatherResponse.name
        setLayoutBackgroundImage(city_container, weatherResponse.weather?.get(0)?.icon)
        back_button.setOnClickListener {
            fragment.findNavController().navigate(
                CityDetailedWeatherFragmentDirections.actionCiyDetailedWeatherFragmentToOtherCitiesFragment()
            )
        }
    }
}