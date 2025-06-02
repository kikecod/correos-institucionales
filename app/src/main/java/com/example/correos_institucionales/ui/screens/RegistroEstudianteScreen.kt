package com.example.correos_institucionales.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.correos_institucionales.data.entidades.Estudiante
import com.example.correos_institucionales.viewmodel.EstudianteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroEstudianteScreen(
    viewModel: EstudianteViewModel,
    estudianteEditado: Estudiante? = null,
    onVolver: () -> Unit
) {
    var ci by remember { mutableStateOf(estudianteEditado?.ci?.toString() ?: "") }
    var nombre by remember { mutableStateOf(estudianteEditado?.nombre ?: "") }
    var apPaterno by remember { mutableStateOf(estudianteEditado?.apPaterno ?: "") }
    var apMaterno by remember { mutableStateOf(estudianteEditado?.apMaterno ?: "") }
    var fechaNac by remember { mutableStateOf(estudianteEditado?.fechaNac ?: "") }
    var sexo by remember { mutableStateOf(estudianteEditado?.sexo ?: "") }
    var telefono by remember { mutableStateOf(estudianteEditado?.telefono ?: "") }
    var email by remember { mutableStateOf(estudianteEditado?.emailPersonal ?: "") }
    var expandedSexo by remember { mutableStateOf(false) }

    val mensaje by viewModel.mensaje.collectAsState()
    val opcionesSexo = listOf("Masculino", "Femenino", "Otro")

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (estudianteEditado == null) "Registrar Estudiante" else "Editar Estudiante",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Card de información personal
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Título de la sección
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Información Personal",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))

                    // Campos de información personal
                    OutlinedTextField(
                        value = ci,
                        onValueChange = { ci = it },
                        label = { Text("Cédula de Identidad") },
                        leadingIcon = { Icon(Icons.Default.Badge, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        enabled = estudianteEditado == null,
                        shape = RoundedCornerShape(12.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
                        )
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = nombre,
                            onValueChange = { nombre = it },
                            label = { Text("Nombre") },
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        )
                        OutlinedTextField(
                            value = apPaterno,
                            onValueChange = { apPaterno = it },
                            label = { Text("Ap. Paterno") },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        )
                    }

                    OutlinedTextField(
                        value = apMaterno,
                        onValueChange = { apMaterno = it },
                        label = { Text("Apellido Materno") },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedTextField(
                            value = fechaNac,
                            onValueChange = { fechaNac = it },
                            label = { Text("Fecha Nac.") },
                            placeholder = { Text("YYYY-MM-DD") },
                            leadingIcon = { Icon(Icons.Default.CalendarToday, contentDescription = null) },
                            modifier = Modifier.weight(1f),
                            shape = RoundedCornerShape(12.dp)
                        )

                        // Dropdown para sexo
                        ExposedDropdownMenuBox(
                            expanded = expandedSexo,
                            onExpandedChange = { expandedSexo = !expandedSexo },
                            modifier = Modifier.weight(1f)
                        ) {
                            OutlinedTextField(
                                value = sexo,
                                onValueChange = { },
                                readOnly = true,
                                label = { Text("Sexo") },
                                leadingIcon = { Icon(Icons.Default.Wc, contentDescription = null) },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandedSexo)
                                },
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .menuAnchor(),
                                shape = RoundedCornerShape(12.dp)
                            )

                            ExposedDropdownMenu(
                                expanded = expandedSexo,
                                onDismissRequest = { expandedSexo = false }
                            ) {
                                opcionesSexo.forEach { opcion ->
                                    DropdownMenuItem(
                                        text = { Text(opcion) },
                                        onClick = {
                                            sexo = opcion
                                            expandedSexo = false
                                        }
                                    )
                                }
                            }
                        }
                    }
                }
            }

            // Card de información de contacto
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Título de la sección
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            Icons.Default.ContactPhone,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Información de Contacto",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Divider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))

                    OutlinedTextField(
                        value = telefono,
                        onValueChange = { telefono = it },
                        label = { Text("Teléfono") },
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )

                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email Personal") },
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    )
                }
            }

            // Botones de acción
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {
                            val estudiante = Estudiante(
                                ci = ci.toIntOrNull() ?: -1,
                                nombre = nombre,
                                apPaterno = apPaterno,
                                apMaterno = apMaterno,
                                fechaNac = fechaNac,
                                sexo = sexo,
                                telefono = telefono,
                                emailPersonal = email
                            )
                            if (estudianteEditado == null) {
                                viewModel.registrar(estudiante)
                            } else {
                                viewModel.actualizar(estudiante)
                            }
                            onVolver()
                        },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    ) {
                        Icon(
                            Icons.Default.Save,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = if (estudianteEditado == null) "Registrar Estudiante" else "Guardar Cambios",
                            fontWeight = FontWeight.Medium
                        )
                    }

                    OutlinedButton(
                        onClick = onVolver,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            "Cancelar",
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            // Mensaje de estado
            if (mensaje.isNotBlank()) {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.secondaryContainer
                    )
                ) {
                    Text(
                        text = mensaje,
                        modifier = Modifier.padding(16.dp),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
    }
}
