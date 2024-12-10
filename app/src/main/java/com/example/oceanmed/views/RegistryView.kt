package com.example.oceanmed.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarColors
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.oceanmed.data.PatientData
import com.example.oceanmed.dialogs.AddOrUpdatePatientDialog
import com.example.oceanmed.ui.theme.ContrastBLue
import com.example.oceanmed.ui.theme.PrimaryBlue
import com.example.oceanmed.ui.theme.SecondaryBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistryView ( navController: NavController ) {
    var showPatientDialog by remember { mutableStateOf ( false ) }
    var searchText by remember { mutableStateOf ( "" ) }
    var searchBarActive by remember { mutableStateOf ( false ) }

    // usar una funcion que regrese los pacientes con la info de PatientData
    // quitar estos pacientes de ejemplo y guardar en esta lista los pacientes traidos
    // desde la base de datos mediante ese metodo
    val patients = listOf (
        PatientData ( "John Doe", 21, "Diabético" ),
        PatientData ( "María Pérez", 21, "Diabético" ),
        PatientData ( "Roberto Rodriguez", 21, "Diabético" ),
        PatientData ( "Sofia García", 21, "Diabético" ),
        PatientData ( "Jesus Flores", 21, "Diabético" ),
        PatientData ( "Mariana Onofre", 21, "Diabético" ),
        PatientData ( "Elena Maldonado", 21, "Diabético" ),
        PatientData ( "Francisco Ruiz", 21, "Diabético" ),
        PatientData ( "Itzel Orona", 21, "Diabético" ),
        PatientData ( "Andrea Chavez", 21, "Diabético" ),
        PatientData ( "Humberto Medina", 21, "Diabético" ),
        PatientData ( "Rodrigo Macias", 21, "Diabético" ),
        PatientData ( "Fernanda Torres", 21, "Diabético" ),
    )

    val patients_showed = if ( searchText.isEmpty() ) {
        patients
    } else {
        patients.filter { it.nombre.contains ( searchText, ignoreCase = true ) }
    }

    Box ( modifier = Modifier.fillMaxSize() ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            SearchBar (
                query = searchText,
                onQueryChange = { searchText = it },
                onSearch = {  },
                active = searchBarActive,
                onActiveChange = { searchBarActive = it },
                placeholder = { Text ( "Buscar paciente" ) },
                leadingIcon = {
                    Icon (
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                },
                shape = RoundedCornerShape ( 28.dp ),
                shadowElevation = 0.dp,
                colors = SearchBarDefaults.colors ( containerColor = SecondaryBlue ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
                    .padding(top = 40.dp)
            ) {
                listaPacientes ( patients = patients_showed, navController = navController )
            }

            listaPacientes ( patients = patients_showed, navController = navController )
        }

        FloatingActionButton (
            onClick = { showPatientDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(0.dp, 0.dp, 20.dp, 40.dp),
            containerColor = SecondaryBlue
        ) {
            Icon (
                Icons.Default.Add,
                contentDescription = "Add",
                tint = ContrastBLue
            )
        }

        AddOrUpdatePatientDialog (
            showDialog = showPatientDialog,
            onDismiss = { showPatientDialog = false },
            onSave = { nombre, edad, peso, altura, talla, condicion -> /* TODO */ },
            dialogTitle = "Añadir Nuevo Paciente",
            dialogText = "Ingrese los datos del paciente",
            confirmButtonText = "Añadir"
        )
    }
}

@Composable
fun listaPacientes ( patients: List<PatientData>, navController: NavController ) {
    LazyColumn {
        items ( patients ) { patient ->
            Card (
                onClick = {
                    navController.navigate ( "patient" )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                colors = CardDefaults.cardColors (
                    containerColor = PrimaryBlue
                )
            ) {
                Row (
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row (
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon (
                            Icons.Default.Person,
                            contentDescription = null,
                            tint = SecondaryBlue,
                            modifier = Modifier.size ( 40.dp )
                        )

                        Spacer ( modifier = Modifier.width ( 16.dp ) )

                        Column {
                            Text (
                                text = patient.nombre,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color.White
                            )
                            Text (
                                text = "${patient.edad} años - ${patient.condicion}",
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        }
                    }

                    Icon (
                        Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size ( 24.dp )
                    )
                }
            }
        }
    }
}