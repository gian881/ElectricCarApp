package com.example.electriccarapp.data

import com.example.electriccarapp.domain.Car

object CarFactory {
    val list = arrayOf(
        Car(
            id = 1,
            name = "Nome do carro",
            price = 300000F,
            battery = 300F,
            potency = 200,
            rechargeTime = 30,
            photoUrl = ""
        )
    )

}