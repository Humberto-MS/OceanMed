package com.example.oceanmed.views

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.oceanmed.data.PatientData
import com.example.oceanmed.dialogs.AddOrUpdatePatientDialog
import com.example.oceanmed.ui.theme.ContrastBLue
import com.example.oceanmed.ui.theme.PrimaryBlue
import com.example.oceanmed.ui.theme.SecondaryBlue
import org.json.JSONArray
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistryView(navController: NavController, context: Context) {
    var showPatientDialog by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var searchBarActive by remember { mutableStateOf(false) }
    var patients by remember { mutableStateOf<List<PatientData>>(emptyList()) }

    // Llamada al backend al iniciar la vista
    LaunchedEffect(Unit) {
        fetchPatients(context) { fetchedPatients ->
            patients = fetchedPatients
        }
    }

    // Filtrar pacientes según el texto de búsqueda
    val patients_showed = if (searchText.isEmpty()) {
        patients
    } else {
        patients.filter { it.nombre.contains(searchText, ignoreCase = true) }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            SearchBar(
                query = searchText,
                onQueryChange = { searchText = it },
                onSearch = { },
                active = searchBarActive,
                onActiveChange = { searchBarActive = it },
                placeholder = { Text("Buscar paciente") },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                },
                shape = RoundedCornerShape(28.dp),
                shadowElevation = 0.dp,
                colors = SearchBarDefaults.colors(containerColor = SecondaryBlue),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp, top = 40.dp)
            ) {
                listaPacientes(patients_showed, navController)
            }

            listaPacientes(patients_showed, navController)
        }

        FloatingActionButton(
            onClick = { showPatientDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(0.dp, 0.dp, 20.dp, 40.dp),
            containerColor = SecondaryBlue
        ) {
            Icon(
                Icons.Default.Add,
                contentDescription = "Add",
                tint = ContrastBLue
            )
        }

        AddOrUpdatePatientDialog(
            showDialog = showPatientDialog,
            onDismiss = { showPatientDialog = false },
            onSave = { nombre, edad, peso, altura, talla, condicion ->
                addPatient(context, nombre, edad, peso, altura, talla, condicion) {
                    Toast.makeText(context, "Paciente añadido correctamente", Toast.LENGTH_SHORT).show()
                    fetchPatients(context) { updatedPatients ->
                        patients = updatedPatients
                    }
                }
            },
            dialogTitle = "Añadir Nuevo Paciente",
            dialogText = "Ingrese los datos del paciente",
            confirmButtonText = "Añadir"
        )
    }
}

@Composable
fun listaPacientes(patients: List<PatientData>, navController: NavController) {
    LazyColumn {
        items(patients) { patient ->
            Card(
                onClick = {
                    navController.navigate("patient/${patient.id_usuario}") // Navegar con el ID del usuario
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                colors = CardDefaults.cardColors(containerColor = PrimaryBlue)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            Icons.Default.Person,
                            contentDescription = null,
                            tint = SecondaryBlue,
                            modifier = Modifier.size(40.dp)
                        )

                        Spacer(modifier = Modifier.width(16.dp))

                        Column {
                            Text(
                                text = patient.nombre,
                                fontWeight = FontWeight.Bold,
                                fontSize = 18.sp,
                                color = Color.White
                            )
                            Text(
                                text = "${patient.edad} años - ${patient.condicion}",
                                fontSize = 14.sp,
                                color = Color.White
                            )
                        }
                    }

                    Icon(
                        Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = null,
                        tint = Color.White,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

// Función para obtener pacientes del backend
fun fetchPatients(context: Context, onResult: (List<PatientData>) -> Unit) {
    val url = "http://192.168.100.119/API_OceanMed/mostrar.php"
    val queue = Volley.newRequestQueue(context)

    val stringRequest = StringRequest(
        Request.Method.GET, url,
        Response.Listener { response ->
            try {
                val jsonArray = JSONArray(response)
                val patients = mutableListOf<PatientData>()

                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val id_usuario = jsonObject.getInt("id_usuario")
                    val nombre = jsonObject.getString("nombre")
                    val edad = jsonObject.getInt("edad")
                    val condicion = jsonObject.getString("condicion")

                    patients.add(PatientData(id_usuario, nombre, edad, condicion))
                }

                onResult(patients)
            } catch (e: Exception) {
                Log.e("Volley", "Error parsing response: ${e.message}")
                Toast.makeText(context, "Error al procesar los pacientes", Toast.LENGTH_LONG).show()
            }
        },
        Response.ErrorListener { error ->
            Log.e("Volley", "Error: ${error.message}")
            Toast.makeText(context, "Error al obtener los pacientes", Toast.LENGTH_LONG).show()
        }
    )

    queue.add(stringRequest)
}

// Función para añadir un paciente
fun addPatient(
    context: Context,
    nombre: String,
    edad: Int,
    peso: Double,
    altura: Double,
    talla: String,
    condicion: String,
    onResult: (Boolean) -> Unit
) {
    val url = "http://192.168.100.119/API_OceanMed/insertarUsuario.php"
    val queue = Volley.newRequestQueue(context)

    val stringRequest = object : StringRequest(
        Request.Method.POST, url,
        Response.Listener { response ->
            onResult(response.trim() == "El usuario se inserto de forma exitosa")
        },
        Response.ErrorListener { error ->
            Log.e("Volley", "Error: ${error.message}")
            onResult(false)
        }
    ) {
        override fun getParams(): MutableMap<String, String> {
            val params = HashMap<String, String>()
            params["nombre"] = nombre
            params["edad"] = edad.toString()
            params["peso"] = peso.toString()
            params["altura"] = altura.toString()
            params["talla"] = talla
            params["condicion"] = condicion
            return params
        }
    }

    queue.add(stringRequest)
}