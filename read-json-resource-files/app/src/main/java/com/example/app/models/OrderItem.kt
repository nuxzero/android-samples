package com.example.app.models

import com.squareup.moshi.Json


data class OrderItem(
    val id: Int,
    @Json(name = "product_id")
    val productId: Int,
    val quantity: Int
)
