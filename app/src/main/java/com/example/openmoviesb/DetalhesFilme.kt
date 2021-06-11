package com.example.openmoviesb


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.ParsedRequestListener
import com.example.openmoviesb.Model.Detalhes
import com.example.openmoviesb.Model.Rating
import com.example.openmoviesb.databinding.ActivityDetalhesFilmeBinding
import com.squareup.picasso.Picasso


class DetalhesFilme : AppCompatActivity() {
    private var ratingList : MutableList<Rating> = mutableListOf()
    private lateinit var binding: ActivityDetalhesFilmeBinding
    private var dataDetalhes: Detalhes = Detalhes(" "," "," "," "," "," "," "," "," "," "," "," "," "," "," "," ", ratingList, " "," "," "," "," "," "," "," ")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalhesFilmeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //setup Android Networking
        AndroidNetworking.initialize(this)
        var imdbID = intent.getStringExtra("imdbID")
        var urlDetalhes = "https://www.omdbapi.com/?i=${imdbID}&apikey=960d95f2&r=json"
        AndroidNetworking.get(urlDetalhes)
                .build()
                .getAsObject(Detalhes::class.java, object : ParsedRequestListener<Detalhes> {
                    override fun onResponse(response: Detalhes) {
                        dataDetalhes = response
                        data(dataDetalhes)
                    }
                    override fun onError(anError: ANError?) {
                        println("Ocorreu um erro")
                    }
                }
                )

        supportActionBar!!.hide()
        Toolbar()
    }

    private fun Toolbar(){
        val toolbar_detalhes = binding.toolbarDetalhes
        toolbar_detalhes.setNavigationIcon(getDrawable(R.drawable.ic_voltar))
        toolbar_detalhes.setNavigationOnClickListener {
            finish()
        }
    }
    private fun data(detalhes: Detalhes){
        var poster = detalhes.poster
        println("Poster é " + poster)
        var title = detalhes.title
        binding.title.text = title

        var data = detalhes.released
        binding.year.text = "Released: ${data}"

        var tipo = detalhes.type
        binding.type.text = "Type: ${tipo}"

        var nota = detalhes.imdbRating
        binding.rating.text = "Imb Rating: ${nota}"

        var sinopse = detalhes.plot
        binding.plot.text = sinopse

        var director = detalhes.director
        binding.director.text = "Director: ${director}"

        var stars = detalhes.actors
        binding.stars.text = "Stars: ${stars}"

        var writers = detalhes.writer
        binding.writers.text = "Stars: ${writers}"

        if(poster != "N/A"){
            Picasso.get().load(poster).fit().into(binding.poster)
        }else{
            binding.poster.setImageResource(R.drawable.noimageavailable)
        }
    }
}