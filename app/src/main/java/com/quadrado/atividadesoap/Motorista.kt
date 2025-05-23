package com.quadrado.atividadesoap

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Motorista( // motorista e carro '-'
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    val nome: String,
    val cpf: String,
    val cnh: String,
    val email: String,
    val celular: String,
    val cep: String,
    val rua: String,
    val bairro: String,
    val cidade: String,
    val estado: String,
    val placa: String,
    val marca: String,
    val modelo: String,
    val ano: Int,
    val cor: String,
    val kmAtual: Int,
    val sincronizado: Boolean = false

)
