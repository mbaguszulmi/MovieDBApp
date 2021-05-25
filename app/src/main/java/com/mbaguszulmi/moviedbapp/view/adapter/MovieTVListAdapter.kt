package com.mbaguszulmi.moviedbapp.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mbaguszulmi.moviedbapp.R
import com.mbaguszulmi.moviedbapp.databinding.ListitemMovieBinding
import com.mbaguszulmi.moviedbapp.model.network.Movie
import com.mbaguszulmi.moviedbapp.model.network.TV
import com.mbaguszulmi.moviedbapp.model.network.getPosterUrl
import com.mbaguszulmi.moviedbapp.model.network.getRating

class MovieTVListAdapter(private val movies: MutableList<Movie>?, private val tvs: MutableList<TV>?,
        val onClickListener: (id: Int) -> Unit): RecyclerView.Adapter<MovieTVListAdapter.ViewHolder>() {

    inner class ViewHolder(val binding: ListitemMovieBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(id: Int, title: String, overview: String, rating: Float, posterUrl: String) {
            binding.root.setOnClickListener {
                onClickListener(id)
            }

            binding.tvTitle.text = title
            binding.tvOverview.text = overview
            binding.rbarMain.rating = rating
            Glide.with(binding.root.context)
                .load(posterUrl)
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .into(binding.ivPoster)
        }
    }

    fun updateMovies(newMovies: MutableList<Movie>) {
        movies?.apply {
            clear()
            addAll(newMovies)
        }

        notifyDataSetChanged()
    }

    fun updateTVs(newTVs: MutableList<TV>) {
        tvs?.apply {
            clear()
            addAll(newTVs)
        }

        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ListitemMovieBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (movies != null) {
            val movie = movies[position]
            holder.bind(movie.id, movie.title, movie.overview, movie.getRating().toFloat(),
                movie.getPosterUrl())
        }
        else if (tvs != null) {
            val tv = tvs[position]
            holder.bind(tv.id, tv.name, tv.overview, tv.getRating().toFloat(), tv.getPosterUrl())
        }
    }

    override fun getItemCount(): Int = movies?.size ?: (tvs?.size ?: 0)

}