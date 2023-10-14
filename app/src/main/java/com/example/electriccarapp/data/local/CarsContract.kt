package com.example.electriccarapp.data.local

import android.provider.BaseColumns

object CarsContract {
    object CarEntry : BaseColumns {
        const val TABLE_NAME = "car"
        const val COLUMN_NAME_CAR_ID = "car_id"
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_PRICE = "price"
        const val COLUMN_NAME_BATTERY = "battery"
        const val COLUMN_NAME_POTENCY = "potency"
        const val COLUMN_NAME_RECHARGE_TIME = "recharge_time"
        const val COLUMN_NAME_PHOTO_URL = "photo_url"
    }

    const val TABLE_CAR =
        "CREATE TABLE ${CarEntry.TABLE_NAME} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY, " +
                "${CarEntry.COLUMN_NAME_CAR_ID} INTEGER, " +
                "${CarEntry.COLUMN_NAME_NAME} TEXT, " +
                "${CarEntry.COLUMN_NAME_PRICE} REAL, " +
                "${CarEntry.COLUMN_NAME_BATTERY} REAL, " +
                "${CarEntry.COLUMN_NAME_POTENCY} INTEGER, " +
                "${CarEntry.COLUMN_NAME_RECHARGE_TIME} INTEGER, " +
                "${CarEntry.COLUMN_NAME_PHOTO_URL} TEXT)"

    const val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS ${CarEntry.TABLE_NAME}"
}