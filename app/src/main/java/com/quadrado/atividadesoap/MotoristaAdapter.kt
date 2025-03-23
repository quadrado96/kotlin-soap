package com.quadrado.atividadesoap

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class MotoristaAdapter(private val listaMotoristas: MutableList<Motorista>) :
    RecyclerView.Adapter<MotoristaAdapter.MotoristaViewHolder>() {

    class MotoristaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tv_nome_celular = view.findViewById<TextView>(R.id.tv_nome_celular)
        val tv_email_cpf_cnh = view.findViewById<TextView>(R.id.tv_email_cpf_cnh)
        val tv_endereco = view.findViewById<TextView>(R.id.tv_endereco)
        val tv_marca_modelo_cor_ano = view.findViewById<TextView>(R.id.tv_marca_modelo_cor_ano)
        val tv_placa = view.findViewById<TextView>(R.id.tv_placa)
        val tv_km = view.findViewById<TextView>(R.id.tv_km)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MotoristaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_motorista, parent, false)
        return MotoristaViewHolder(view)
    }

    override fun onBindViewHolder(holder: MotoristaViewHolder, position: Int) {
        val motorista = listaMotoristas[position]

        val nome_celular = "${motorista.nome} - ${motorista.celular}"
        val email_cpf_cnh = "${motorista.email} - CPF: ${motorista.cpf}\nCNH: ${motorista.cnh}"
        val endereco = "${motorista.rua}, ${motorista.bairro} / ${motorista.cidade} - ${motorista.estado}"

        val marca_modelo_cor_ano = "${motorista.marca} ${motorista.modelo} ${motorista.cor} - ${motorista.ano}"

        holder.tv_nome_celular.text = nome_celular
        holder.tv_email_cpf_cnh.text = email_cpf_cnh
        holder.tv_endereco.text = endereco

        holder.tv_marca_modelo_cor_ano.text = marca_modelo_cor_ano
        holder.tv_placa.text = motorista.placa
        holder.tv_km.text = "KMs rodados: ${motorista.kmAtual}"

        if (!motorista.sincronizado) {
            holder.tv_nome_celular.setTextColor(
                ContextCompat.getColor(holder.itemView.context, R.color.vermelho)
            )
        } else {
            holder.tv_nome_celular.setTextColor(
                ContextCompat.getColor(holder.itemView.context, R.color.verde)
            )
        }
    }

    override fun getItemCount(): Int {
        return listaMotoristas.size
    }

    fun listarMotoristas(novaLista: List<Motorista>) {
        listaMotoristas.clear()
        listaMotoristas.addAll(novaLista)
        notifyDataSetChanged()
    }
}
