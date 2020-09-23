package com.example.moviesappinkotlin

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object MovieRespository {
    val BASE_URL = "https://api.themoviedb.org/3/"
    val SERVICE: Api


    init {
       val retrofit = Retrofit.Builder()
           .baseUrl(BASE_URL)
           .addConverterFactory(GsonConverterFactory.create())
           .build()

        SERVICE = retrofit.create(Api::class.java)
    }

    fun fetchPopularMovies(page: Int = 1,
                           onSuccess: (moviesList: MutableList<Movie>) -> Unit,
                           onError: () -> Unit
    ){
        SERVICE.getPopularMovies(pageNumber = page)
            .enqueue(object : Callback<com.example.moviesappinkotlin.Response> {
                override fun onFailure(call: Call<com.example.moviesappinkotlin.Response>, t: Throwable) {
                    onError.invoke()
                }

                override fun onResponse(
                    call: Call<com.example.moviesappinkotlin.Response>,
                    response: Response<com.example.moviesappinkotlin.Response>
                ) {
                    if(response.isSuccessful){
                        if(response.body() != null){
                           onSuccess.invoke(response.body()!!.movies)
                        } else {
                           onError.invoke()
                        }

                    }

                }

            })
    }
}