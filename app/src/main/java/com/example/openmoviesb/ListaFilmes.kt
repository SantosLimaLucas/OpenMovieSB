package com.example.openmoviesb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.openmoviesb.Adapter.FilmesAdapter
import com.google.firebase.auth.FirebaseAuth

import com.example.openmoviesb.databinding.ActivityListaFilmesBinding
import com.example.openmoviesb.Model.Filme
import com.example.openmoviesb.Model.Filmes
import com.example.openmoviesb.OnClick.OnItemClickListener
import com.example.openmoviesb.OnClick.addOnItemClickListener

class ListaFilmes : AppCompatActivity() {

    private lateinit var binding: ActivityListaFilmesBinding
    private val datalist: MutableList<Filmes> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaFilmesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val recycler_filmes = binding.recyclerFilmes
        recycler_filmes.layoutManager = GridLayoutManager(applicationContext,3)
        recycler_filmes.addItemDecoration(DividerItemDecoration(this, OrientationHelper.VERTICAL))
        recycler_filmes.adapter = FilmesAdapter(datalist)

        var url = intent.getStringExtra("url")
        var numberofpages = intent.getIntExtra("pages", 0)

        for(i in 1..numberofpages){
            //setup Android Networking
            AndroidNetworking.initialize(this)
            var urlPages = url + "&page=${i}"
            AndroidNetworking.get(urlPages)
                    .build()
                    .getAsObject(Filme::class.java, object : ParsedRequestListener<Filme> {
                        override fun onResponse(response: Filme) {
                            datalist.addAll(response.search)
                            (recycler_filmes.adapter as FilmesAdapter).notifyDataSetChanged()
                        }
                        override fun onError(anError: ANError?) {
                            println("Ocorreu um erro")
                        }

                    }

                    )
        }







        recycler_filmes.addOnItemClickListener(object: OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {
                    DetalhesFilme(datalist, position)
            }
        })

    }

    private fun DetalhesFilme(datalist: MutableList<Filmes>, position: Int) {
        val intent = Intent(this,DetalhesFilme::class.java)
        intent.putExtra("title", datalist[position].title)
        intent.putExtra("imdbID", datalist[position].imdbID)
        intent.putExtra("poster", datalist[position].poster)
        intent.putExtra("type", datalist[position].type)
        intent.putExtra("year", datalist[position].year)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflate = menuInflater
        inflate.inflate(R.menu.menu_principal,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.deslogar -> {
                FirebaseAuth.getInstance().signOut()
                VoltarTelaLogin()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun VoltarTelaLogin(){
        val intent = Intent(this,FormLogin::class.java)
        startActivity(intent)
        finish()
    }
}

