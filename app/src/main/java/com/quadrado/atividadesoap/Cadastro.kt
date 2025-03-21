package com.quadrado.atividadesoap

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Cadastro : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cadastro)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val et_nome = findViewById<EditText>(R.id.et_nome)
        val et_cpf = findViewById<EditText>(R.id.et_cpf)
        val et_cnh = findViewById<EditText>(R.id.et_cnh)
        val et_email = findViewById<EditText>(R.id.et_email)
        val et_celular = findViewById<EditText>(R.id.et_celular)
        val et_cep = findViewById<EditText>(R.id.et_cep)
        val et_rua = findViewById<EditText>(R.id.et_rua)
        val et_bairro = findViewById<EditText>(R.id.et_bairro)
        val et_cidade = findViewById<EditText>(R.id.et_cidade)
        val et_estado = findViewById<EditText>(R.id.et_estado)
        val et_placa = findViewById<EditText>(R.id.et_placa)
        val et_marca = findViewById<EditText>(R.id.et_marca)
        val et_modelo = findViewById<EditText>(R.id.et_modelo)
        val et_ano = findViewById<EditText>(R.id.et_ano)
        val et_cor = findViewById<EditText>(R.id.et_cor)
        val et_kmAtual = findViewById<EditText>(R.id.et_kmAtual)

        val imgb_busca = findViewById<ImageButton>(R.id.imgb_busca)
        val btn_salvar = findViewById<Button>(R.id.btn_salvar)


        

    }
}