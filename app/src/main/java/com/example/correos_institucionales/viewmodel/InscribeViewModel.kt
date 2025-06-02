package com.example.correos_institucionales.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.correos_institucionales.data.dao.InscribeDao
import com.example.correos_institucionales.data.entidades.Inscribe
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class InscribeViewModel(private val dao: InscribeDao) : ViewModel() {

    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje

    fun registrar(inscribe: Inscribe) {
        viewModelScope.launch {
            val exito = dao.insertar(inscribe)
            _mensaje.value = if (exito) "✅ Inscripción registrada" else "❌ Error al registrar"
        }
    }

//    fun actualizar(inscribe: Inscribe) {
//        viewModelScope.launch {
//            val exito = dao.actualizar(inscribe)
//            _mensaje.value = if (exito) "✅ Inscripción actualizada" else "❌ Error al actualizar"
//        }
//    }

    fun eliminar(ci: Int, idCarrera: Int) {
        viewModelScope.launch {
            val exito = dao.eliminar(ci, idCarrera)
            _mensaje.value = if (exito) "✅ Inscripción eliminada" else "❌ No encontrada"
        }
    }
}