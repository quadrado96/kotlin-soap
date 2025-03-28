package com.quadrado.atividadesoap

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.ksoap2.SoapEnvelope
import org.ksoap2.serialization.SoapObject
import org.ksoap2.serialization.SoapSerializationEnvelope
import org.ksoap2.transport.HttpTransportSE

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

        /*  ---- inserts aleatórios para teste do app :)


        Database.getInstance(this)!!.MotoristaDAO().salvar(
            Motorista(
                null, "Brunno", "123.456.789-00", "98765432100",
                "brunno@example.com", "(11) 91234-5678", "01234-000", "Rua das Flores",
                "Centro", "São Paulo", "SP", "ABC1D23", "Toyota", "Corolla",
                2018, "Preto", 45000, false
            )
        )

        Database.getInstance(this)!!.MotoristaDAO().salvar(
            Motorista(
                null, "Juliana Rocha", "234.567.890-11", "12345678901",
                "juliana.rocha@example.com", "(21) 99876-5432", "20000-000", "Av. Atlântica",
                "Copacabana", "Rio de Janeiro", "RJ", "XYZ9H56", "Honda", "Civic",
                2020, "Cinza", 32000, false
            )
        )

        Database.getInstance(this)!!.MotoristaDAO().salvar(
            Motorista(
                null, "Carlos Henrique", "345.678.901-22", "23456789012",
                "carlos.henrique@example.com", "(31) 98765-4321", "30130-000", "Rua da Bahia",
                "Funcionários", "Belo Horizonte", "MG", "LMN3G45", "Ford", "Ka",
                2017, "Branco", 67000, true
            )
        )

        Database.getInstance(this)!!.MotoristaDAO().salvar(
            Motorista(
                null, "Marina Silva", "456.789.012-33", "34567890123",
                "marina.silva@example.com", "(41) 97654-3210", "80010-000", "Rua XV de Novembro",
                "Centro", "Curitiba", "PR", "DEF5H78", "Chevrolet", "Onix",
                2019, "Vermelho", 29000, false
            )
        )
       */

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.imgb_sincronizar -> {
                sincronizarMotoristas()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun listarMotoristas() {
        val lista = Database.getInstance(this)?.MotoristaDAO()?.listarMotoristas()
        if (lista != null && lista.isNotEmpty()) {
            adapter.listarMotoristas(lista)
        } else {
            Toast.makeText(this, "Nenhum motorista encontrado", Toast.LENGTH_LONG).show()
        }

    }

    fun sincronizarMotoristas() {
        CoroutineScope(Dispatchers.IO).launch {
            val motoristas =
                Database.getInstance(this@MainActivity)!!.MotoristaDAO().listarNaoSincronizados()

            for (motorista in motoristas) {
                val request = SoapObject("http://motoristaservice.com/", "salvarMotorista")

                request.addProperty("nome", motorista.nome)
                request.addProperty("cpf", motorista.cpf)
                request.addProperty("cnh", motorista.cnh)
                request.addProperty("email", motorista.email)
                request.addProperty("celular", motorista.celular)
                request.addProperty("cep", motorista.cep)
                request.addProperty("rua", motorista.rua)
                request.addProperty("bairro", motorista.bairro)
                request.addProperty("cidade", motorista.cidade)
                request.addProperty("estado", motorista.estado)
                request.addProperty("placa", motorista.placa)
                request.addProperty("marca", motorista.marca)
                request.addProperty("modelo", motorista.modelo)
                request.addProperty("ano", motorista.ano)
                request.addProperty("cor", motorista.cor)
                request.addProperty("kmAtual", motorista.kmAtual)

                val envelope = SoapSerializationEnvelope(SoapEnvelope.VER11).apply {
                    dotNet = false
                    setOutputSoapObject(request)
                }

                val transport = HttpTransportSE("")

                try {
                    transport.call("http://motoristaservice.com/salvarMotorista", envelope)
                    val response = envelope.response as SoapObject
                    val resultado = response.getProperty("resultado").toString()

                    if (resultado == "ok") {
                        Database.getInstance(this@MainActivity)!!.MotoristaDAO()
                            .marcarComoSincronizado(motorista.id)
                    } else {
                        withContext(Dispatchers.Main) {
                            Toast.makeText(this@MainActivity, "Erro ao sincronizar ${motorista.nome}", Toast.LENGTH_SHORT).show()
                        }
                    }

                } catch (e: Exception) {
                    e.printStackTrace()
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@MainActivity, "Erro ao sincronizar: ${motorista.nome}", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            withContext(Dispatchers.Main) {
                Toast.makeText(this@MainActivity, "Sincronização concluída", Toast.LENGTH_SHORT).show()
                listarMotoristas()
            }
        }
    }

}