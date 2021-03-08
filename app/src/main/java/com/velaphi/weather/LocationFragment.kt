package com.velaphi.weather


import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.velaphi.weather.databinding.FragmentLocationBinding
import com.velaphi.weather.util.Constant
import com.velaphi.weather.util.dateConverter
import com.velaphi.weather.util.timeConverter
import com.velaphi.weather.viewmodel.LocationViewModel
import im.delight.android.location.SimpleLocation
import kotlinx.android.synthetic.main.fragment_location.*

class LocationFragment : Fragment() {

    private lateinit var viewModel: LocationViewModel
    private lateinit var dataBinding: FragmentLocationBinding

    var location: SimpleLocation? = null
    var latitude: String? = null
    var longitude: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_location, container, false)
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(LocationViewModel::class.java)

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
                    REQUEST_CODE
                )
            } else {
                location = SimpleLocation(context)
                latitude = String.format("%.6f", location?.latitude)
                longitude = String.format("%.6f", location?.longitude)
            }
        }
        viewModel.getWeatherDataWithGPS(latitude!!, longitude!!, Constant.METRIC)

        viewModel.locationData.observe(viewLifecycleOwner, Observer { locationGps ->
            locationGps?.let {
                forcast_container.visibility = View.VISIBLE
                dataBinding.locationGPS = locationGps
                dataBinding.textViewTemperature.text = locationGps.main!!.temp.toInt().toString()
                dataBinding.textViewDate.text = dateConverter()
                dataBinding.textViewSunrise.text = timeConverter((locationGps.sys!!.sunrise).toLong())
                dataBinding.textViewSunset.text = timeConverter((locationGps.sys!!.sunset).toLong())
                dataBinding.imageState.setImageResource(resources.getIdentifier(DRAWABLE_PREFIX + locationGps.weather?.get(0)?.icon, DRAWABLE_DEF_TYPE, view.context.packageName))

            }
        })

        viewModel.locationLoading.observe(viewLifecycleOwner, Observer { loading ->
            loading?.let {
                if (it){
                    location_loading.visibility = View.VISIBLE
                    forcast_container.visibility = View.GONE
                }else{
                    location_loading.visibility = View.GONE
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
                viewModel.getWeatherDataWithGPS(latitude!!, longitude!!, Constant.METRIC)

            } else {
                Toast.makeText(context, getString(R.string.location_not_granted), Toast.LENGTH_LONG)
                    .show()
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    companion object{
        const val REQUEST_CODE = 1
        const val DRAWABLE_DEF_TYPE = "drawable"
        const val DRAWABLE_PREFIX = "ic_"
    }

}
