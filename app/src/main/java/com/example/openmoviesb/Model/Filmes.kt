package com.example.openmoviesb.Model

import com.androidnetworking.AndroidNetworking
import com.androidnetworking.interfaces.ParsedRequestListener
import com.google.gson.annotations.SerializedName
import com.example.openmoviesb.R

data class Filmes(

        @SerializedName("imdbID")
        var imdbID: String,
        @SerializedName("Poster")
        var poster: String,
        @SerializedName("Title")
        var title: String,
        @SerializedName("Type")
        var type: String,
        @SerializedName("Year")
        var year: String
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



