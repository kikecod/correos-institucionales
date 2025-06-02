package com.example.correos_institucionales.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.correos_institucionales.data.dao.CuentaDao
import com.example.correos_institucionales.data.entidades.Cuenta
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CuentaViewModel(private val dao: CuentaDao) : ViewModel() {

    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje

    private val _cuentaEncontrada = MutableStateFlow<Cuenta?>(null)
    val cuentaEncontrada: StateFlow<Cuenta?> = _cuentaEncontrada

    fun registrar(cuenta: Cuenta) {
        viewModelScope.launch {
            val exito = dao.insertar(cuenta)
            _mensaje.value = if (exito) "✅ Cuenta registrada correctamente" else "❌ Error al registrar"
        }
    }

    fun buscar(id: Int) {
        viewModelScope.launch {
            _cuentaEncontrada.value = dao.obtener(id)
            _mensaje.value = if (_cuentaEncontrada.value != null) "✅ Cuenta encontrada" else "❌ No se encontró la cuenta"
        }
    }

    fun actualizar(cuenta: Cuenta) {
        viewModelScope.launch {
            val exito = dao.actualizar(cuenta)
            _mensaje.value = if (exito) "✅ Cuenta actualizada" else "❌ Error al actualizar"
        }
    }

    fun eliminar(id: Int) {
        viewModelScope.launch {
            val exito = dao.eliminar(id)
            _mensaje.value = if (exito) "✅ Cuenta eliminada" else "❌ No se encontró para eliminar"
        }
    }
}