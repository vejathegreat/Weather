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
import androidx.recyclerview.widget.LinearLayoutManager
import com.velaphi.weather.adapter.CitiesAdapter
import com.velaphi.weather.callbacks.OnCityClick
import com.velaphi.weather.databinding.FragmentOtherCitiesBinding
import com.velaphi.weather.util.getCitiesList
import com.velaphi.weather.viewmodel.OtherCitiesViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_five_days.*

class OtherCitiesFragment : Fragment(), OnCityClick {

    private lateinit var dataBinding: FragmentOtherCitiesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_other_cities, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val otherCitiesAdapter = context?.let { getCitiesList(it) }?.let { CitiesAdapter(it, this) }
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = otherCitiesAdapter
    }

    override fun recyclerviewClick(cityName: String) {
        fragment.findNavController().navigate(
            OtherCitiesFragmentDirections.actionOtherCitiesFragmentToCiyDetailedWeatherFragment(
                cityName
            )
        )
    }
}