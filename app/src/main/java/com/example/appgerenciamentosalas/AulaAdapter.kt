package com.example.appgerenciamentosalas

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.appgerenciamentosalas.api.AulaResponseDto

class AulaAdapter(
    private val listaAulas: List<AulaResponseDto>,
    private val onDeletarClique: (AulaResponseDto) -> Unit // 1. Adicionado o callback de clique
) : RecyclerView.Adapter<AulaAdapter.AulaViewHolder>() {

    class AulaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtDisciplina: TextView = view.findViewById(R.id.txtDisciplinaItem)
        val txtCurso: TextView = view.findViewById(R.id.txtCursoItem)
        val txtProfessor: TextView = view.findViewById(R.id.txtProfessorItem)
        val txtHorario: TextView = view.findViewById(R.id.txtHorarioItem)
        val txtSala: TextView = view.findViewById(R.id.txtSalaItem)

        // 2. Mapeia o ícone de menos vermelho do layout
        val btnDeletar: ImageView = view.findViewById(R.id.btnDeletarItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AulaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_aula, parent, false)
        return AulaViewHolder(view)
    }

    override fun onBindViewHolder(holder: AulaViewHolder, position: Int) {
        val aula = listaAulas[position]

        holder.txtDisciplina.text = aula.disciplina.uppercase()
        holder.txtCurso.text = aula.curso
        holder.txtProfessor.text = "Prof. ${aula.professor}"

        // 1. Pegamos o dia ("SEGUNDA") e formatamos para ficar bonito ("Segunda")
        val diaFormatado = aula.diaDaSemana.lowercase().replaceFirstChar { it.uppercase() }

        // 2. Juntamos o dia formatado com os horários
        holder.txtHorario.text = "$diaFormatado • ${aula.horarioInicio.substring(0, 5)} - ${aula.horarioFim.substring(0, 5)}"

        holder.txtSala.text = aula.localizacaoDaSala

        holder.btnDeletar.setOnClickListener {
            onDeletarClique(aula)
        }
    }

    override fun getItemCount(): Int = listaAulas.size
}