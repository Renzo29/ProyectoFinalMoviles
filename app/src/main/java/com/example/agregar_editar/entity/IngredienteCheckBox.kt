package com.example.agregar_editar.entity

data class IngredienteCheckBox (
    val ingrediente: Ingrediente,
    var isSelected: Boolean = false,
    var editTextValue: String = ""
)