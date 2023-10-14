package com.example.electriccarapp.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.electriccarapp.R
import com.example.electriccarapp.data.local.CarRepository
import com.example.electriccarapp.domain.Car
import com.example.electriccarapp.ui.adapter.CarAdapter

class FavoritesFragment : Fragment() {
    lateinit var listaCarrosFavoritos: RecyclerView
    lateinit var sadImage: ImageView
    lateinit var sadText: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.favorites_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView(view)
        setupList()
    }

    override fun onResume() {
        super.onResume()
        setupList()
    }

    private fun getCarsOnLocalDB(): List<Car> {
        val repository = CarRepository(requireContext())
        return repository.getAll()
    }

    private fun setupView(view: View) {
        view.apply {
            listaCarrosFavoritos = findViewById(R.id.rv_favorite_car_list)
            sadImage = findViewById(R.id.iv_sad)
            sadText = findViewById(R.id.tv_sad)
        }
    }

    private fun setupList() {
        val cars = getCarsOnLocalDB()
        if (cars.isEmpty()) {
            sadImage.isVisible = true
            sadText.isVisible = true
        } else {
            sadImage.isVisible = false
            sadText.isVisible = false
        }
        val adapter = CarAdapter(cars)
        listaCarrosFavoritos.adapter = adapter
        listaCarrosFavoritos.isVisible = true

        adapter.onFavoriteToggle = { car, position ->
            setupList()
            if (car.isFavorite) {
                adapter.notifyItemRemoved(position)
            } else {
                adapter.notifyItemInserted(position)
            }
        }
    }
}