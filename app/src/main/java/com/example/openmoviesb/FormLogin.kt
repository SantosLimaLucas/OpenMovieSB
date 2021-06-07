package com.example.openmoviesb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.openmoviesb.databinding.ActivityFormLoginBinding

class FormLogin : AppCompatActivity() {

    private lateinit var binding: ActivityFormLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()

        binding.txtTelaCadastro.setOnClickListener{
            intent = Intent(this, FormCadastro::class.java)
            startActivity(intent)
        }
    }
}