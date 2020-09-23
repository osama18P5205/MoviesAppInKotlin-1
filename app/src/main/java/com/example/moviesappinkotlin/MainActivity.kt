package com.example.moviesappinkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    var currentPageNumber = 1
    lateinit var movieAdapter: MovieAdapter
    lateinit var llm: LinearLayoutManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        movieAdapter = MovieAdapter(mutableListOf())
        llm = LinearLayoutManager(this,
            LinearLayoutManager.VERTICAL,
            false)
        rv_movies.adapter = movieAdapter
        rv_movies.layoutManager = llm
        getPopularMovies()
        // This is in development branch
    }

    fun getPopularMovies(){
        Log.d("Popular Movies",
            "here" )
        MovieRespository.fetchPopularMovies(
            currentPageNumber,
        ::onPopularMoviesFetched,
        ::onError)
    }

    private fun onError() {
        Toast
            .makeText(this, "Failed to fetch movies", Toast.LENGTH_SHORT)
            .show()
    }

    private fun onPopularMoviesFetched(list: MutableList<Movie>) {
        movieAdapter.appendMovies(list)
        attachOnScrollListener()
    }

    fun attachOnScrollListener(){
        rv_movies.addOnScrollListener(object: RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItems = llm.itemCount
                val visibleItemsCount = llm.childCount
                val firstVisibleItem = llm.findLastVisibleItemPosition()

                if(firstVisibleItem + visibleItemsCount >= totalItems/2){
                    rv_movies.removeOnScrollListener(this)
                    currentPageNumber++
                    getPopularMovies()
                }
            }
        })
    }

}