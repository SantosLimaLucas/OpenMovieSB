package com.marcos.netflixclone.Model

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.interfaces.ParsedRequestListener
import com.google.gson.annotations.SerializedName
import com.example.openmoviesb.R

data class Filmes(

        @SerializedName("imdbID")
        val imdbID: String,
        @SerializedName("Poster")
        val poster: String,
        @SerializedName("Title")
        val title: String,
        @SerializedName("Type")
        val type: String,
        @SerializedName("Year")
        val year: String
)

class FilmesBuilder{
    var id: String = ""
    var imagemfilme: String = ""
    var title: String = ""
    var type: String = ""
    var year: String=""
    fun build(): Filmes = Filmes(id, imagemfilme, title,type,year)
}

fun filmes(block: FilmesBuilder.() -> Unit): Filmes = FilmesBuilder().apply(block).build()



