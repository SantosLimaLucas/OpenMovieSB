package com.example.openmoviesb

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.openmoviesb.Model.Filmes
import com.example.openmoviesb.databinding.ActivityListaFilmesBinding
import com.example.openmoviesb.databinding.ActivityTelaPesquisaBinding
import com.google.firebase.auth.FirebaseAuth
import org.json.JSONArray
import org.json.JSONStringer
import java.net.HttpURLConnection
import java.net.URL

class TelaPesquisa : AppCompatActivity() {

    private lateinit var binding: ActivityTelaPesquisaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTelaPesquisaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        val busca = binding.editBusca.text.toString()
        val url = "http://www.omdbapi.com/?s=" + busca + "&apikey=960d95f2&type=movie&r=json"

        AsyncTaskHandleJson().execute(url)
    }

    inner class AsyncTaskHandleJson : AsyncTask<String, String, String>(){
        override fun doInBackground(vararg url: String?): String {
            var text: String
            val connection = URL(url[0]).openConnection() as HttpURLConnection
            try {
                connection.connect()
                text = connection.inputStream.use { it.reader().use{reader -> reader.readText()} }
            }finally {
                connection.disconnect()
            }
            return text
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            handleJson(result)
        }
    }
    private fun handleJson(jsonString: String?){
        val jsonArray = JSONArray(jsonString)
        val list = ArrayList<Filmes>()
        var x = 0
        while (x < jsonArray.length()){
            val jsonObjeto = jsonArray.getJSONObject(x)
            list.add(
                Filmes(
                jsonObjeto.getString("titulo"),
                    jsonObjeto.getString("ano"),
                    jsonObjeto.getString("imdbId"),
                    jsonObjeto.getString("tipo"),
                    jsonObjeto.getString("capaFilme")
            ))
            x++
        }
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

    private fun VoltarTelaLogin(){
        val intent = Intent(this, FormLogin::class.java)
        startActivity(intent)
        finish()
    }
}