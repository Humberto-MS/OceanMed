package com.example.oceanmed.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.oceanmed.dataStore.StoreBoarding
import com.example.oceanmed.views.MainViewBoarding
import com.example.oceanmed.views.PatientView
import com.example.oceanmed.views.RegistryView
import com.example.oceanmed.views.SplashScreen

@Composable
fun NavManager() {
    val context = LocalContext.current // Obtener el contexto actual
    val dataStore = StoreBoarding(context)
    val store = dataStore.getStoreBoarding.collectAsState(initial = false)
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (store.value == true) "registry" else "splash"
    ) {
        composable(route = "boarding") {
            MainViewBoarding(navController = navController, store = dataStore)
        }
        composable(route = "registry") {
            RegistryView(navController = navController, context = context) // Pasar el contexto
        }
        composable(
            route = "patient/{patientId}",
            arguments = listOf(navArgument("patientId") { type = NavType.IntType }) // Argumento dinámico
        ) { backStackEntry ->
            val patientId = backStackEntry.arguments?.getInt("patientId") ?: -1
            PatientView(navController = navController, context = context, patientId = patientId)
        }
        composable(route = "splash") {
            SplashScreen(navController = navController, store.value)
        }
    }
}

