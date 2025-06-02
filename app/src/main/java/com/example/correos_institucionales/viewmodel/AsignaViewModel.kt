package com.example.correos_institucionales.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.correos_institucionales.data.dao.AsignaDao
import com.example.correos_institucionales.data.dao.CuentaDao
import com.example.correos_institucionales.data.dao.EstudianteDao
import com.example.correos_institucionales.data.entidades.Asigna
import com.example.correos_institucionales.data.entidades.AsignacionDetalle
import com.example.correos_institucionales.data.entidades.Cuenta
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class AsignaViewModel(
    private val asignaDao: AsignaDao,
    private val cuentaDao: CuentaDao,
    private val estudianteDao: EstudianteDao
) : ViewModel() {

    private val _mensaje = MutableStateFlow("")
    val mensaje: StateFlow<String> = _mensaje

    private val _asignacionEditada = MutableStateFlow<Asigna?>(null)
    val asignacionEditada: StateFlow<Asigna?> = _asignacionEditada

    private val _listaAsignaciones = MutableStateFlow<List<Asigna>>(emptyList())
    val listaAsignaciones: StateFlow<List<Asigna>> = _listaAsignaciones

    private val _listaDetalles = MutableStateFlow<List<AsignacionDetalle>>(emptyList())
    val listaDetalles = _listaDetalles.asStateFlow()

    fun cargarAsignacionesDetalladas() {
        viewModelScope.launch {
            _listaDetalles.value = asignaDao.obtenerAsignacionesDetalladas()
        }
    }

    fun cargarAsignaciones() {
        viewModelScope.launch {
            _listaAsignaciones.value = asignaDao.obtenerTodos()
        }
    }

    fun setAsignacionEditada(asigna: Asigna?) {
        _asignacionEditada.value = asigna
    }



    fun asignarCorreo(ci: Int, tipoCuenta: String) {
        viewModelScope.launch {
            if (cuentaDao.existeCi(ci)) {
                _mensaje.value = "❌ Este estudiante ya tiene una cuenta"
                return@launch
            }
            val estudiante = estudianteDao.obtener(ci)
            if (estudiante == null) {
                _mensaje.value = "❌ Estudiante no encontrado"
                return@launch
            }

            val base = "${estudiante.nombre.first().lowercase()}${estudiante.apPaterno.lowercase()}${estudiante.apMaterno.first().lowercase()}@umsa.bo"
            var direccion = base
            var sufijo = 1
            while (cuentaDao.existeCorreo(direccion)) {
                direccion = base.replace("@", "$sufijo@")
                sufijo++
            }

            val nuevoId = cuentaDao.generarNuevoId()

            val cuenta = Cuenta(
                idCuenta = nuevoId,
                direccionEmail = direccion,
                tipo = tipoCuenta,
                estado = "Activa"
            )

            val fecha = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())

            val cuentaInsertada = cuentaDao.insertar(cuenta)
            if (!cuentaInsertada) {
                _mensaje.value = "❌ Error al crear cuenta"
                return@launch
            }

            val asignacion = Asigna(
                idCuenta = nuevoId,
                ci = ci,
                fechaAsignacion = fecha
            )

            val exito = asignaDao.insertar(asignacion)
            _mensaje.value = if (exito) {
                cargarAsignaciones()
                "✅ Correo asignado: $direccion"
            } else "❌ Error al asignar"

            if (tipoCuenta != "docente" && tipoCuenta != "estudiante") {
                _mensaje.value = "❌ Tipo inválido. Solo 'docente' o 'estudiante'"
                return@launch
            }
        }

    }

    fun actualizar(asigna: Asigna) {
        viewModelScope.launch {
            val exito = asignaDao.actualizar(asigna)
            _mensaje.value = if (exito) "✅ Asignación actualizada" else "❌ Error al actualizar"
        }
    }

    fun eliminar(idCuenta: Int, ci: Int) {
        viewModelScope.launch {
            val exitoAsigna = asignaDao.eliminar(idCuenta, ci)
            val exitoCuenta = cuentaDao.eliminar(idCuenta)
            _mensaje.value = if (exitoAsigna && exitoCuenta) "✅ Asignación y cuenta eliminadas"
            else "❌ Error al eliminar"
            if (exitoAsigna) cargarAsignacionesDetalladas()
        }
    }

}
