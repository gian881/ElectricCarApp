package com.example.electriccarapp.presentation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView
import com.example.electriccarapp.R
import com.example.electriccarapp.presentation.adapter.CarAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    lateinit var btnCalcularAutonomia: FloatingActionButton
    lateinit var listaCarros: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupView()
        setupListeners()
        setupList()
    }

    private fun setupView() {
        btnCalcularAutonomia = findViewById(R.id.fab_calculate)
        listaCarros = findViewById(R.id.rv_car_list)
    }

    private fun setupList() {
        val data = arrayOf(
            "R$ 1.322.000,00",
            "R$ 952.280,00",
            "R$ 2.222.222,00",
            "R$ 3.000.312,00"
        )

        val adapter = CarAdapter(data)
        listaCarros.adapter = adapter
    }

    private fun setupListeners() {
        btnCalcularAutonomia.setOnClickListener {
            startActivity(Intent(this, CalcularAutonomiaActivity::class.java))
        }
    }
}