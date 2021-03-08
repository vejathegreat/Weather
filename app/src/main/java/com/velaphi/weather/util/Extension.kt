package com.velaphi.weather.util

import android.content.Context
import android.content.res.TypedArray
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.velaphi.weather.R
import com.velaphi.weather.model.CityModel
import java.text.SimpleDateFormat
import java.util.*


fun dateConverter(): String {
    val date = Calendar.getInstance().time
    val converter = SimpleDateFormat("EEE, d MMM yyyy", Locale("en"))

    return converter.format(date)
}

fun timeConverter(time: Long): String {
    val converter = SimpleDateFormat("hh:mm a")

    return converter.format(Date(time*1000))
}

fun dayConverter(time: Long) : String{
    val converter = SimpleDateFormat("EEE, d MMM yyyy hh:mm a")

    return converter.format(Date(time*1000))
}

@BindingAdapter("android:dayConverter")
fun convertToDay(view: TextView, value:Long){
    val converter = SimpleDateFormat("EEE, d MMM yyyy hh:mm a")
    val convertedDay = converter.format(Date(value*1000))
    view.text = convertedDay
}

@BindingAdapter("android:converterInt")
fun convertToInt(view: TextView, value:Double){
    val valueInt = value.toInt()
    val valueString = valueInt.toString()
    view.text = valueString
}

@BindingAdapter("layoutBackgroundImage")
fun setLayoutBackgroundImage(constraintLayout: ConstraintLayout, url: String?){
    when(url){
        "01d","02d","03d","04d","09d","10d","11d","13d","50d" -> {
            constraintLayout.setBackgroundResource(constraintLayout.resources.getIdentifier("ic_background_daylight","drawable", constraintLayout.context.packageName))
        }
        "01n","02n","03n","04n","09n","10n","11n","13n","50n" -> {
            constraintLayout.setBackgroundResource(constraintLayout.resources.getIdentifier("ic_background_night","drawable", constraintLayout.context.packageName))
        }
    }

}

@BindingAdapter("imageResource")
fun setImageResource(imageview: ImageView, url:String){
    when(url){
        "01d" -> imageview.setImageDrawable(imageview, R.drawable.ic_01d)
        "01n" -> imageview.setImageDrawable(imageview, R.drawable.ic_01n)
        "02d" -> imageview.setImageDrawable(imageview, R.drawable.ic_02d)
        "02n" -> imageview.setImageDrawable(imageview, R.drawable.ic_02n)
        "03d" -> imageview.setImageDrawable(imageview, R.drawable.ic_03d)
        "03n" -> imageview.setImageDrawable(imageview, R.drawable.ic_03n)
        "04d" -> imageview.setImageDrawable(imageview, R.drawable.ic_04d)
        "04n" -> imageview.setImageDrawable(imageview, R.drawable.ic_04n)
        "09d" -> imageview.setImageDrawable(imageview, R.drawable.ic_09d)
        "09n" -> imageview.setImageDrawable(imageview, R.drawable.ic_09n)
        "10d" -> imageview.setImageDrawable(imageview, R.drawable.ic_10d)
        "10n" -> imageview.setImageDrawable(imageview, R.drawable.ic_10n)
        "11d" -> imageview.setImageDrawable(imageview, R.drawable.ic_11d)
        "11n" -> imageview.setImageDrawable(imageview, R.drawable.ic_11n)
        "13d" -> imageview.setImageDrawable(imageview, R.drawable.ic_13d)
        "13n" -> imageview.setImageDrawable(imageview, R.drawable.ic_13n)
        "50d" -> imageview.setImageDrawable(imageview, R.drawable.ic_50d)
        "50n" -> imageview.setImageDrawable(imageview, R.drawable.ic_50n)
    }

}

private fun ImageView.setImageDrawable(view:ImageView,image: Int) {
        Glide.with(context).load(image).into(view)
}

fun getCitiesList(context: Context): List<CityModel> {

    val cityList: MutableList<CityModel> = mutableListOf()
    val citiesNamesList = context.resources.getStringArray(R.array.city_name)
    val images: TypedArray = context.resources.obtainTypedArray(R.array.city_background)

    for (i in citiesNamesList.indices){
        cityList.add(CityModel(citiesNamesList[i], images.getResourceId(i, -1)))
    }
    images.recycle()
    return cityList
}