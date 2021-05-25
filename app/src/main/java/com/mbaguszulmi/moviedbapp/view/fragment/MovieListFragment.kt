package com.mbaguszulmi.moviedbapp.view.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mbaguszulmi.moviedbapp.R
import com.mbaguszulmi.moviedbapp.databinding.FragmentMovieListBinding
import com.mbaguszulmi.moviedbapp.model.network.Movie
import com.mbaguszulmi.moviedbapp.view.activity.DetailActivity
import com.mbaguszulmi.moviedbapp.view.adapter.MovieTVListAdapter
import com.mbaguszulmi.moviedbapp.viewmodel.MainViewModel

const val TAG = "MovieListFragment"

/**
 * A simple [Fragment] subclass.
 * Use the [MovieListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MovieListFragment : Fragment() {
    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!

    private lateinit var movieTVListAdapter: MovieTVListAdapter
    private val mainViewModel by activityViewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieListBinding.inflate(layoutInflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        initAdapter()

        mainViewModel.moviesLiveData.observe(requireActivity(), {
            movieTVListAdapter.updateMovies(it as MutableList<Movie>)
            Log.d(TAG, "Adapter items size = ${movieTVListAdapter.itemCount}")
        })

        mainViewModel.isLoadingMoviesLiveData.observe(requireActivity(), {
            binding.srlMovies.isRefreshing = it
        })

        binding.srlMovies.setOnRefreshListener {
            mainViewModel.refreshMovies(requireContext())
        }
    }

    private fun initAdapter() {
        movieTVListAdapter = MovieTVListAdapter(ArrayList(), null) {
            val moveIntent = Intent(activity, DetailActivity::class.java)
                .putExtra(DetailActivity.ITEM_ID, it)
                .putExtra(DetailActivity.ITEM_TYPE, DetailActivity.ITEM_TYPE_MOVIE)

            startActivity(moveIntent)
        }

        binding.rvMovies.layoutManager = LinearLayoutManager(requireContext())
        binding.rvMovies.adapter = movieTVListAdapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment MovieListFragment.
         */
        @JvmStatic
        fun newInstance() = MovieListFragment()
    }
}