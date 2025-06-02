package com.example.correos_institucionales.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.correos_institucionales.viewmodel.AsignaViewModel
import com.example.correos_institucionales.viewmodel.EstudianteViewModel
import com.example.correos_institucionales.ui.screens.*

@Composable
fun AppNavigation(
    navController: NavHostController,
    estudianteViewModel: EstudianteViewModel,
    asignaViewModel: AsignaViewModel
) {
    NavHost(
        navController = navController,
        startDestination = "main"
    ) {
        composable("main") {
            MainScreen(navController)
        }

        composable("registro_estudiante") {
            RegistroEstudianteScreen(
                viewModel = estudianteViewModel,
                onVolver = { navController.popBackStack() }
            )
        }

        composable("lista_estudiantes") {
            ListaEstudiantesScreen(
                navController = navController,
                viewModel = estudianteViewModel,
                onAgregar = { navController.navigate("registro_estudiante") },
                onEditar = { estudiante ->
                    estudianteViewModel.setEstudianteEditado(estudiante)
                    navController.navigate("editar_estudiante")
                }
            )
        }
        composable("lista_asignaciones") {
            ListaAsignacionesScreen(
                viewModel = asignaViewModel,
                onEditar = { asigna ->
                    asignaViewModel.setAsignacionEditada(asigna)
                    navController.navigate("editar_asignacion")
                },
                onVolver = { navController.popBackStack() }
            )
        }

        composable("editar_estudiante") {
            RegistroEstudianteScreen(
                viewModel = estudianteViewModel,
                estudianteEditado = estudianteViewModel.estudianteEditado.value,
                onVolver = { navController.popBackStack() }
            )
        }

        composable("asignar_cuenta") {
            AsignarCuentaScreen(
                viewModel = asignaViewModel,
                onVolver = { navController.popBackStack() }
            )
        }



        composable("editar_asignacion") {
            AsignarCuentaScreen(
                viewModel = asignaViewModel,
                asignacionEditada = asignaViewModel.asignacionEditada.value,
                onVolver = { navController.popBackStack() }
            )
        }
    }
}