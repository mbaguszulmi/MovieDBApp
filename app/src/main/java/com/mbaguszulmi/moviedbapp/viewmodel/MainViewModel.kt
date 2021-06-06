package com.mbaguszulmi.moviedbapp.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mbaguszulmi.moviedbapp.helper.EspressoIdlingResource
import com.mbaguszulmi.moviedbapp.model.network.Movie
import com.mbaguszulmi.moviedbapp.model.network.TV
import com.mbaguszulmi.moviedbapp.repository.MovieRepository
import com.mbaguszulmi.moviedbapp.repository.TVRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

const val TAG = "MainViewModel"

@HiltViewModel
class MainViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvRepository: TVRepository
) : ViewModel() {
    private val movies = MutableLiveData<List<Movie>>()
    private val tvs = MutableLiveData<List<TV>>()
    private val isLoadingMovies = MutableLiveData<Boolean>()
    private val isLoadingTVs = MutableLiveData<Boolean>()

    init {
        movies.value = ArrayList()
        tvs.value = ArrayList()
        isLoadingMovies.value = false
        isLoadingTVs.value = false
    }

    val moviesLiveData get(): LiveData<List<Movie>> = movies
    val tvsLiveData get(): LiveData<List<TV>> = tvs

    val movieList get(): List<Movie> = movies.value!!
    val tvList get(): List<TV> = tvs.value!!

    val isLoadingMoviesLiveData get(): LiveData<Boolean> = isLoadingMovies
    val isLoadingTVsLiveData get(): LiveData<Boolean> = isLoadingTVs

    fun refreshMovies(context: Context) {
        viewModelScope.launch {
            EspressoIdlingResource.increment()
            isLoadingMovies.value = true

            try {
                val movieList = movieRepository.getMovies()
                Log.d(TAG, "Movie size = ${movieList.size}")

                movies.value = movieList
                Log.d(TAG, "Movie Live Data size = ${movies.value!!.size}")
            } catch (e: Exception) {
                e.printStackTrace()

                Toast.makeText(context, "Error occurred! ${e.message}", Toast.LENGTH_LONG).show()
            }

            isLoadingMovies.value = false
            if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                EspressoIdlingResource.decrement()
            }
        }
    }

    fun refreshTvs(context: Context) {
        viewModelScope.launch {
            EspressoIdlingResource.increment()
            isLoadingTVs.value = true

            try {
                val tvList = tvRepository.getTVs()

                tvs.value = tvList
            } catch (e: Exception) {
                e.printStackTrace()

                Toast.makeText(context, "Error occurred! ${e.message}", Toast.LENGTH_LONG).show()
            }

            isLoadingTVs.value = false
            if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                EspressoIdlingResource.decrement()
            }
        }
    }
}
