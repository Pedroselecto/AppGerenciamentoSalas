package com.example.appgerenciamentosalas

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appgerenciamentosalas.api.AulaResponseDto
import com.example.appgerenciamentosalas.api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AulasFragment : Fragment() {

    private lateinit var recyclerAulas: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_aulas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerAulas = view.findViewById(R.id.recyclerTodasAsAulas)
        recyclerAulas.layoutManager = LinearLayoutManager(requireContext())

        carregarTodasAsAulas()
    }

    private fun carregarTodasAsAulas() {
        RetrofitClient.aulaApi.listarAulas().enqueue(object : Callback<List<AulaResponseDto>> {
            override fun onResponse(call: Call<List<AulaResponseDto>>, response: Response<List<AulaResponseDto>>) {
                if (isAdded && response.isSuccessful && response.body() != null) {
                    configurarLista(response.body()!!)
                }
            }
            override fun onFailure(call: Call<List<AulaResponseDto>>, t: Throwable) {  }
        })
    }

    private fun configurarLista(lista: List<AulaResponseDto>) {
        recyclerAulas.adapter = AulaAdapter(lista) { aulaSelecionada ->
            mostrarConfirmacaoDelecao(aulaSelecionada)
        }
    }

    private fun mostrarConfirmacaoDelecao(aula: AulaResponseDto) {
        AlertDialog.Builder(requireContext())
            .setTitle("Remover Aula")
            .setMessage("Tem certeza que deseja cancelar e excluir a aula de ${aula.disciplina}?")
            .setNegativeButton("Cancelar", null)
            .setPositiveButton("Sim, Remover") { _, _ ->
                executarDelecaoNoServidor(aula.id)
            }
            .show()
    }

    private fun executarDelecaoNoServidor(aulaId: Int) {
        RetrofitClient.aulaApi.deletarAula(aulaId).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (isAdded && response.isSuccessful) {
                    Toast.makeText(requireContext(), "Aula removida com sucesso!", Toast.LENGTH_SHORT).show()
                    carregarTodasAsAulas()
                } else {
                    Toast.makeText(requireContext(), "Não foi possível remover a aula", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                if (isAdded) Toast.makeText(requireContext(), "Erro de conexão", Toast.LENGTH_SHORT).show()
            }
        })
    }
}