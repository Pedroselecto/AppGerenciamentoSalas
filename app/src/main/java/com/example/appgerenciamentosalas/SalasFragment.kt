package com.example.appgerenciamentosalas

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.appgerenciamentosalas.api.RetrofitClient
import com.example.appgerenciamentosalas.api.SalaResponseDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.app.AlertDialog
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner

class SalasFragment : Fragment() {

    private lateinit var listaDeSalas: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_salas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        listaDeSalas = view.findViewById(R.id.listaDeSalas)
        listaDeSalas.layoutManager = LinearLayoutManager(requireContext())

        // Chama a função que busca os dados no Spring Boot
        buscarSalasDoBanco()
    }

    private fun buscarSalasDoBanco() {
        RetrofitClient.salaApi.listarSalas().enqueue(object : Callback<List<SalaResponseDto>> {
            override fun onResponse(
                call: Call<List<SalaResponseDto>>,
                response: Response<List<SalaResponseDto>>
            ) {
                if (response.isSuccessful && response.body() != null) {
                    val salasDoBanco = response.body()!!
                    processarEExibirSalas(salasDoBanco)
                } else {
                    Toast.makeText(context, "Erro ao carregar salas", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<SalaResponseDto>>, t: Throwable) {
                Log.e("API_ERRO", "Falha na conexão: ${t.message}")
                Toast.makeText(context, "Sem conexão com o servidor", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun processarEExibirSalas(salasDoBanco: List<SalaResponseDto>) {
        val dadosAgrupados = mutableListOf<ItemLista>()

        // 1. O groupBy do Kotlin cria um Map separando as salas pelas chaves (ex: "PRIMEIRO", "SEGUNDO")
        val salasPorAndar = salasDoBanco.groupBy { it.andar }

        // 2. Para cada Andar, criamos um Cabeçalho e depois listamos as salas dele
        salasPorAndar.forEach { (andarEnum, listaDeSalasDoAndar) ->

            // Formatação do Andar que fizemos antes
            val andarFormatado = andarEnum.lowercase().replaceFirstChar { it.uppercase() }
            dadosAgrupados.add(ItemLista.CabecalhoAndar("$andarFormatado Andar"))

            // Adiciona as salas do andar
            listaDeSalasDoAndar.forEach { sala ->

                // O nosso "Tradutor" de UX
                // O nosso "Tradutor" de UX
                val tipoAmigavel = when (sala.tipo) {
                    "LABORATORIO" -> "Laboratório"
                    "TECH" -> "Tech"
                    "PREMIUM" -> "Premium"
                    "REGULAR" -> "Regular" // Oficializado aqui!
                    else -> sala.tipo.lowercase().replaceFirstChar { it.uppercase() }
                }

                dadosAgrupados.add(
                    ItemLista.Sala(
                        id = sala.id,
                        nome = "Sala ${sala.numero}",
                        tipo = tipoAmigavel, // Injetamos o nome amigável aqui
                        estaLivre = true
                    )
                )
            }
        }

        listaDeSalas.adapter = SalaAdapter(dadosAgrupados) { salaSelecionada ->
            exibirDialogNovaAula(salaSelecionada)
        }
    }

    private fun exibirDialogNovaAula(sala: ItemLista.Sala) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_nova_aula, null)
        val builder = AlertDialog.Builder(requireContext()).setView(dialogView)
        val dialog = builder.create()

        // 1. Busca as referências dos Dropdowns (Spinners)
        val spinnerTurno = dialogView.findViewById<Spinner>(R.id.spinnerTurno)
        val spinnerCurso = dialogView.findViewById<Spinner>(R.id.spinnerCurso)
        val spinnerDia = dialogView.findViewById<Spinner>(R.id.spinnerDia)

        // 2. Cria as listas idênticas aos seus Enums no Spring Boot
        val listaTurnos = arrayOf("MANHA", "TARDE", "NOITE")
        val listaCursos = arrayOf("TECH", "DIREITO", "ECONOMIA", "PUBLICIDADE")
        val listaDias = arrayOf("SEGUNDA", "TERCA", "QUARTA", "QUINTA", "SEXTA", "SABADO")

        // 3. Conecta as listas aos Spinners usando um Adapter padrão do Android
        spinnerTurno.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            listaTurnos
        )
        spinnerCurso.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, listaCursos)
        spinnerDia.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, listaDias)

        val btnCancelar = dialogView.findViewById<Button>(R.id.btnCancelar)
        val btnSalvar = dialogView.findViewById<Button>(R.id.btnSalvar)

        btnCancelar.setOnClickListener { dialog.dismiss() }

        btnSalvar.setOnClickListener {
            val nome = dialogView.findViewById<EditText>(R.id.edtDisciplina).text.toString()
            val professor = dialogView.findViewById<EditText>(R.id.edtProfessor).text.toString()
            val inicio = dialogView.findViewById<EditText>(R.id.edtInicio).text.toString()
            val fim = dialogView.findViewById<EditText>(R.id.edtFim).text.toString()

            // 4. Captura a opção selecionada nos Dropdowns
            val turnoSelecionado = spinnerTurno.selectedItem.toString()
            val cursoSelecionado = spinnerCurso.selectedItem.toString()
            val diaSelecionado = spinnerDia.selectedItem.toString()

            RetrofitClient.aulaApi.cadastrarAula(
                nome, turnoSelecionado, cursoSelecionado, inicio, fim, professor, diaSelecionado, sala.id
            ).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (isAdded) {
                        if (response.isSuccessful) {
                            Toast.makeText(requireContext(), "Aula agendada com sucesso!", Toast.LENGTH_SHORT).show()
                            dialog.dismiss()
                        } else {
                            // Se bater no servidor mas ele rejeitar (ex: horário no formato errado)
                            Toast.makeText(requireContext(), "Erro do servidor: ${response.code()}", Toast.LENGTH_LONG).show()
                            Log.e("POST_AULA", "Corpo do erro: ${response.errorBody()?.string()}")
                        }
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    if (isAdded) {
                        Toast.makeText(requireContext(), "Falha na conexão, verifique o Logcat", Toast.LENGTH_SHORT).show()
                        // AQUI vamos descobrir o erro verdadeiro caso não seja o Finsky!
                        Log.e("POST_AULA_REAL", "Erro exato: ${t.message}")
                    }
                }
            })
        }

        dialog.show()
    }
}