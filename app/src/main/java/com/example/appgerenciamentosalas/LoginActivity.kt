package com.example.appgerenciamentosalas

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // 1. Mapeando os componentes visuais do XML para o Kotlin
        val edtEmail = findViewById<EditText>(R.id.edtEmail)
        val edtSenha = findViewById<EditText>(R.id.edtSenha)
        val btnEntrar = findViewById<Button>(R.id.btnEntrar)

        // 2. Ação de clique no botão
        btnEntrar.setOnClickListener {
            val email = edtEmail.text.toString()
            val senha = edtSenha.text.toString()

            // Validação simples para não deixar campos vazios
            if (email.isEmpty() || senha.isEmpty()) {
                Toast.makeText(this, "Preencha e-mail e senha!", Toast.LENGTH_SHORT).show()
            } else {
                // INTENT EXPLÍCITA: Sai da LoginActivity e vai para a MainActivity
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

                // Finaliza a tela de login para que o usuário não volte para ela se apertar o botão "Voltar" do celular
                finish()
            }
        }
    }
}