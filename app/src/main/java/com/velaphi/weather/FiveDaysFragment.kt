package com.velaphi.weather

import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.velaphi.weather.adapter.HourlyAdapter

import com.velaphi.weather.util.Constant
import com.velaphi.weather.viewmodel.FiveDaysViewModel
import im.delight.android.location.SimpleLocation
import kotlinx.android.synthetic.main.fragment_five_days.*


class FiveDaysFragment : Fragment() {

    var location: SimpleLocation? = null
    var latitude: String? = null
    var longitude: String? = null

    private lateinit var viewModel: FiveDaysViewModel
    private val hourlyAdapter = HourlyAdapter(arrayListOf())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_five_days, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel = ViewModelProviders.of(this).get(FiveDaysViewModel::class.java)

        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = hourlyAdapter

        location = SimpleLocation(context)
        if (!location!!.hasLocationEnabled()) {
            SimpleLocation.openSettings(context)
        } else {
            if (ContextCompat.checkSelfPermission(
                    activity!!,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    activity!!,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    Companion.REQUEST_CODE
                )
            } else {
                location = SimpleLocation(context)
                latitude = String.format("%.6f", location?.latitude)
                longitude = String.format("%.6f", location?.longitude)
                Log.e("LAT1", "" + latitude)
                Log.e("LONG1", "" + longitude)

            }
        }
        viewModel.getForecastFromGps(latitude!!, longitude!!, Constant.METRIC)

        viewModel.forecastData.observe(viewLifecycleOwner, Observer { forecastGps ->
            forecastGps?.let {
                hourlyAdapter.updateHourlyList(forecastGps)

            }
        })

        viewModel.fiveDaysLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if(it){
                   fiveDaysLoading.visibility = View.VISIBLE
                }else{
                    fiveDaysLoading.visibility = View.GONE
                }
            }

        })

    }

    override fun onRequestPermissionsResult(
        requestCode: Int, permissions: Array<out String>, grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                location = SimpleLocation(context)
                latitude = String.format("%.6f", location?.latitude)
                longitude = String.format("%.6f", location?.longitude)

                viewModel.getForecastFromGps(latitude!!, longitude!!, Constant.METRIC)

            } else {
                Toast.makeText(context, getString(R.string.location_not_granted), Toast.LENGTH_LONG)
                    .show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object {
        private const val REQUEST_CODE = 1
    }

}
