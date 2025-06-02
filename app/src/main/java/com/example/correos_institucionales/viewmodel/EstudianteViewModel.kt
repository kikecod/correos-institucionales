package com.example.correos_institucionales.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.correos_institucionales.data.dao.EstudianteDao
import com.example.correos_institucionales.data.entidades.Estudiante
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EstudianteViewModel(private val dao: EstudianteDao) : ViewModel() {

    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje

    private val _estudianteEncontrado = MutableStateFlow<Estudiante?>(null)
    val estudianteEncontrado: StateFlow<Estudiante?> = _estudianteEncontrado

    private val _estudianteEditado = MutableStateFlow<Estudiante?>(null)
    val estudianteEditado: StateFlow<Estudiante?> = _estudianteEditado


    fun registrar(estudiante: Estudiante) {
        viewModelScope.launch {
            val exito = dao.insertar(estudiante)
            _mensaje.value = if (exito) "✅ Estudiante registrado correctamente" else "❌ Error al registrar"
        }
    }

    fun buscar(ci: Int) {
        viewModelScope.launch {
            _estudianteEncontrado.value = dao.obtener(ci)
            _mensaje.value = if (_estudianteEncontrado.value != null) "✅ Estudiante encontrado" else "❌ No se encontró el estudiante"
        }
    }

    fun actualizar(estudiante: Estudiante) {
        viewModelScope.launch {
            val exito = dao.actualizar(estudiante)
            _mensaje.value = if (exito) "✅ Estudiante actualizado" else "❌ Error al actualizar"
        }
    }

    fun eliminar(ci: Int) {
        viewModelScope.launch {
            val exito = dao.eliminar(ci)
            _mensaje.value = if (exito) "✅ Estudiante eliminado" else "❌ No se encontró para eliminar"
        }
    }

    fun setEstudianteEditado(estudiante: Estudiante?) {
        _estudianteEditado.value = estudiante
    }
    private val _listaEstudiantes = MutableStateFlow<List<Estudiante>>(emptyList())
    val listaEstudiantes: StateFlow<List<Estudiante>> = _listaEstudiantes

    fun obtenerTodos() {
        viewModelScope.launch {
            _listaEstudiantes.value = dao.obtenerTodos()
        }
    }
}