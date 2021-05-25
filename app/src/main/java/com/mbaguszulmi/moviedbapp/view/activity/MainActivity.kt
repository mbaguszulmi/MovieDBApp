package com.mbaguszulmi.moviedbapp.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.mbaguszulmi.moviedbapp.R
import com.mbaguszulmi.moviedbapp.databinding.ActivityMainBinding
import com.mbaguszulmi.moviedbapp.view.adapter.MainTabAdapter
import com.mbaguszulmi.moviedbapp.view.fragment.MovieListFragment
import com.mbaguszulmi.moviedbapp.view.fragment.TVListFragment
import com.mbaguszulmi.moviedbapp.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        initView()
    }

    private fun initView() {
        initTabAdapter()

        // get data
        mainViewModel.refreshMovies(this)
        mainViewModel.refreshTvs(this)
    }

    private fun initTabAdapter() {
        @StringRes
        val tabTitles = intArrayOf(R.string.tab_movies, R.string.tab_tvs)
        val tabAdapter = MainTabAdapter(this, this,
            arrayOf(MovieListFragment.newInstance(), TVListFragment.newInstance()))

        binding.vpMain.adapter = tabAdapter
        TabLayoutMediator(binding.tlMain, binding.vpMain) {
            tab, position -> tab.text = getString(tabTitles[position])
        }.attach()
    }
}