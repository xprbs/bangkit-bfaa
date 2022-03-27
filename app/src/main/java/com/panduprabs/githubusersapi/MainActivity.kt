package com.panduprabs.githubusersapi

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.panduprabs.githubusersapi.databinding.ActivityMainBinding
import com.panduprabs.githubusersapi.models.MainViewModel
import com.panduprabs.githubusersapi.models.User

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = "Github User's"

        val searchView: SearchView = findViewById(R.id.search_view)
        val id = searchView.context.resources.getIdentifier("android:id/search_src_text", null, null)
        val textView = searchView.findViewById<View>(id) as TextView
        textView.setTextColor(resources.getColor(R.color.white_custom))
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14F)
        textView.setTypeface(resources.getFont(R.font.kumbh_sans))

        adapter = UserAdapter()
        adapter.setOnItemClick(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity, DetailUser::class.java).also {
                    it.putExtra(DetailUser.EXTRA_USERS, data.login)
                    it.putExtra(DetailUser.EXTRA_ID, data.id)
                    it.putExtra(DetailUser.EXTRA_AVATAR, data.avatar_url)
                    Log.d("AVATARRR", data.avatar_url)
                    startActivity(it)
                }
            }
        })



        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            MainViewModel::class.java
        )
        binding.apply {
            rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUsers.adapter = adapter
            rvUsers.setHasFixedSize(true)

            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextChange(keyword: String?): Boolean {
                    if (keyword != null && keyword.isNotEmpty()){
                        searchUser()
                    }
                    return true
                }

                override fun onQueryTextSubmit(keyword: String?): Boolean {
                    return false
                }
            })

            viewModel.getSearchUser().observe(this@MainActivity){
                if (it != null){
                    showLoading(false)
                    adapter.setList(it)
                    binding.rvUsers.visibility = View.VISIBLE
                }else{
                    Toast.makeText(this@MainActivity, "Data not found / network failure", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite -> {
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun searchUser(){
        binding.apply {
            val keyword = searchView.query.toString()
            if(keyword.isEmpty()) return
            binding.rvUsers.visibility = View.GONE
            showLoading(true)
            viewModel.setSearchUser(keyword)
        }
    }
    private fun showLoading(isLoading: Boolean){
        if (isLoading){
            binding.progressBar.visibility = View.VISIBLE
        }else{
            binding.progressBar.visibility = View.GONE
        }
    }
}