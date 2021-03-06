package com.mbaguszulmi.moviedbapp.viewmodel

import android.content.Context
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

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvRepository: TVRepository
) : ViewModel() {

    private val movie: MutableLiveData<Movie?> = MutableLiveData<Movie?>()
    private val tv: MutableLiveData<TV?> = MutableLiveData<TV?>()
    private val isLoading: MutableLiveData<Boolean> = MutableLiveData<Boolean>()

    init {
        isLoading.value = false
    }

    val movieLiveData get(): LiveData<Movie?> = movie
    val tvLiveData get(): LiveData<TV?> = tv

    val movieValue get(): Movie? = movie.value
    val tvValue get(): TV? = tv.value

    val isLoadingLiveData get(): LiveData<Boolean> = isLoading

    fun getMovie(context: Context, id: Int, onFailure: () -> Unit) {
        viewModelScope.launch {
            EspressoIdlingResource.increment()
            isLoading.value = true

            try {
                val movieData = movieRepository.getMovie(id)

                if (movieData != null) {
                    movie.value = movieData
                }
                else {
                    Toast.makeText(context, "Movie not found!", Toast.LENGTH_LONG).show()
                    onFailure()
                }
            } catch (e: Exception) {
                e.printStackTrace()

                Toast.makeText(context, "Error occurred! ${e.message}", Toast.LENGTH_LONG).show()
            }

            isLoading.value = false

            if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                EspressoIdlingResource.decrement()
            }
        }
    }

    fun getTV(context: Context, id: Int, onFailure: () -> Unit) {
        viewModelScope.launch {
            EspressoIdlingResource.increment()
            isLoading.value = true

            try {
                val tvData = tvRepository.getTV(id)

                if (tvData != null) {
                    tv.value = tvData
                }
                else {
                    Toast.makeText(context, "TV show not found!", Toast.LENGTH_LONG).show()
                    onFailure()
                }
            } catch (e: Exception) {
                e.printStackTrace()

                Toast.makeText(context, "Error occurred! ${e.message}", Toast.LENGTH_LONG).show()
            }

            isLoading.value = false
            if (!EspressoIdlingResource.getEspressoIdlingResource().isIdleNow) {
                EspressoIdlingResource.decrement()
            }
        }
    }
}