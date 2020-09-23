package com.example.moviesappinkotlin

import com.google.gson.annotations.SerializedName

data class Response (
    @SerializedName("results") val movies : MutableList<Movie>,
    @SerializedName("page") val currentPage : Int,
    @SerializedName("total_pages") val totalPages : Int
)