package com.example.appgerenciamentosalas

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

sealed class ItemLista {
    data class CabecalhoAndar(val andar: String) : ItemLista()
    data class Sala(val id: Int, val nome: String, val tipo: String, val estaLivre: Boolean) : ItemLista()
}

class SalaAdapter(
    private val itens: List<ItemLista>,
    private val onAddClick: (ItemLista.Sala) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TIPO_CABECALHO = 0
        private const val TIPO_SALA = 1
    }

    override fun getItemViewType(position: Int): Int {
        return when (itens[position]) {
            is ItemLista.CabecalhoAndar -> TIPO_CABECALHO
            is ItemLista.Sala -> TIPO_SALA
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return if (viewType == TIPO_CABECALHO) {
            val view = inflater.inflate(R.layout.item_andar_header, parent, false)
            CabecalhoViewHolder(view)
        } else {
            val view = inflater.inflate(R.layout.item_sala, parent, false)
            SalaViewHolder(view)
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itens[position]
        if (holder is CabecalhoViewHolder && item is ItemLista.CabecalhoAndar) {
            holder.txtNomeAndar.text = item.andar
        } else if (holder is SalaViewHolder && item is ItemLista.Sala) {
            holder.txtNomeSala.text = item.nome
            holder.txtCapacidade.text = "Tipo: ${item.tipo}"

            if (item.estaLivre) {
                holder.indicadorStatus.setCardBackgroundColor(Color.parseColor("#10B981"))
                holder.txtStatusTexto.text = "Livre"
                holder.txtStatusTexto.setTextColor(Color.parseColor("#10B981"))
            } else {
                holder.indicadorStatus.setCardBackgroundColor(Color.parseColor("#EF4444"))
                holder.txtStatusTexto.text = "Ocupada"
                holder.txtStatusTexto.setTextColor(Color.parseColor("#EF4444"))
            }

            holder.btnAdicionarAula.setOnClickListener {
                onAddClick(item)
            }
        }
    }
    override fun getItemCount(): Int {
        return itens.size
    }

    class CabecalhoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNomeAndar: TextView = view.findViewById(R.id.txtNomeAndar)
    }

    class SalaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtNomeSala: TextView = view.findViewById(R.id.txtNomeSala)
        val txtCapacidade: TextView = view.findViewById(R.id.txtCapacidade)
        val indicadorStatus: CardView = view.findViewById(R.id.indicadorStatus)
        val txtStatusTexto: TextView = view.findViewById(R.id.txtStatusTexto)
        val btnAdicionarAula: ImageView = view.findViewById(R.id.btnAdicionarAula)
    }
}