package com.rncp.kotlinmovielist.view

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.rncp.kotlinmovielist.R
import com.rncp.kotlinmovielist.adapter.MoviesAdapter
import com.rncp.kotlinmovielist.model.AllMovies
import com.rncp.kotlinmovielist.network.ApiClient
import com.rncp.kotlinmovielist.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var request: ApiInterface
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.title = "Most Popular Movies"

        val colorDrawable = ColorDrawable(Color.parseColor("#0F9D58"))
        supportActionBar!!.setBackgroundDrawable(colorDrawable)

        recyclerView = findViewById(R.id.recyclerView)

        request = ApiClient.getUsersService()
        val call = request.getMostPopularMovies(getString(R.string.api_key))
        showMovies(call)

        val floatBtn =  findViewById<FloatingActionButton>(R.id.floatingActionButton)
        floatBtn.setOnClickListener {
            showMsg("Exit Application")
            finish()
        }
    }

    private fun showMovies(call : Call<AllMovies>){
        call.enqueue(object : Callback<AllMovies>{
            override fun onResponse(call: Call<AllMovies>, response: Response<AllMovies>) {
                if (response.isSuccessful){
                    recyclerView.apply {
                        setHasFixedSize(true)
                        layoutManager = LinearLayoutManager(this@MainActivity)
                        adapter = MoviesAdapter(response.body()!!.results)
                    }
                }
            }
            override fun onFailure(call: Call<AllMovies>, t: Throwable) {
                showMsg("App Failure")
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle item selection
        return when (item.itemId) {
            R.id.settings -> {
                true
            }

            R.id.top -> {
                val call = request.getTopRatedMovies(getString(R.string.api_key))
                showMovies(call)
                supportActionBar?.title = "Top Rated Movies"
                true
            }
            R.id.popular -> {
                //showMsg("Neo")
                val call = request.getMostPopularMovies(getString(R.string.api_key))
                supportActionBar?.title = "Most Popular Movies"
                showMovies(call)
                true
            }

            R.id.about -> {
                showMsg("Developed by Neyomal")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showMsg(msg: String) {
        Snackbar.make(findViewById(android.R.id.content), msg, Snackbar.LENGTH_SHORT).show()
    }

}