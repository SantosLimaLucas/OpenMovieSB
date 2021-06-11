package com.example.openmoviesb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.OrientationHelper
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.openmoviesb.Adapter.FilmesAdapter
import com.example.openmoviesb.FormLogin
import com.example.openmoviesb.DetalhesFilme
import com.example.openmoviesb.R
import com.google.firebase.auth.FirebaseAuth

import com.example.openmoviesb.databinding.ActivityListaFilmesBinding
import com.marcos.netflixclone.Model.Filme
import com.marcos.netflixclone.Model.Filmes
import com.marcos.netflixclone.OnClick.OnItemClickListener
import com.marcos.netflixclone.OnClick.addOnItemClickListener

class ListaFilmes : AppCompatActivity() {

    private lateinit var binding: ActivityListaFilmesBinding
    private val datalist: MutableList<Filmes> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaFilmesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        println("Chegouaquiiiiiiiiiiiiiiiii")
        val recycler_filmes = binding.recyclerFilmes
        recycler_filmes.layoutManager = GridLayoutManager(applicationContext,3)
        recycler_filmes.addItemDecoration(DividerItemDecoration(this, OrientationHelper.VERTICAL))
        recycler_filmes.adapter = FilmesAdapter(datalist)
        println("O dataList é: " + datalist)
        println("Passou tudo mas num atualiza")

        var url = intent.getStringExtra("url")
        //setup Android Networking
        AndroidNetworking.initialize(this)

        AndroidNetworking.get(url)
                .build()
                .getAsObject(Filme::class.java, object : ParsedRequestListener<Filme> {
                    override fun onResponse(response: Filme) {
                        datalist.addAll(response.search)
                        (recycler_filmes.adapter as FilmesAdapter).notifyDataSetChanged()
                        println("O dataList é: " + datalist)
                    }
                    override fun onError(anError: ANError?) {
                        println("Ocorreu um erro")
                    }

                }

                )




        recycler_filmes.addOnItemClickListener(object: OnItemClickListener {
            override fun onItemClicked(position: Int, view: View) {

                when{
                    position == 0 -> DetalhesFilme()
                }
            }
        })

    }

    private fun DetalhesFilme(){
        val intent = Intent(this,DetalhesFilme::class.java)
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