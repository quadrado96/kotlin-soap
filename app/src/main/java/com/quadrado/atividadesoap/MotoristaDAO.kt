package com.quadrado.atividadesoap

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MotoristaDAO {

    @Insert
    fun salvar(m: Motorista)

    @Query("SELECT * FROM Motorista ORDER BY id")
    fun listarMotoristas(): List<Motorista>

}
