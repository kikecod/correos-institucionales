package com.example.correos_institucionales

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.correos_institucionales.data.database.Bdsqlite
import com.example.correos_institucionales.data.dao.AsignaDao
import com.example.correos_institucionales.data.dao.CuentaDao
import com.example.correos_institucionales.data.dao.EstudianteDao
import com.example.correos_institucionales.ui.navigation.AppNavigation
import com.example.correos_institucionales.viewmodel.AsignaViewModel
import com.example.correos_institucionales.viewmodel.EstudianteViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppContent()
        }
    }
}

@Composable
fun AppContent() {
    val context = LocalContext.current
    val bd = remember { Bdsqlite(context) }

    val estudianteViewModel = remember { EstudianteViewModel(EstudianteDao(bd)) }
    val asignaViewModel = remember { AsignaViewModel(AsignaDao(bd), CuentaDao(context), EstudianteDao(bd)) }

    val navController = rememberNavController()

    AppNavigation(
        navController = navController,
        estudianteViewModel = estudianteViewModel,
        asignaViewModel = asignaViewModel
    )
}