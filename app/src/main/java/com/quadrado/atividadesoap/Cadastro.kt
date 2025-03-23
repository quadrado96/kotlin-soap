package com.quadrado.atividadesoap

import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.URL

private lateinit var rua: EditText
private lateinit var bairro: EditText
private lateinit var cidade: EditText
private lateinit var estado: EditText

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


        val nome = findViewById<EditText>(R.id.et_nome)
        val cpf = findViewById<EditText>(R.id.et_cpf)
        val cnh = findViewById<EditText>(R.id.et_cnh)
        val email = findViewById<EditText>(R.id.et_email)
        val celular = findViewById<EditText>(R.id.et_celular)
        val cep = findViewById<EditText>(R.id.et_cep)
        rua = findViewById<EditText>(R.id.et_rua)
        bairro = findViewById<EditText>(R.id.et_bairro)
        cidade = findViewById<EditText>(R.id.et_cidade)
        estado = findViewById<EditText>(R.id.et_estado)
        val placa = findViewById<EditText>(R.id.et_placa)
        val marca = findViewById<EditText>(R.id.et_marca)
        val modelo = findViewById<EditText>(R.id.et_modelo)
        val ano = findViewById<EditText>(R.id.et_ano)
        val cor = findViewById<EditText>(R.id.et_cor)
        val kmAtual = findViewById<EditText>(R.id.et_kmAtual)

        val imgb_busca = findViewById<ImageButton>(R.id.imgb_busca)
        val btn_salvar = findViewById<Button>(R.id.btn_salvar)


        imgb_busca.setOnClickListener {
            buscarCep(cep.text.toString())
        }

        btn_salvar.setOnClickListener {
            val motorista = Motorista(
                id = null,
                nome = nome.text.toString(),
                cpf = cpf.text.toString(),
                cnh = cnh.text.toString(),
                celular = celular.text.toString(),
                email = email.text.toString(),
                cep = cep.text.toString(),
                rua = rua.text.toString(),
                bairro = bairro.text.toString(),
                cidade = cidade.text.toString(),
                estado = estado.text.toString(),
                placa = placa.text.toString(),
                marca = marca.text.toString(),
                modelo = modelo.text.toString(),
                ano = ano.text.toString().toInt(),
                cor = cor.text.toString(),
                kmAtual = kmAtual.text.toString().toInt()

            )

            Database.getInstance(this)!!.MotoristaDAO().salvar(motorista)

            finish()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return false
    }

    private fun buscarCep(cep: String) {
        val url = "https://viacep.com.br/ws/$cep/json/"

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val resposta = URL(url).readText()

                withContext(Dispatchers.Main) {
                    processarResposta(resposta)
                }
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@Cadastro, "Erro ao buscar CEP", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun processarResposta(resp: String) {

        try {
            val respostaJSON = JSONObject(resp)

            if (respostaJSON.has("erro")) {
                Toast.makeText(this, "CEP inválido", Toast.LENGTH_LONG).show()
            } else {
                rua.setText(respostaJSON.getString("logradouro"))
                bairro.setText(respostaJSON.getString("bairro"))
                cidade.setText(respostaJSON.getString("localidade"))
                estado.setText(respostaJSON.getString("uf"))
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Erro ao processar resposta", Toast.LENGTH_LONG).show()
        }
    }
}
