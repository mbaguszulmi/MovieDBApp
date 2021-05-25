package com.mbaguszulmi.moviedbapp.view.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainTabAdapter(private val context: Context, fa: FragmentActivity,
                     private val items: Array<Fragment>): FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = items.size

    override fun createFragment(position: Int): Fragment = items[position]
}