package com.example.oceanmed.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.oceanmed.data.RegistryData

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientView ( navController: NavController ) {
    // guardar en estas variables la info del usuario traida desde la bd
    val nombre: String = "John Doe"
    val edad: Int = 22
    val peso: Int = 80
    val altura: Double = 1.79
    val talla: String = "Mediana"
    val condicion: String = "Diabético"

    // usar una funcion que regrese los registros con los datos como en RegistryData
    // quitar estos registros de ejemplo y guardar en esta lista los registros traidos
    // desde la base de datos mediante ese metodo
    val registros = listOf (
        RegistryData (
            "Martes 03 Diciembre 2024",
            1.2,
            1,
            2
        ),
        RegistryData (
            "Martes 03 Diciembre 2024",
            1.2,
            1,
            2
        ),
        RegistryData (
            "Martes 03 Diciembre 2024",
            1.2,
            1,
            2
        ),
    )

    Box (
        modifier = Modifier
            .fillMaxSize()
            .padding ( top = 20.dp )
    ) {
        TopAppBar (
            title = {
                Text (
                    text = "Información del paciente",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding ( start = 20.dp )
                )
            },

            navigationIcon = {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.size ( 40.dp )
                ) {
                    Icon (
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },

            actions = {
                IconButton (
                    onClick = { /* TODO: Edit action */ }
                ) {
                    Icon(
                        Icons.Default.Edit,
                        contentDescription = "Edit"
                    )
                }
                IconButton (
                    onClick = { /* TODO: Delete action */ }
                ) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = "Delete"
                    )
                }
            },

            colors = TopAppBarDefaults.topAppBarColors (
                containerColor = Color ( 0xFFE7FAFC )
            )
        )
    }

    Box (
        modifier = Modifier
            .fillMaxSize()
            .padding ( top = 120.dp )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp, 0.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                colors = CardDefaults.cardColors (
                    containerColor = Color ( 0xFFE7FAFC )
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = "Nombre: ${nombre}")
                        Spacer(modifier = Modifier.height ( 15.dp ) )
                        Text(text = "Peso: ${peso} kg")
                        Spacer(modifier = Modifier.height ( 15.dp ) )
                        Text(text = "Talla: ${talla}")
                    }
                    Column {
                        Text(text = "Edad: ${edad} años")
                        Spacer(modifier = Modifier.height ( 15.dp ) )
                        Text(text = "Altura: ${altura} m")
                        Spacer(modifier = Modifier.height ( 15.dp ) )
                        Text(text = "Condición: ${condicion}")
                    }
                }
            }

            Text (
                text = "Registros diarios",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding ( bottom = 8.dp )
            )

            LazyColumn {
                items ( registros ) { registro ->
                    Card (
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                        colors = CardDefaults.cardColors (
                            containerColor = Color ( 0xFFE7FAFC )
                        )
                    ) {
                        Row (
                            modifier = Modifier.padding ( 16.dp ),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text ( text = registro.fecha )
                            Spacer ( modifier = Modifier.weight ( 1f ) )
                            Column {
                                Text ( text = "Glucosa: ${registro.glucosa}" )
                                Text ( text = "Presión: ${registro.presion_s}/${registro.presion_d}" )
                            }
                            Spacer ( modifier = Modifier.weight ( 1f ) )
                            IconButton (
                                onClick = { /* TODO: Handle delete action */ }
                            ) {
                                Icon (
                                    Icons.Default.Close,
                                    contentDescription = "Delete"
                                )
                            }
                        }
                    }

//                    HorizontalDivider (
//                        color = Color.Gray,
//                        modifier = Modifier
//                            .width(1.dp)
//                            .fillMaxHeight()
//                    )
                }
            }
        }

        FloatingActionButton(
            onClick = { /* TODO: Add action */ },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(0.dp, 0.dp, 20.dp, 40.dp)
        ) {
            Icon (
                Icons.Default.Add,
                contentDescription = "Add"
            )
        }
    }
}

//@Preview ( showBackground = true)
//@Composable
//fun PreviewPatientView() {
//    PatientView ( navController = null )
//}