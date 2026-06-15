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
    private val mostrarBotaoDeletar: Boolean = true,
    private val onDeletarClique: (AulaResponseDto) -> Unit
) : RecyclerView.Adapter<AulaAdapter.AulaViewHolder>() {

    class AulaViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val txtDisciplina: TextView = view.findViewById(R.id.txtDisciplinaItem)
        val txtCurso: TextView = view.findViewById(R.id.txtCursoItem)
        val txtProfessor: TextView = view.findViewById(R.id.txtProfessorItem)
        val txtHorario: TextView = view.findViewById(R.id.txtHorarioItem)
        val txtSala: TextView = view.findViewById(R.id.txtSalaItem)
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

        val diaFormatado = aula.diaDaSemana.lowercase().replaceFirstChar { it.uppercase() }
        holder.txtHorario.text = "$diaFormatado • ${aula.horarioInicio.substring(0, 5)} - ${aula.horarioFim.substring(0, 5)}"

        holder.txtSala.text = aula.localizacaoDaSala

        // Lógica adicionada para exibir ou ocultar o botão de acordo com a tela
        if (mostrarBotaoDeletar) {
            holder.btnDeletar.visibility = View.VISIBLE
            holder.btnDeletar.setOnClickListener {
                onDeletarClique(aula)
            }
        } else {
            // Trocamos de GONE para INVISIBLE: O botão some, mas o espaço dele é preservado!
            holder.btnDeletar.visibility = View.INVISIBLE
        }
    }

    override fun getItemCount(): Int = listaAulas.size
}