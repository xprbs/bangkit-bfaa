package com.panduprabs.githubusersapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter(activity: AppCompatActivity, fragment: FragmentManager, data: Bundle) : FragmentStateAdapter(activity){

    private var fragmentBundle: Bundle
    init{
        fragmentBundle = data
    }
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingsFragment()
        }

        fragment?.arguments = this.fragmentBundle
        return fragment as Fragment
    }

}