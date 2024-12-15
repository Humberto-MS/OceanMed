package com.example.oceanmed.views

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.oceanmed.data.RegistryData
import com.example.oceanmed.dialogs.AddOrUpdatePatientDialog
import com.example.oceanmed.dialogs.AddRegistryDialog
import com.example.oceanmed.dialogs.SimpleDialog
import org.json.JSONArray
import org.json.JSONObject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PatientView(navController: NavController, context: Context, patientId: Int) {
    var showRegistryDialog by remember { mutableStateOf(false) }
    var showEditPatientDialog by remember { mutableStateOf(false) }
    var showDeletePatientDialog by remember { mutableStateOf(false) }
    var showDeleteRegistryDialog by remember { mutableStateOf(false) }

    var registros by remember { mutableStateOf(listOf<RegistryData>()) }
    var patientName by remember { mutableStateOf("Nombre del paciente") }
    var patientAge by remember { mutableStateOf(0) }
    var patientWeight by remember { mutableStateOf(0.0) }
    var patientHeight by remember { mutableStateOf(0.0) }
    var patientSize by remember { mutableStateOf("M") }
    var patientCondition by remember { mutableStateOf("Condición no definida") }

    var dataChanged by remember { mutableStateOf(false) } // Bandera para detectar cambios en datos

    // Cargar información del paciente y sus registros
    LaunchedEffect(patientId, dataChanged) {
        fetchPatientDetails(context, patientId) { name, age, weight, height, size, condition ->
            patientName = name
            patientAge = age
            patientWeight = weight
            patientHeight = height
            patientSize = size
            patientCondition = condition
        }
        fetchPatientRecords(context, patientId) { fetchedRecords ->
            registros = fetchedRecords
        }
        dataChanged = false // Reiniciar la bandera tras actualizar los datos
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp)
    ) {
        TopAppBar(
            title = {
                Text(
                    text = "Información del paciente",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(start = 20.dp)
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = { navController.popBackStack() },
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            actions = {
                IconButton(onClick = { showEditPatientDialog = true }) {
                    Icon(Icons.Default.Edit, contentDescription = "Edit")
                }
                IconButton(onClick = { showDeletePatientDialog = true }) {
                    Icon(Icons.Default.Delete, contentDescription = "Delete")
                }
            }
        )
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 140.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp, 0.dp)
        ) {
            // Datos del paciente
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text(text = "Nombre: $patientName")
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(text = "Peso: $patientWeight kg")
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(text = "Talla: $patientSize")
                    }
                    Column {
                        Text(text = "Edad: $patientAge años")
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(text = "Altura: $patientHeight m")
                        Spacer(modifier = Modifier.height(15.dp))
                        Text(text = "Condición: $patientCondition")
                    }
                }
            }

            // Lista de registros diarios
            Text(
                text = "Registros diarios",
                fontSize = 18.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            LazyColumn {
                items(registros) { registro ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(text = registro.fecha)
                            Spacer(modifier = Modifier.weight(1f))
                            Column {
                                Text(text = "Glucosa: ${registro.glucosa}")
                                Text(text = "Presión: ${registro.presion_s}/${registro.presion_d}")
                            }
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(onClick = {
                                deleteRegistry(context, registro.idRegistro) { success ->
                                    if (success) {
                                        Toast.makeText(context, "Registro eliminado correctamente", Toast.LENGTH_SHORT).show()
                                        // Actualizar lista localmente eliminando el registro
                                        registros = registros.filter { it.idRegistro != registro.idRegistro }
                                    } else {
                                        Toast.makeText(context, "Error al eliminar registro", Toast.LENGTH_SHORT).show()
                                    }
                                }
                            }) {
                                Icon(Icons.Default.Close, contentDescription = "Delete")
                            }
                        }
                    }
                }
            }
        }

        FloatingActionButton(
            onClick = { showRegistryDialog = true },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(0.dp, 0.dp, 20.dp, 40.dp)
        ) {
            Icon(Icons.Default.Add, contentDescription = "Add")
        }

        // Añadir registro
        AddRegistryDialog(
            showDialog = showRegistryDialog,
            onDismiss = { showRegistryDialog = false },
            onSave = { glucosa, presionS, presionD ->
                addRegistry(context, patientId, glucosa, presionS, presionD) { success ->
                    if (success) {
                        dataChanged = true // Notificar que los datos cambiaron
                        Toast.makeText(context, "Registro añadido", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Error al añadir registro", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        )

        // Editar paciente
        AddOrUpdatePatientDialog(
            showDialog = showEditPatientDialog,
            onDismiss = { showEditPatientDialog = false },
            onSave = { name, age, weight, height, size, condition ->
                updatePatient(context, patientId, name, age, weight, height, size, condition) { success ->
                    if (success) {
                        dataChanged = true // Notificar que los datos cambiaron
                        Toast.makeText(context, "Paciente actualizado", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, "Error al actualizar paciente", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            dialogTitle = "Editar Paciente",
            dialogText = "Actualiza los datos del paciente",
            confirmButtonText = "Guardar"
        )

        // Eliminar paciente
        SimpleDialog(
            showDialog = showDeletePatientDialog,
            onDismiss = { showDeletePatientDialog = false },
            onSave = {
                deletePatient(context, patientId) { success ->
                    if (success) {
                        Toast.makeText(context, "Paciente eliminado", Toast.LENGTH_SHORT).show()
                        navController.popBackStack() // Regresar al RegistryView
                    } else {
                        Toast.makeText(context, "Error al eliminar paciente", Toast.LENGTH_SHORT).show()
                    }
                }
            },
            dialogTitle = "Eliminar Paciente",
            dialogText = "¿Estás seguro que deseas eliminar al paciente?",
            confirmButtonText = "Eliminar"
        )
    }
}

fun fetchPatientDetails(
    context: Context,
    patientId: Int,
    onResult: (String, Int, Double, Double, String, String) -> Unit
) {
    val url = "http://192.168.100.119/API_OceanMed/mostrar.php?id_usuario=$patientId"
    val queue = Volley.newRequestQueue(context)

    val stringRequest = StringRequest(
        Request.Method.GET, url,
        Response.Listener { response ->
            try {
                // Procesa directamente como JSONObject
                val jsonObject = JSONObject(response)

                val name = jsonObject.getString("nombre")
                val age = jsonObject.getInt("edad")
                val weight = jsonObject.getDouble("peso")
                val height = jsonObject.getDouble("altura")
                val size = jsonObject.getString("talla")
                val condition = jsonObject.getString("condicion")

                // Llama al callback con los datos obtenidos
                onResult(name, age, weight, height, size, condition)
            } catch (e: Exception) {
                Log.e("PatientView", "Error parsing patient details: ${e.message}")
            }
        },
        Response.ErrorListener { error ->
            Log.e("PatientView", "Error fetching patient details: ${error.message}")
        }
    )

    queue.add(stringRequest)
}

fun fetchPatientRecords(
    context: Context,
    patientId: Int,
    onResult: (List<RegistryData>) -> Unit
) {
    val url = "http://192.168.100.119/API_OceanMed/getRegistros.php?id_usuario=$patientId"
    val queue = Volley.newRequestQueue(context)

    val stringRequest = StringRequest(
        Request.Method.GET, url,
        Response.Listener { response ->
            try {
                val jsonArray = JSONArray(response)
                val records = mutableListOf<RegistryData>()

                for (i in 0 until jsonArray.length()) {
                    val jsonObject = jsonArray.getJSONObject(i)
                    val idRegistro = jsonObject.getInt("id_registro")
                    val fecha = jsonObject.getString("fechaRegistro")
                    val glucosa = jsonObject.getDouble("nivel_glucosa")
                    val presionS = jsonObject.getInt("presion_sistolica")
                    val presionD = jsonObject.getInt("presion_diastolica")

                    records.add(RegistryData(idRegistro, fecha, glucosa, presionS, presionD))
                }

                onResult(records)
            } catch (e: Exception) {
                Log.e("PatientView", "Error parsing records: ${e.message}")
            }
        },
        Response.ErrorListener { error ->
            Log.e("PatientView", "Error fetching records: ${error.message}")
        }
    )

    queue.add(stringRequest)
}

fun addRegistry(
    context: Context,
    patientId: Int,
    glucosa: Double,
    presionS: Int,
    presionD: Int,
    onResult: (Boolean) -> Unit
) {
    val url = "http://192.168.100.119/API_OceanMed/insertarRegistro.php"
    val queue = Volley.newRequestQueue(context)

    val stringRequest = object : StringRequest(Request.Method.POST, url,
        Response.Listener { response ->
            onResult(response == "El registro se insertó de forma exitosa")
        },
        Response.ErrorListener { error ->
            Log.e("PatientView", "Error adding registry: ${error.message}")
            onResult(false)
        }) {
        override fun getParams(): MutableMap<String, String> {
            return hashMapOf(
                "id_usuario" to patientId.toString(),
                "nivel_glucosa" to glucosa.toString(),
                "presion_sistolica" to presionS.toString(),
                "presion_diastolica" to presionD.toString()
            )
        }
    }

    queue.add(stringRequest)
}

fun deletePatient(
    context: Context,
    patientId: Int,
    onResult: (Boolean) -> Unit
) {
    val url = "http://192.168.100.119/API_OceanMed/borrar.php"
    val queue = Volley.newRequestQueue(context)

    val stringRequest = object : StringRequest(Request.Method.POST, url,
        Response.Listener { response ->
            onResult(response.trim() == "Paciente eliminado correctamente")
        },
        Response.ErrorListener { error ->
            Log.e("PatientView", "Error deleting patient: ${error.message}")
            onResult(false)
        }) {
        override fun getParams(): MutableMap<String, String> {
            return hashMapOf("id_usuario" to patientId.toString())
        }
    }

    queue.add(stringRequest)
}

fun updatePatient(
    context: Context,
    patientId: Int,
    nombre: String,
    edad: Int,
    peso: Double,
    altura: Double,
    talla: String,
    condicion: String,
    onResult: (Boolean) -> Unit
) {
    val url = "http://192.168.100.119/API_OceanMed/editar.php"
    val queue = Volley.newRequestQueue(context)

    val stringRequest = object : StringRequest(Request.Method.POST, url,
        Response.Listener { response ->
            onResult(response == "El usuario se modifico correctamente")
        },
        Response.ErrorListener { error ->
            Log.e("PatientView", "Error updating patient: ${error.message}")
            onResult(false)
        }) {
        override fun getParams(): MutableMap<String, String> {
            return hashMapOf(
                "id_usuario" to patientId.toString(),
                "nombre" to nombre,
                "edad" to edad.toString(),
                "peso" to peso.toString(),
                "altura" to altura.toString(),
                "talla" to talla,
                "condicion" to condicion
            )
        }
    }

    queue.add(stringRequest)
}

fun deleteRegistry(
    context: Context,
    registryId: Int,
    onResult: (Boolean) -> Unit
) {
    val url = "http://192.168.100.119/API_OceanMed/borrarRegistro.php"
    val queue = Volley.newRequestQueue(context)

    val stringRequest = object : StringRequest(Request.Method.POST, url,
        Response.Listener { response ->
            onResult(response.trim() == "El registro se elimino correctamente")
        },
        Response.ErrorListener { error ->
            Log.e("PatientView", "Error deleting registry: ${error.message}")
            onResult(false)
        }) {
        override fun getParams(): MutableMap<String, String> {
            return hashMapOf("id_registro" to registryId.toString())
        }
    }

    queue.add(stringRequest)
}