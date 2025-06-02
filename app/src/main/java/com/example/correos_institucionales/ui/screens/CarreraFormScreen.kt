package com.example.correos_institucionales.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavBackStackEntry
import com.example.correos_institucionales.data.dao.CarreraDao
import com.example.correos_institucionales.data.database.Bdsqlite
import com.example.correos_institucionales.data.entidades.Carrera
import com.example.correos_institucionales.viewmodel.CarreraViewModel

@Composable
fun CarreraFormScreen(
    navController: NavController,
    viewModel: CarreraViewModel,
    backStackEntry: NavBackStackEntry
) {
    val context = LocalContext.current
    val idParam = backStackEntry.arguments?.getString("id")?.toIntOrNull()

    var nombre by remember { mutableStateOf("") }
    var facultad by remember { mutableStateOf("") }

    LaunchedEffect(idParam) {
        if (idParam != null) {
            val carrera = CarreraDao(Bdsqlite(context)).obtener(idParam)
            carrera?.let {
                nombre = it.nombre
                facultad = it.facultad
            }
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = if (idParam == null) "Nueva Carrera" else "Editar Carrera",
            style = MaterialTheme.typography.titleLarge
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = facultad,
            onValueChange = { facultad = it },
            label = { Text("Facultad") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                if (idParam == null) {
                    viewModel.insertar(nombre, facultad)
                } else {
                    viewModel.actualizar(Carrera(idParam, nombre, facultad))
                }
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = nombre.isNotBlank() && facultad.isNotBlank()
        ) {
            Text("Guardar")
        }
    }
}