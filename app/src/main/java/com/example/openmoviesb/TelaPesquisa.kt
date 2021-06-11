package com.example.openmoviesb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast

import com.example.openmoviesb.databinding.ActivityTelaPesquisaBinding
import com.google.firebase.auth.FirebaseAuth



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
                url = "https://www.omdbapi.com/?s="+busca+"&apikey=960d95f2&page=2&r=json"
                fetchJson(url)
            }
        }


    }

    fun fetchJson(url: String){
        TelaListaFilmes(url)
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
    private fun TelaListaFilmes(url: String){
        val intent = Intent(this, ListaFilmes::class.java)
        intent.putExtra("url", url)
        startActivity(intent)

    }

    private fun VoltarTelaLogin(){
        val intent = Intent(this, FormLogin::class.java)
        startActivity(intent)
        finish()
    }
}