package com.example.electriccarapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.electriccarapp.R
import com.example.electriccarapp.data.local.CarRepository
import com.example.electriccarapp.domain.Car
import java.text.DecimalFormat
import java.util.Locale

class CarAdapter(private val cars: List<Car>) : RecyclerView.Adapter<CarAdapter.ViewHolder>() {
    private lateinit var carsRepository: CarRepository
    var onFavoriteToggle: (Car, Int) -> Unit = { car, position -> }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_item, parent, false)
        carsRepository = CarRepository(view.context)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.price.text =
            DecimalFormat.getCurrencyInstance(Locale("pt", "BR")).format(cars[position].price)
        holder.battery.text = buildString {
            append(cars[position].battery.toString().replace('.', ','))
            append(" kWh")
        }
        holder.potency.text = buildString {
            append(cars[position].potency)
            append(" cv")
        }
        holder.recharge.text = buildString {
            append(cars[position].rechargeTime)
            append(" min")
        }
        holder.carName.text = cars[position].name
        holder.favorite.setOnClickListener {
            val car = cars[position]
            toggleFavorite(car, holder)
            onFavoriteToggle(car, position)
        }

        if (cars[position].isFavorite) {
            holder.favorite.setImageResource(R.drawable.ic_star_fill)
        }
    }

    private fun toggleFavorite(car: Car, holder: ViewHolder) {
        if (car.isFavorite) {
            holder.favorite.setImageResource(R.drawable.ic_star_outline)
            carsRepository.delete(car.id)
        } else {
            holder.favorite.setImageResource(R.drawable.ic_star_fill)
            carsRepository.saveIfNotExists(car)
        }
    }

    override fun getItemCount() = cars.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val carName: TextView
        val price: TextView
        val battery: TextView
        val potency: TextView
        val recharge: TextView
        val favorite: ImageView

        init {
            price = view.findViewById(R.id.tv_price_value)
            carName = view.findViewById(R.id.tv_car_name)
            battery = view.findViewById(R.id.tv_battery_value)
            potency = view.findViewById(R.id.tv_potency_value)
            recharge = view.findViewById(R.id.tv_recharge_value)
            favorite = view.findViewById(R.id.iv_favorite)
        }
    }
}

