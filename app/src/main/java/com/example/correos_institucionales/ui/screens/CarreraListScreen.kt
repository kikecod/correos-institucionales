package com.example.correos_institucionales.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.correos_institucionales.data.entidades.Carrera
import com.example.correos_institucionales.viewmodel.CarreraViewModel

@Composable
fun CarreraListScreen(
    navController: NavController,
    viewModel: CarreraViewModel
) {
    val carreras by viewModel.listaCarreras.collectAsState()
    val mensaje by viewModel.mensaje.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.cargarCarreras()
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Text("Lista de Carreras", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = { navController.navigate("form_carrera") }) {
            Text("Agregar Carrera")
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(carreras) { carrera ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(8.dp)) {
                        Text("ID: ${carrera.idCarrera}")
                        Text("Nombre: ${carrera.nombre}")
                        Text("Facultad: ${carrera.facultad}")

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End
                        ) {
                            TextButton(onClick = {
                                navController.navigate("form_carrera?id=${carrera.idCarrera}")
                            }) {
                                Text("Editar")
                            }
                            TextButton(onClick = {
                                viewModel.eliminar(carrera.idCarrera)
                            }) {
                                Text("Eliminar")
                            }
                        }
                    }
                }
            }
        }

        if (mensaje.isNotBlank()) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(mensaje)
        }
    }
}