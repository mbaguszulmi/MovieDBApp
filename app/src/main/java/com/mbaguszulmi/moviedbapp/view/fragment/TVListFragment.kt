package com.mbaguszulmi.moviedbapp.view.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.mbaguszulmi.moviedbapp.R
import com.mbaguszulmi.moviedbapp.databinding.FragmentTvListBinding
import com.mbaguszulmi.moviedbapp.model.network.TV
import com.mbaguszulmi.moviedbapp.view.activity.DetailActivity
import com.mbaguszulmi.moviedbapp.view.adapter.MovieTVListAdapter
import com.mbaguszulmi.moviedbapp.viewmodel.MainViewModel

/**
 * A simple [Fragment] subclass.
 * Use the [TVListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class TVListFragment : Fragment() {
    private var _binding: FragmentTvListBinding? = null
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
        _binding = FragmentTvListBinding.inflate(layoutInflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView()
    }

    private fun initView() {
        initAdapter()

        mainViewModel.tvsLiveData.observe(requireActivity(), {
            movieTVListAdapter.updateTVs(it as MutableList<TV>)
        })

        mainViewModel.isLoadingTVsLiveData.observe(requireActivity(), {
            binding.srlTvs.isRefreshing = it
        })

        binding.srlTvs.setOnRefreshListener {
            mainViewModel.refreshTvs(requireContext())
        }
    }

    private fun initAdapter() {
        movieTVListAdapter = MovieTVListAdapter(null, ArrayList()) {
            val moveIntent = Intent(activity, DetailActivity::class.java)
                .putExtra(DetailActivity.ITEM_ID, it)
                .putExtra(DetailActivity.ITEM_TYPE, DetailActivity.ITEM_TYPE_TV)

            startActivity(moveIntent)
        }

        binding.rvTvs.layoutManager = LinearLayoutManager(requireContext())
        binding.rvTvs.adapter = movieTVListAdapter
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @return A new instance of fragment TVListFragment.
         */
        @JvmStatic
        fun newInstance() = TVListFragment()
    }
}