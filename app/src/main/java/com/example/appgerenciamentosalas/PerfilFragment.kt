package com.example.appgerenciamentosalas

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class PerfilFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_perfil, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // --- INTENT IMPLÍCITA (SUPORTE) ---
        // Mude de <Button> para <View> ou <androidx.cardview.widget.CardView>
        val btnSuporte = view.findViewById<View>(R.id.btnSuporteIbmec)
        btnSuporte.setOnClickListener {
            val linkIbmec = Uri.parse("https://www.ibmec.br")
            val intentImplicita = Intent(Intent.ACTION_VIEW, linkIbmec)
            startActivity(intentImplicita)
        }

        // --- INTENT EXPLÍCITA (SAIR / LOGOUT) ---
        // Mude de <Button> para <View> também
        val btnSair = view.findViewById<View>(R.id.btnSairPerfil)
        btnSair.setOnClickListener {
            val intentExplicita = Intent(requireContext(), LoginActivity::class.java)
            intentExplicita.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intentExplicita)
        }  }
}