package com.stewartmcm.newyorkschools.data

import kotlinx.coroutines.CoroutineExceptionHandler
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

val nycOpenDataApi: NewYorkSchoolsService = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl("https://data.cityofnewyork.us/")
    .build()
    .create(NewYorkSchoolsService::class.java)

val coroutineExceptionHandler = CoroutineExceptionHandler{_, throwable ->
    throwable.printStackTrace()
}

interface NewYorkSchoolsService {
    @Headers("X-App-Token: EVxixoD9MwwNR2DcLf9dDG8hs")
    @GET("resource/s3k6-pzi2.json")
    fun getSchoolDirectory(): Call<List<SchoolData>>

    @Headers("X-App-Token: EVxixoD9MwwNR2DcLf9dDG8hs")
    @GET("resource/f9bf-2cp4.json")
    fun getSatResult(@Query("dbn") schoolId: String): Call<List<SatResult>>
}