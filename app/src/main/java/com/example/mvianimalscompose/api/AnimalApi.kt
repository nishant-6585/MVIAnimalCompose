package com.example.mvianimalscompose.api

import com.example.mvianimalscompose.model.Animal
import retrofit2.http.GET

interface AnimalApi {

    @GET("animals.json")
    suspend fun getAnimals(): List<Animal>
}