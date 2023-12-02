package com.example.agregar_editar.adapter

import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.agregar_editar.R
import com.example.agregar_editar.entity.Ingrediente
import com.example.agregar_editar.entity.IngredienteCheckBox

class IngredienteAdapter(
    private val ingredientes: List<IngredienteCheckBox>,
    private val onIngredientCheckedListener: (IngredienteCheckBox) -> Unit,
    private val onEditTextChangedListener: (IngredienteCheckBox, String) -> Unit
) : RecyclerView.Adapter<IngredienteAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkIngrediente) // cambiado}
        val editText: EditText = itemView.findViewById(R.id.txt_cantidad)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.ingrediente_items, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ingrediente = ingredientes[position]
        holder.checkBox.text = ingrediente.ingrediente.nombre
        holder.checkBox.isChecked = ingrediente.isSelected
        holder.editText.setText(ingrediente.editTextValue)

        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            ingrediente.isSelected = isChecked
            onIngredientCheckedListener(ingrediente)
        }

        holder.editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                ingrediente.editTextValue = s?.toString() ?: ""
                onEditTextChangedListener(ingrediente, ingrediente.editTextValue)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    override fun getItemCount(): Int {
        return ingredientes.size
    }
}