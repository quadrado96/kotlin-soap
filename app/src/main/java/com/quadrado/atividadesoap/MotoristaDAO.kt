package com.quadrado.atividadesoap

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MotoristaDAO {

    @Insert
    fun salvar(m: Motorista)

    @Query("SELECT * FROM Motorista ORDER BY nome")
    fun listarMotoristas(): List<Motorista>

    @Query("SELECT * FROM Motorista WHERE sincronizado = false")
    fun listarNaoSincronizados(): List<Motorista>

    @Query("UPDATE Motorista SET sincronizado = 1 WHERE id = :id")
    fun marcarComoSincronizado(id: Int?)


}
