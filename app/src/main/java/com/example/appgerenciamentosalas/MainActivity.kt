package com.example.appgerenciamentosalas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        setContentView(R.layout.activity_main)

        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)

        // Ao abrir o app, carrega o HomeFragment por padrão
        if (savedInstanceState == null) {
            substituirFragment(HomeFragment())
        }

        // Escuta os cliques na barra inferior
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    substituirFragment(HomeFragment())
                    true
                }
                R.id.nav_perfil -> {
                    substituirFragment(PerfilFragment())
                    true
                }
                R.id.nav_salas -> {
                    substituirFragment(SalasFragment())
                    true
                }
                R.id.nav_aulas -> {
                    substituirFragment(AulasFragment())
                    true
                }

                // As outras telas (Salas e Aulas) faremos depois!
                else -> false
            }
        }
    }

    // Função mágica que troca os Fragments na tela
    private fun substituirFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}