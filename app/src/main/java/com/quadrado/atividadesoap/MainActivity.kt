package com.quadrado.atividadesoap

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

lateinit var lista_motoristacarro: RecyclerView
lateinit var adapter: MotoristaAdapter

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        lista_motoristacarro = findViewById(R.id.list_motoristacarro)

        val activityCadastro = Intent(this, Cadastro::class.java)

        findViewById<FloatingActionButton>(R.id.fab_cadastro).setOnClickListener() {
            startActivity(activityCadastro)
        }

        lista_motoristacarro.layoutManager = LinearLayoutManager(this)
        adapter = MotoristaAdapter(mutableListOf())
        lista_motoristacarro.adapter = adapter

    }

    override fun onResume() {
        super.onResume()
        listarMotoristas()
    }

    private fun listarMotoristas() {
        val lista = Database.getInstance(this)?.MotoristaDAO()?.listarMotoristas()
        if (lista != null && lista.isNotEmpty()) {
            adapter.listarMotoristas(lista)
        } else {
            Toast.makeText(this, "Nenhum motorista encontrado", Toast.LENGTH_LONG).show()
        }

    }
}