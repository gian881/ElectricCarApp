package com.example.electriccarapp.presentation

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.electriccarapp.R
import java.text.DecimalFormat
import java.util.Locale

class CalcularAutonomiaActivity : AppCompatActivity() {
    lateinit var preco: EditText
    lateinit var kmPercorridos: EditText
    lateinit var resultado: TextView
    lateinit var btnEnviar: Button
    val numberFormatter = DecimalFormat.getCurrencyInstance(Locale("pt", "BR"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calcular_autonomia)
        setupView()
        setupListeners()
    }

    private fun setupView() {
        preco = findViewById(R.id.et_preco_kwh)
        kmPercorridos = findViewById(R.id.et_km_percorrido)
        btnEnviar = findViewById(R.id.btn_enviar)
        resultado = findViewById(R.id.tv_resultado)
    }

    private fun setupListeners() {
        btnEnviar.setOnClickListener {
            val precoValue = preco.text.toString().toFloat()
            val kmPercorridoValue = kmPercorridos.text.toString().toFloat()

            val result = precoValue / kmPercorridoValue
            resultado.text = numberFormatter.format(result)
        }
    }

}