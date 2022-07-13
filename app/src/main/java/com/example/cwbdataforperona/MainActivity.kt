package com.example.cwbdataforperona

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.cwbdataforperona.databinding.ActivityMainBinding
import com.example.cwbdataforperona.mint.MinTViewMode


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MinTViewMode

    companion object {
        const val SHARED_IS_OPENED = "isOpened"
    }

    private lateinit var adapter: MainListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        viewModel = ViewModelProvider(this).get(MinTViewMode::class.java)
        adapter = MainListAdapter(this)

        binding.rlMainList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.rlMainList.adapter = adapter

        viewModel.mintlist.observe(this, { newlist ->
            if (newlist.size != 0) {
                adapter.submitList(newlist)
            }
        })

        viewModel.loadingComplete.observe(this, { loadingComplete ->
            binding.cpiProgress.visibility =
                if (loadingComplete) View.GONE else View.VISIBLE
        })

        viewModel.showToast.observe(this, { showToast ->
            if (showToast) {
                showToast()
            }
        })
    }

    private fun showToast() {
        Toast.makeText(this, "歡迎回來!", Toast.LENGTH_SHORT).show()
        viewModel.showToastComplete()
    }
}