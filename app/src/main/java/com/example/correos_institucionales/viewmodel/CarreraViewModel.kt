package com.example.correos_institucionales.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.correos_institucionales.data.dao.CarreraDao
import com.example.correos_institucionales.data.entidades.Carrera
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CarreraViewModel(private val dao: CarreraDao) : ViewModel() {

    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje

    private val _carreraEncontrada = MutableStateFlow<Carrera?>(null)
    val carreraEncontrada: StateFlow<Carrera?> = _carreraEncontrada

    fun registrar(carrera: Carrera) {
        viewModelScope.launch {
            val exito = dao.insertar(carrera)
            _mensaje.value = if (exito) "✅ Carrera registrada correctamente" else "❌ Error al registrar"
        }
    }

    fun buscar(id: Int) {
        viewModelScope.launch {
            _carreraEncontrada.value = dao.obtener(id)
            _mensaje.value = if (_carreraEncontrada.value != null) "✅ Carrera encontrada" else "❌ No se encontró la carrera"
        }
    }

    fun actualizar(carrera: Carrera) {
        viewModelScope.launch {
            val exito = dao.actualizar(carrera)
            _mensaje.value = if (exito) "✅ Carrera actualizada" else "❌ Error al actualizar"
        }
    }

    fun eliminar(id: Int) {
        viewModelScope.launch {
            val exito = dao.eliminar(id)
            _mensaje.value = if (exito) "✅ Carrera eliminada" else "❌ No se encontró para eliminar"
        }
    }
}