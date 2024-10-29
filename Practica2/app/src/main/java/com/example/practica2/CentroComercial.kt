package com.example.practica2

data class CentroComercial(
    val nombre: String,
    val dirrecion: String,
    val numTiendas: Int,
    val imagen: Int,
    val tiendas: List<Tienda>
)
