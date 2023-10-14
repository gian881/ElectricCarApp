package com.example.electriccarapp.data.local

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import android.util.Log
import com.example.electriccarapp.data.local.CarsContract.CarEntry.COLUMN_NAME_BATTERY
import com.example.electriccarapp.data.local.CarsContract.CarEntry.COLUMN_NAME_CAR_ID
import com.example.electriccarapp.data.local.CarsContract.CarEntry.COLUMN_NAME_NAME
import com.example.electriccarapp.data.local.CarsContract.CarEntry.COLUMN_NAME_PHOTO_URL
import com.example.electriccarapp.data.local.CarsContract.CarEntry.COLUMN_NAME_POTENCY
import com.example.electriccarapp.data.local.CarsContract.CarEntry.COLUMN_NAME_PRICE
import com.example.electriccarapp.data.local.CarsContract.CarEntry.COLUMN_NAME_RECHARGE_TIME
import com.example.electriccarapp.domain.Car

class CarRepository(private val context: Context) {
    fun save(car: Car): Boolean {
        var wasSaved = false
        try {
            val dbHelper = CarsDBHelper(context)
            val db = dbHelper.writableDatabase

            val values = ContentValues().apply {
                put(COLUMN_NAME_CAR_ID, car.id)
                put(COLUMN_NAME_NAME, car.name)
                put(COLUMN_NAME_PRICE, car.price)
                put(COLUMN_NAME_BATTERY, car.battery)
                put(COLUMN_NAME_POTENCY, car.potency)
                put(COLUMN_NAME_RECHARGE_TIME, car.rechargeTime)
                put(COLUMN_NAME_PHOTO_URL, car.photoUrl)
            }
            val newRegister = db?.insert(CarsContract.CarEntry.TABLE_NAME, null, values)

            if (newRegister != null) wasSaved = true
        } catch (e: Exception) {
            e.message?.let {
                Log.e("Error", it)
            }
        }

        return wasSaved
    }

    fun findCarById(id: Int): Car {
        val dbHelper = CarsDBHelper(context)
        val db = dbHelper.readableDatabase

        val columns = arrayOf(
            BaseColumns._ID,
            COLUMN_NAME_CAR_ID,
            COLUMN_NAME_NAME,
            COLUMN_NAME_PRICE,
            COLUMN_NAME_BATTERY,
            COLUMN_NAME_POTENCY,
            COLUMN_NAME_RECHARGE_TIME,
            COLUMN_NAME_PHOTO_URL
        )

        val filter = "$COLUMN_NAME_CAR_ID = ?"
        val filterValues = arrayOf(id.toString())

        val cursor = db.query(
            CarsContract.CarEntry.TABLE_NAME,
            columns,
            filter,
            filterValues,
            null,
            null,
            null
        )

        var carId = ID_WHEN_NO_CAR_IS_FOUND.toLong()
        var name = ""
        var price = 0F
        var battery = 0F
        var potency = 0L
        var rechargeTime = 0L
        var photoUrl = ""

        with(cursor) {
            while (moveToNext()) {
                carId = getLong(getColumnIndexOrThrow(COLUMN_NAME_CAR_ID))
                name = getString(getColumnIndexOrThrow(COLUMN_NAME_NAME))
                price = getFloat(getColumnIndexOrThrow(COLUMN_NAME_PRICE))
                battery = getFloat(getColumnIndexOrThrow(COLUMN_NAME_BATTERY))
                potency = getLong(getColumnIndexOrThrow(COLUMN_NAME_POTENCY))
                rechargeTime = getLong(getColumnIndexOrThrow(COLUMN_NAME_RECHARGE_TIME))
                photoUrl = getString(getColumnIndexOrThrow(COLUMN_NAME_PHOTO_URL))
            }
        }

        cursor.close()

        return Car(
            carId.toInt(),
            name,
            price,
            battery,
            potency.toInt(),
            rechargeTime.toInt(),
            photoUrl,
            true
        )
    }

    fun saveIfNotExists(car: Car) {
        val carFound = findCarById(car.id)
        if (carFound.id == ID_WHEN_NO_CAR_IS_FOUND) {
            save(car)
        }
    }

    fun getAll(): List<Car> {
        val dbHelper = CarsDBHelper(context)
        val db = dbHelper.readableDatabase

        val columns = arrayOf(
            BaseColumns._ID,
            COLUMN_NAME_CAR_ID,
            COLUMN_NAME_NAME,
            COLUMN_NAME_PRICE,
            COLUMN_NAME_BATTERY,
            COLUMN_NAME_POTENCY,
            COLUMN_NAME_RECHARGE_TIME,
            COLUMN_NAME_PHOTO_URL
        )

        val cursor = db.query(
            CarsContract.CarEntry.TABLE_NAME,
            columns,
            null,
            null,
            null,
            null,
            null
        )

        val cars = mutableListOf<Car>()

        with(cursor) {
            while (moveToNext()) {
                val carId = getLong(getColumnIndexOrThrow(COLUMN_NAME_CAR_ID))
                val name = getString(getColumnIndexOrThrow(COLUMN_NAME_NAME))
                val price = getFloat(getColumnIndexOrThrow(COLUMN_NAME_PRICE))
                val battery = getFloat(getColumnIndexOrThrow(COLUMN_NAME_BATTERY))
                val potency = getLong(getColumnIndexOrThrow(COLUMN_NAME_POTENCY))
                val rechargeTime = getLong(getColumnIndexOrThrow(COLUMN_NAME_RECHARGE_TIME))
                val photoUrl = getString(getColumnIndexOrThrow(COLUMN_NAME_PHOTO_URL))

                cars.add(
                    Car(
                        carId.toInt(),
                        name,
                        price,
                        battery,
                        potency.toInt(),
                        rechargeTime.toInt(),
                        photoUrl,
                        true
                    )
                )
            }
        }

        cursor.close()

        return cars
    }

    companion object {
        const val ID_WHEN_NO_CAR_IS_FOUND = 0
    }
}