package com.example.electriccarapp.ui

import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.electriccarapp.R
import java.lang.NumberFormatException
import java.text.DecimalFormat
import java.util.Locale

class CalcularAutonomiaActivity : AppCompatActivity() {
    lateinit var preco: EditText
    lateinit var kmPercorridos: EditText
    lateinit var resultado: TextView
    lateinit var btnEnviar: Button
    lateinit var btnVoltar: ImageView
    val numberFormatter = DecimalFormat.getCurrencyInstance(Locale("pt", "BR"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calcular_autonomia)
        setupView()
        setupListeners()
        val valor = getSharedPref()
        if (valor != 0f) {
            resultado.text = numberFormatter.format(valor)
        }
    }

    private fun setupView() {
        preco = findViewById(R.id.et_preco_kwh)
        kmPercorridos = findViewById(R.id.et_km_percorrido)
        btnEnviar = findViewById(R.id.btn_enviar)
        resultado = findViewById(R.id.tv_resultado)
        btnVoltar = findViewById(R.id.btn_back)
    }

    private fun setupListeners() {
        btnVoltar.setOnClickListener {
            finish()
        }
        btnEnviar.setOnClickListener {
            try {
                val precoValue = preco.text.toString().toFloat()
                val kmPercorridoValue = kmPercorridos.text.toString().toFloat()

                val result = precoValue / kmPercorridoValue

                resultado.text = numberFormatter.format(result)

                saveSharedPref(result)
            } catch (e: NumberFormatException) {
                // TODO: Mostrar um erro que os valores estão vazios
            } catch (e: ArithmeticException) {
                // TODO: Mostrar um erro de divisão por 0
            }
        }
    }

    private fun saveSharedPref(result: Float) {
        val sharedPref = getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putFloat(getString(R.string.saved_calc), result)
            apply()
        }
    }

    private fun getSharedPref(): Float {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getFloat(getString(R.string.saved_calc), 0f)
    }
}