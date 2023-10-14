package com.example.electriccarapp.domain

data class Car(
    val id: Int,
    val name: String,
    val price: Float,
    val battery: Float,
    val potency: Int,
    val rechargeTime: Int,
    val photoUrl: String,
    var isFavorite: Boolean
)