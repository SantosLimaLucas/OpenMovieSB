package com.example.openmoviesb

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.openmoviesb.databinding.ActivityFormCadastroBinding
import com.google.firebase.auth.FirebaseAuth

class FormCadastro : AppCompatActivity() {

    private lateinit var binding: ActivityFormCadastroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFormCadastroBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.hide()
        Toolbar()

        binding.btCadastrar.setOnClickListener {

            val email = binding.editEmail.text.toString()
            val senha = binding.editSenha.text.toString()
            val mensagem_erro = binding.mensagemErro

            if (email.isEmpty() || senha.isEmpty()){
                mensagem_erro.setText("Preencha todos os campos")
            }else{
                CadastrarUsuario()
            }
        }

    }

    private fun CadastrarUsuario(){
        val email = binding.editEmail.text.toString()
        val senha = binding.editSenha.text.toString()
        val mensagem_erro = binding.mensagemErro

        FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, senha).addOnCompleteListener {
            if (it.isSuccessful){
                Toast.makeText(this, "Usuário cadastrado com sucesso!", Toast.LENGTH_SHORT).show()
                binding.editEmail.setText("")
                binding.editSenha.setText("")
                mensagem_erro.setText("")
            }
        }.addOnFailureListener {
            mensagem_erro.setText(it.message)
        }
    }

    private fun Toolbar(){
        val toolbar = binding.toolbarCadastro
        toolbar.setBackgroundColor(getColor(R.color.white))
        toolbar.setNavigationIcon(getDrawable(R.drawable.openmoviesb))
    }
}