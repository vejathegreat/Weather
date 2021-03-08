package com.velaphi.weather.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.velaphi.weather.R
import com.velaphi.weather.callbacks.OnCityClick
import com.velaphi.weather.databinding.OtherCitiesItemBinding
import com.velaphi.weather.model.CityModel

class CitiesAdapter(private val citiesList: List<CityModel>, private val listener: OnCityClick) :
    RecyclerView.Adapter<CitiesAdapter.CitiesViewHolder>() {

    class CitiesViewHolder(var view: OtherCitiesItemBinding) :
        RecyclerView.ViewHolder(view.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CitiesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = DataBindingUtil.inflate<OtherCitiesItemBinding>(
            inflater,
            R.layout.other_cities_item,
            parent,
            false
        )
        return CitiesViewHolder(view)
    }

    override fun getItemCount() = citiesList.size

    override fun onBindViewHolder(holder: CitiesViewHolder, position: Int) {
        val cityModel = citiesList[position]
        val context = holder.itemView.context
        holder.view.cityModel = citiesList[position]
        holder.view.textViewCityName.text = cityModel.cityName
        holder.view.imageViewCityBackground.setImageDrawable(context.getDrawable(cityModel.background))
        holder.itemView.setOnClickListener {
            listener.recyclerviewClick(cityModel.cityName)
        }
    }

}