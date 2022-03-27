package com.panduprabs.githubusersapi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.panduprabs.githubusersapi.database.FavoriteUsers
import com.panduprabs.githubusersapi.databinding.ActivityFavoriteBinding
import com.panduprabs.githubusersapi.models.FavoriteViewModel
import com.panduprabs.githubusersapi.models.User
import java.security.Provider

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding : ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter
    private lateinit var viewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val avatar = intent.getStringExtra(DetailUser.EXTRA_AVATAR).toString()

        viewModel = ViewModelProvider(this).get(FavoriteViewModel::class.java)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        adapter.setOnItemClick(object : UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Intent(this@FavoriteActivity, DetailUser::class.java).also {
                    it.putExtra(DetailUser.EXTRA_USERS, data.login)
                    it.putExtra(DetailUser.EXTRA_ID, data.id)
                    it.putExtra(DetailUser.EXTRA_AVATAR, data.avatar_url)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            rvUsers.setHasFixedSize(true)
            rvUsers.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUsers.adapter = adapter
        }

        viewModel.getDataFavoriteUser()?.observe(this) {
            if (it != null) {
                val list = mapping(it)
                adapter.setList(list)
            }
        }

    }

    private fun mapping(users: List<FavoriteUsers>): ArrayList<User>{
        val listUsers = ArrayList<User>()
        for (user in users){
            val mapped = User(
                user.id,
                user.login,
                user.avatar_url,
            )
            listUsers.add(mapped)
        }
        return listUsers
    }
}