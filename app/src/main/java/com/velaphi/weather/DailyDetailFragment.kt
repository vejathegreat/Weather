package com.velaphi.weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.velaphi.weather.databinding.FragmentOneDayDetailBinding
import com.velaphi.weather.model.DailyForecastResponse


class DailyDetailFragment : Fragment() {

    private lateinit var dataBinding: FragmentOneDayDetailBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_one_day_detail, container, false)
        val cityDailyResponse =
            arguments?.getParcelable<DailyForecastResponse.Forecast>(getString(R.string.city_weather_detail))
        dataBinding.detail = cityDailyResponse
        return dataBinding.root
    }



}
