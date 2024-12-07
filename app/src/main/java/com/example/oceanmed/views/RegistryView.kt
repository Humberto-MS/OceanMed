package com.example.oceanmed.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistryView ( navController: NavController ) {
    var searchText by remember { mutableStateOf ( "" ) }

    // usar una funcion que regrese los pacientes
    // quitar estos pacientes de ejemplo y guardar en esta lista los pacientes traidos
    // desde la base de datos mediante ese metodo
    val patients = listOf (
        "John Doe",
        "María Pérez",
        "Roberto Rodríguez",
        "Sofía García",
        "Humberto medina",
        "Humberto medina",
        "Humberto medina",
        "Humberto medina",
        "Humberto medina",
        "Humberto medina",
        "Humberto medina",
        "Humberto medina",
    )

    Box ( modifier = Modifier.fillMaxSize() ) {
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            TextField (
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text ( "Buscar paciente" ) },
                leadingIcon = {
                    Icon (
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                },
                shape = RoundedCornerShape ( 28.dp ),
                colors = TextFieldDefaults.textFieldColors (
                    containerColor = Color ( 0xFFF0F0F0 ),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding ( bottom = 16.dp )
                    .padding ( top = 40.dp )
            )

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
                            containerColor = Color ( 0xFF008BB2 )
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
                                    tint = Color.White,
                                    modifier = Modifier.size ( 40.dp )
                                )

                                Spacer ( modifier = Modifier.width ( 16.dp ) )

                                Column {
                                    Text (
                                        text = patient,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 18.sp,
                                        color = Color.White
                                    )
                                    Text (
                                        text = "Datos x",
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

        FloatingActionButton (
            onClick = { /* TODO: Add new patient */ },
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

//@Preview( showBackground = true)
//@Composable
//fun PreviewRegistryView() {
//    RegistryView ( navController = null )
//}