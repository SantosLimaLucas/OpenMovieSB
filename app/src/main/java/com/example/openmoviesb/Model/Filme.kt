package com.example.openmoviesb.Model



import com.google.gson.annotations.SerializedName

data class Filme(
        @SerializedName("Response")
        val response: String,
        @SerializedName("Search")
        val search: List<Filmes>,
        @SerializedName("totalResults")
        val totalResults: String
)