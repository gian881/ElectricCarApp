package com.example.electriccarapp.data

import com.example.electriccarapp.domain.Car
import retrofit2.Call
import retrofit2.http.GET

interface CarsApi {
    @GET("cars.json")
    fun getAllCars():Call<List<Car>>
}