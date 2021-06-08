package com.example.openmoviesb.Model

import com.example.openmoviesb.R
import com.google.gson.annotations.SerializedName

data class Filmes(
    @SerializedName("Title") var titulo: String = "",
    @SerializedName("Year") var ano: String="",
    @SerializedName("imdbID") var imdbID: String="",
    @SerializedName("Type") var tipo: String="",
    @SerializedName("Poster") val capaFilme: String = ""

)
class FilmesBuilder{
    var capaFilme: String = ""
    fun build():Filmes = Filmes(capaFilme)
}

fun filmes(block: FilmesBuilder.()-> Unit): Filmes = FilmesBuilder().apply(block).build()

fun addFilmes(): MutableList<Filmes> = mutableListOf(

)