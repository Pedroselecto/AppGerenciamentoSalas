package com.example.appgerenciamentosalas

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
import java.util.Calendar

class HomeFragment : Fragment() {

    private lateinit var listaDeAulas: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listaDeAulas = view.findViewById(R.id.listaDeAulas)
        listaDeAulas.layoutManager = LinearLayoutManager(requireContext())

        carregarAulasDoDiaAtual()
    }

    private fun carregarAulasDoDiaAtual() {
        val calendar = Calendar.getInstance()
        val diaDaSemana = calendar.get(Calendar.DAY_OF_WEEK)

        val diaEnum = when (diaDaSemana) {
            Calendar.MONDAY -> "SEGUNDA"
            Calendar.TUESDAY -> "TERCA"
            Calendar.WEDNESDAY -> "QUARTA"
            Calendar.THURSDAY -> "QUINTA"
            Calendar.FRIDAY -> "SEXTA"
            Calendar.SATURDAY -> "SABADO"
            Calendar.SUNDAY -> "DOMINGO"
            else -> "SEGUNDA"
        }

        RetrofitClient.aulaApi.buscarAulasPorDia(diaEnum).enqueue(object : Callback<List<AulaResponseDto>> {
            override fun onResponse(call: Call<List<AulaResponseDto>>, response: Response<List<AulaResponseDto>>) {
                if (isAdded) {
                    if (response.isSuccessful && response.body() != null) {
                        val aulasDeHoje = response.body()!!

                        listaDeAulas.adapter = AulaAdapter(
                            listaAulas = aulasDeHoje,
                            mostrarBotaoDeletar = false
                        ) {
                        }

                    } else {
                        Toast.makeText(requireContext(), "Erro ao carregar cronograma", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            override fun onFailure(call: Call<List<AulaResponseDto>>, t: Throwable) {
                if (isAdded) {
                    Toast.makeText(requireContext(), "Erro de conexão com o servidor", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }
}