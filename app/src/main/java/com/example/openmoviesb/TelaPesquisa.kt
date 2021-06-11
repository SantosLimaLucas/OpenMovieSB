package com.example.openmoviesb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener

import com.example.openmoviesb.databinding.ActivityTelaPesquisaBinding
import com.google.firebase.auth.FirebaseAuth
import com.example.openmoviesb.Model.Filme


class TelaPesquisa : AppCompatActivity() {

    private lateinit var binding: ActivityTelaPesquisaBinding
    private var busca: String =""
    var url: String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaPesquisaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btBusca.setOnClickListener {

            busca = binding.editBusca.text.toString()
            if (busca.isEmpty()) {
                Toast.makeText(this, "Preencha o campo de busca", Toast.LENGTH_SHORT).show()
            }else{
                url = "https://www.omdbapi.com/?s="+busca+"&apikey=960d95f2&r=json"
                fetchJson(url)
            }
        }


    }

    fun fetchJson(url: String){
        AndroidNetworking.initialize(this)
        AndroidNetworking.get(url)
                .build()
                .getAsObject(Filme::class.java, object : ParsedRequestListener<Filme> {
                    override fun onResponse(response: Filme) {
                        var pages = response.totalResults.toInt()/10
                        TelaListaFilmes(url, pages)
                    }
                    override fun onError(anError: ANError?) {
                        println("Ocorreu um erro")
                    }

                }

                )

    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflate = menuInflater
        inflate.inflate(R.menu.menu_principal, menu)
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
    private fun TelaListaFilmes(url: String, pages: Int){
        val intent = Intent(this, ListaFilmes::class.java)
        intent.putExtra("url", url)
        intent.putExtra("pages", pages)
        startActivity(intent)

    }

    private fun VoltarTelaLogin(){
        val intent = Intent(this, FormLogin::class.java)
        startActivity(intent)
        finish()
    }
}