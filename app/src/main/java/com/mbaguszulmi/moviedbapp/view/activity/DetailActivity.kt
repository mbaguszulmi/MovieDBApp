package com.mbaguszulmi.moviedbapp.view.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mbaguszulmi.moviedbapp.R
import com.mbaguszulmi.moviedbapp.databinding.ActivityDetailBinding
import com.mbaguszulmi.moviedbapp.model.network.getGenresStr
import com.mbaguszulmi.moviedbapp.model.network.getPosterUrl
import com.mbaguszulmi.moviedbapp.model.network.getRating
import com.mbaguszulmi.moviedbapp.viewmodel.DetailViewModel

class DetailActivity : AppCompatActivity() {
    companion object {
        const val ITEM_ID = "__item_id__"
        const val ITEM_TYPE = "__item_type__"
        const val ITEM_TYPE_MOVIE = 0
        const val ITEM_TYPE_TV = 1
    }

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding!!

    private var id: Int = 0
    private var itemType: Int = -1

    private lateinit var detailViewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        id = intent.getIntExtra(ITEM_ID, 0)
        itemType = intent.getIntExtra(ITEM_TYPE, -1)

        detailViewModel = ViewModelProvider(this).get(DetailViewModel::class.java)

        initView()
    }

    private fun initView() {
        // actionbar
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = if (itemType == ITEM_TYPE_MOVIE) "Movie Detail" else "TV Detail"
        }

        if (itemType == ITEM_TYPE_MOVIE) {
            detailViewModel.getMovie(this, id) {
                finish()
            }

            detailViewModel.movieLiveData.observe(this, {
                if (it != null) {
                    Glide.with(this)
                        .load(it.getPosterUrl())
                        .centerCrop()
                        .placeholder(R.drawable.placeholder)
                        .into(binding.ivPoster)

                    binding.tvTitle.text = it.title
                    binding.tvGenres.text = it.getGenresStr()
                    binding.tvDurations.text = getString(R.string.text_duration, it.runtime)
                    binding.tvDate.text = it.releaseDate
                    binding.tvOverviewContent.text = it.overview
                    binding.rbarContent.rating = it.getRating().toFloat()
                    binding.btnVisitHomePage.setOnClickListener {_ ->
                        val visitIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it.homepage))
                        startActivity(visitIntent)
                    }
                    binding.btnVisitHomePage.text = getString(R.string.btn_text_visit_movie)

                    binding.btnShare.setOnClickListener {_ ->
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT,
                                getString(R.string.text_share_movie, it.homepage))
                            type = "text/plain"
                        }

                        val shareIntent = Intent.createChooser(sendIntent, null)
                        startActivity(shareIntent)
                    }

                    binding.tvDurations.visibility = View.VISIBLE
                    binding.tvEpisodes.visibility = View.GONE
                    binding.tvSeasons.visibility = View.GONE
                }
            })

            binding.srlDetail.setOnRefreshListener {
                detailViewModel.getMovie(this, id) {
                    finish()
                }
            }
        }
        else {
            detailViewModel.getTV(this, id) {
                finish()
            }

            detailViewModel.tvLiveData.observe(this, {
                if (it != null) {
                    Glide.with(this)
                        .load(it.getPosterUrl())
                        .centerCrop()
                        .placeholder(R.drawable.placeholder)
                        .into(binding.ivPoster)

                    binding.tvTitle.text = it.name
                    binding.tvGenres.text = it.getGenresStr()
                    binding.tvEpisodes.text = getString(R.string.text_episodes, it.numberOfEpisodes)
                    binding.tvSeasons.text = getString(R.string.text_seasons, it.numberOfSeasons)
                    binding.tvDate.text = it.lastAirDate
                    binding.tvOverviewContent.text = it.overview
                    binding.rbarContent.rating = it.getRating().toFloat()
                    binding.btnVisitHomePage.setOnClickListener {_ ->
                        val visitIntent = Intent(Intent.ACTION_VIEW, Uri.parse(it.homepage))
                        startActivity(visitIntent)
                    }
                    binding.btnVisitHomePage.text = getString(R.string.btn_text_visit_tv)

                    binding.btnShare.setOnClickListener {_ ->
                        val sendIntent = Intent().apply {
                            action = Intent.ACTION_SEND
                            putExtra(Intent.EXTRA_TEXT,
                                getString(R.string.text_share_tv, it.homepage))
                            type = "text/plain"
                        }

                        val shareIntent = Intent.createChooser(sendIntent, null)
                        startActivity(shareIntent)
                    }

                    binding.tvDurations.visibility = View.INVISIBLE
                    binding.tvEpisodes.visibility = View.VISIBLE
                    binding.tvSeasons.visibility = View.VISIBLE
                }
            })

            binding.srlDetail.setOnRefreshListener {
                detailViewModel.getTV(this, id) {
                    finish()
                }
            }
        }

        detailViewModel.isLoadingLiveData.observe(this, {
            binding.srlDetail.isRefreshing = it

            binding.btnVisitHomePage.isEnabled = !it
            binding.btnShare.isEnabled = !it

            binding.clDetailWrapper.visibility = if (it) View.GONE else View.VISIBLE
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
