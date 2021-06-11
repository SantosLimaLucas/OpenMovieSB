package com.example.openmoviesb.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.openmoviesb.R
import com.example.openmoviesb.databinding.ListaFilmesItemBinding
import com.example.openmoviesb.Model.Filmes
import com.squareup.picasso.Picasso


class FilmesAdapter (val filmes: MutableList<Filmes>): RecyclerView.Adapter<FilmesAdapter.FilmesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilmesViewHolder {
        val binding = ListaFilmesItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FilmesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FilmesViewHolder, position: Int) {

        with(holder){
            with(filmes[position]){
                //binding.capaFilme.setImageResource(capaFilme)
                val capaFilme: String
                if(filmes[position].poster != "N/A"){
                    capaFilme =filmes[position].poster
                    Picasso.get().load(capaFilme).fit().into(binding.capaFilmeLista)
                }else{
                    binding.capaFilmeLista.setImageResource(R.drawable.noimageavailable)
                }
            }
        }
    }

    override fun getItemCount() = filmes.size

    inner class FilmesViewHolder(val binding: ListaFilmesItemBinding): RecyclerView.ViewHolder(binding.root){

    }
}