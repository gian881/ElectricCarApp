package com.example.electriccarapp.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.electriccarapp.R
import com.example.electriccarapp.domain.Car
import java.text.DecimalFormat
import java.util.Locale

class CarAdapter(private val cars: Array<Car>) : RecyclerView.Adapter<CarAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.car_item, parent, false)
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

    }

    override fun getItemCount() = cars.size


    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val carName: TextView
        val price: TextView
        val battery: TextView
        val potency: TextView
        val recharge: TextView

        init {
            price = view.findViewById(R.id.tv_price_value)
            carName = view.findViewById(R.id.tv_car_name)
            battery = view.findViewById(R.id.tv_battery_value)
            potency = view.findViewById(R.id.tv_potency_value)
            recharge = view.findViewById(R.id.tv_recharge_value)
        }
    }
}

