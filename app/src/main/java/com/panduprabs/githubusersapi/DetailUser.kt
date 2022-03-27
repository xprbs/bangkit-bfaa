package com.panduprabs.githubusersapi

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.panduprabs.githubusersapi.databinding.ActivityDetailUserBinding
import com.panduprabs.githubusersapi.models.DetailUserViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailUser : AppCompatActivity() {

    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel: DetailUserViewModel
    private lateinit var adapter: UserAdapter

    companion object{
        const val EXTRA_USERS = "extra_users"
        const val EXTRA_ID = "extra_id"
        const val EXTRA_AVATAR = "extra_avatar"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.txt_followers,
            R.string.txt_following
        )
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)
        showLoading(true)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        val actionBar = supportActionBar
        actionBar?.title = "Detail Users"
        actionBar?.setHomeButtonEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.elevation = 0f

        val username = intent.getStringExtra(EXTRA_USERS).toString()
        val id = intent.getIntExtra(EXTRA_ID, 0)
        val avatar = intent.getStringExtra(EXTRA_AVATAR).toString()

        val bundle = Bundle()
        bundle.putString(EXTRA_USERS, username)


        viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)
        viewModel.setUserDetail(username)
        viewModel.getUserDetail().observe(this) {
            if (it != null) {
                showLoading(false)
                binding.apply {
                    detailUsername.text = it.username
                    detailName.text = it.name
                    detailCompanyLocation.text  = "${it.company} -  ${it.location}"
                    detailRepository.text = "${it.repository.toString()} Repository"
                    dataFollowers.text = "${it.totalFollowers} Followers"
                    dataFollowing.text = "${it.totalFollowing} Following"
                    Glide.with(detailAvatar)
                        .load(it.avatar)
                        .into(detailAvatar)
                }
            }else{
                showLoading(false)
                Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
            }
        }

        var _isFavorite = false
        CoroutineScope(Dispatchers.IO).launch {
            val exist = viewModel.checkUsers(id)
            withContext(Dispatchers.Main){
                if (exist != null){
                    if (exist > 0){
                        binding.toggleButton.isChecked = true
                        _isFavorite = true
                    }else{
                        binding.toggleButton.isChecked = false
                        _isFavorite = false
                    }
                }
            }
        }

        binding.toggleButton.setOnClickListener{
            Log.d("clicked", "clicked")
            _isFavorite = !_isFavorite
            if (_isFavorite){
                viewModel.addUsersToFavorite(id, username, avatar)
            }else{
                viewModel.deleteFromFavorite(id)
            }
            binding.toggleButton.isChecked = _isFavorite
        }

        val sectionPager = SectionsPagerAdapter(this, supportFragmentManager, bundle)
        val viewPager: ViewPager2 = findViewById(R.id.view_pager)
        viewPager.adapter = sectionPager
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager){
                tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }



}