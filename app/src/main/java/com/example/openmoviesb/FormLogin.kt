package com.example.openmoviesb

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.openmoviesb.databinding.ActivityFormLoginBinding
import com.google.firebase.auth.FirebaseAuth

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

        binding.btEntrar.setOnClickListener {
            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()
            val mensagem_erro = binding.mensagemErro

            if(email.isEmpty() || senha.isEmpty()){
                mensagem_erro.setText("Preencha todos os campos")
            }else{
                AutenticarUsuario()
            }
        }
    }

    private fun AutenticarUsuario(){
        val email = binding.editEmail.text.toString()
        val senha = binding.editSenha.text.toString()
        val mensagem_erro = binding.mensagemErro

        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, senha).addOnCompleteListener {
            if(it.isSuccessful){
                Toast.makeText(this,"Login efetuado com sucesso",Toast.LENGTH_SHORT).show()
                IrParaTelaDeFilmes()
            }
        }.addOnFailureListener {
            mensagem_erro.setText("Erro ao logar usuário")
        }
    }

    private fun IrParaTelaDeFilmes(){
        val intent = Intent(this, ListaFilmes::class.java)
        startActivity(intent)
        finish()
    }
}