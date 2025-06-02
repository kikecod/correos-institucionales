package com.example.correos_institucionales.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.correos_institucionales.data.dao.CarreraDao
import com.example.correos_institucionales.data.entidades.Carrera
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CarreraViewModel(private val dao: CarreraDao) : ViewModel() {

    private val _listaCarreras = MutableStateFlow<List<Carrera>>(emptyList())
    val listaCarreras: StateFlow<List<Carrera>> = _listaCarreras

    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje

    fun cargarCarreras() {
        viewModelScope.launch {
            _listaCarreras.value = dao.obtenerTodos()
        }
    }

    fun insertar(nombre: String, facultad: String) {
        val nueva = Carrera(dao.generarNuevoId(), nombre, facultad)
        viewModelScope.launch {
            val exito = dao.insertar(nueva)
            _mensaje.value = if (exito) "✅ Carrera registrada" else "❌ Error al registrar"
            cargarCarreras()
        }
    }

    fun actualizar(carrera: Carrera) {
        viewModelScope.launch {
            val exito = dao.actualizar(carrera)
            _mensaje.value = if (exito) "✅ Actualizada" else "❌ Error al actualizar"
            cargarCarreras()
        }
    }

    fun eliminar(id: Int) {
        viewModelScope.launch {
            val exito = dao.eliminar(id)
            _mensaje.value = if (exito) "✅ Eliminada" else "❌ Error al eliminar"
            cargarCarreras()
        }
    }
}