package com.example.exdraweradd.ui.addFrag

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.exdraweradd.R

class AddViewModel: ViewModel() {

    val datas: MutableLiveData<MutableList<CityData>> = MutableLiveData(mutableListOf())

    init{
        datas.value?.apply {
            add(CityData(name = "Seoul", date = "2023년 2월 25일", icon = R.drawable.va_sun, temp = 3.0))
            add(CityData(name = "Busan", date = "2023년 2월 25일", icon = R.drawable.va_sun, temp = 3.2))
            add(CityData(name = "London", date = "2023년 2월 25일", icon = R.drawable.va_sun, temp = 16.5))
            add(CityData(name = "New York", date = "2023년 2월 25일", icon = R.drawable.va_sun, temp = 4.0))
            add(CityData(name = "Los Angeles", date = "2023년 2월 25일", icon = R.drawable.va_sun, temp = 11.0))
            add(CityData(name = "Cidny", date = "2023년 2월 25일", icon = R.drawable.va_sun, temp = 23.0))
            add(CityData(name = "ToKyo", date = "2023년 2월 25일", icon = R.drawable.va_sun, temp = 42.2))
            add(CityData(name = "InChon", date = "2023년 2월 25일", icon = R.drawable.va_sun, temp = 444.5))
        }
    }

    fun addCityData(cityData: CityData) {
        datas.value?.add(cityData)
        datas.postValue(datas.value)
    }

    fun removeCityData(cityData: CityData) {
        datas.value?.remove(cityData)
        datas.postValue(datas.value)
    }
}