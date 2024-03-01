package com.stewartmcm.newyorkschools.data

import com.google.gson.annotations.SerializedName

data class SchoolData(
    @SerializedName("dbn") val id: String,
    @SerializedName("school_name") val name: String,
    @SerializedName("boro") val boro: String
)

data class SatResult(
    @SerializedName("dbn") val id: String,
    @SerializedName("school_name") val schoolName: String,
    @SerializedName("sat_critical_reading_avg_score") val readingAvgScore: String,
    @SerializedName("sat_math_avg_score") val mathAvgScore: String,
    @SerializedName("sat_writing_avg_score") val writingAvgScore: String
)