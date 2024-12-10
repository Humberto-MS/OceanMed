package com.example.oceanmed.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.isDigitsOnly
import com.example.oceanmed.ui.theme.ContrastBLue
import com.example.oceanmed.ui.theme.DialogTextBlue
import com.example.oceanmed.ui.theme.PrimaryBlue
import com.example.oceanmed.ui.theme.SecondaryBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrUpdatePatientDialog (
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onSave: ( String, Int, Double, Double, String, String ) -> Unit,
    dialogTitle: String,
    dialogText: String,
    confirmButtonText: String
) {
    var isFormValid by remember { mutableStateOf ( false ) }
    var nombre by rememberSaveable { mutableStateOf ( "" ) }
    var edad by rememberSaveable { mutableStateOf ( "" ) }
    var peso by rememberSaveable { mutableStateOf ( "" ) }
    var altura by rememberSaveable { mutableStateOf ( "" ) }
    var talla by rememberSaveable { mutableStateOf ( "" ) }
    var condicion by rememberSaveable { mutableStateOf ( "" ) }

    // Dropdown menu
    var sizeDropdownExpanded by remember { mutableStateOf ( false ) }
    var conditionDropdownExpanded by remember { mutableStateOf ( false ) }
    val sizeOptions = listOf ( "XS", "S", "M", "L", "XL", "XXL" )

    val conditionOptions = listOf ( "Ninguna", "Diabetes", "Hipertensión", "Hipertiroidismo", "Hipotiroidismo",
                                    "Asma", "Artritis", "Epilepsia", "Esclerosis", "Colitis", "Parkinson" )

    fun validateForm () {
        isFormValid = nombre.isNotBlank() && edad.isNotBlank() && peso.isNotBlank() &&
                      altura.isNotBlank() && talla.isNotBlank() && condicion.isNotBlank()
    }

    fun clearForm () {
        nombre = ""
        edad = ""
        peso = ""
        altura = ""
        talla = ""
        condicion = ""
        isFormValid = false
    }

    if ( showDialog ) {
        AlertDialog (
            onDismissRequest = onDismiss,
            title = { Text ( dialogTitle, style = TextStyle ( ContrastBLue ), fontSize = 24.sp ) },
            text = {
                Column {
                    Text ( dialogText, style = TextStyle ( DialogTextBlue ) )

                    Spacer ( modifier = Modifier.height ( 16.dp ) )

                    OutlinedTextField (
                        value = nombre,
                        onValueChange = {
                            if ( it.all { char -> char.isLetter() || char.isWhitespace() } ) {
                                nombre = it
                                validateForm()
                            }
                        },
                        label = { Text ( "Nombre" ) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors ( focusedBorderColor = ContrastBLue, unfocusedBorderColor = DialogTextBlue, containerColor = Color.White ),
                        keyboardOptions = KeyboardOptions.Default.copy ( keyboardType = KeyboardType.Text )
                    )

                    Spacer ( modifier = Modifier.height ( 8.dp ) )

                    OutlinedTextField (
                        value = edad,
                        onValueChange = {
                            if ( it.isDigitsOnly() ) {
                                edad = it
                                validateForm()
                            }
                        },
                        label = { Text ( "Edad" ) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors ( focusedBorderColor = ContrastBLue, unfocusedBorderColor = DialogTextBlue, containerColor = Color.White ),
                        suffix = { Text ( "años" ) },
                        keyboardOptions = KeyboardOptions.Default.copy ( keyboardType = KeyboardType.Number )
                    )

                    Spacer ( modifier = Modifier.height ( 8.dp ) )

                    OutlinedTextField (
                        value = peso,
                        onValueChange = {
                            if ( it.toDoubleOrNull() != null ) {
                                peso = it
                                validateForm()
                            }
                        },
                        label = { Text ( "Peso" ) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors ( focusedBorderColor = ContrastBLue, unfocusedBorderColor = DialogTextBlue, containerColor = Color.White ),
                        suffix = { Text ( "kg" ) },
                        keyboardOptions = KeyboardOptions.Default.copy ( keyboardType = KeyboardType.Decimal )
                    )

                    Spacer ( modifier = Modifier.height ( 8.dp ) )

                    OutlinedTextField (
                        value = altura,
                        onValueChange = {
                            if ( it.toDoubleOrNull() != null ) {
                                altura = it
                                validateForm()
                            }
                        },
                        label = { Text ( "Altura" ) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors ( focusedBorderColor = ContrastBLue, unfocusedBorderColor = DialogTextBlue, containerColor = Color.White ),
                        suffix = { Text ( "m" ) },
                        keyboardOptions = KeyboardOptions.Default.copy ( keyboardType = KeyboardType.Decimal )
                    )

                    Spacer ( modifier = Modifier.height ( 8.dp ) )

                    ExposedDropdownMenuBox (
                        expanded = sizeDropdownExpanded,
                        onExpandedChange = {
                            sizeDropdownExpanded = !sizeDropdownExpanded
                        }
                    ) {
                        OutlinedTextField (
                            value = talla,
                            onValueChange = {
                                talla = it
                                validateForm()
                            },
                            label = { Text ( "Talla" ) },
                            readOnly = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            colors = TextFieldDefaults.outlinedTextFieldColors ( focusedBorderColor = ContrastBLue, unfocusedBorderColor = DialogTextBlue, containerColor = Color.White ),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon ( expanded = sizeDropdownExpanded ) }
                        )
                        ExposedDropdownMenu (
                            expanded = sizeDropdownExpanded,
                            onDismissRequest = { sizeDropdownExpanded = false },
                            modifier = Modifier
                                .background ( Color.White )
                                .heightIn ( max = 185.dp )
                        ) {
                            sizeOptions.forEach { option ->
                                DropdownMenuItem (
                                    text = { Text ( option ) },
                                    onClick = {
                                        talla = option
                                        sizeDropdownExpanded = false
                                        validateForm()
                                    }
                                )
                            }
                        }
                    }

                    Spacer ( modifier = Modifier.height ( 8.dp ) )

                    ExposedDropdownMenuBox (
                        expanded = conditionDropdownExpanded,
                        onExpandedChange = {
                            conditionDropdownExpanded = !conditionDropdownExpanded
                        }
                    ) {
                        OutlinedTextField (
                            value = condicion,
                            onValueChange = {
                                condicion = it
                                validateForm()
                            },
                            label = { Text ( "Condicion" ) },
                            readOnly = true,
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(),
                            colors = TextFieldDefaults.outlinedTextFieldColors ( focusedBorderColor = ContrastBLue, unfocusedBorderColor = DialogTextBlue, containerColor = Color.White ),
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon ( expanded = conditionDropdownExpanded ) }
                        )
                        ExposedDropdownMenu (
                            expanded = conditionDropdownExpanded,
                            onDismissRequest = { conditionDropdownExpanded = false },
                            modifier = Modifier
                                .background ( Color.White )
                                .heightIn ( max = 185.dp )
                        ) {
                            conditionOptions.forEach { option ->
                                DropdownMenuItem (
                                    text = { Text ( option ) },
                                    onClick = {
                                        condicion = option
                                        conditionDropdownExpanded = false
                                        validateForm()
                                    }
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button (
                    onClick = {
                        onSave ( nombre, edad.toInt(), peso.toDouble(), altura.toDouble(), talla, condicion )
                        clearForm()
                        onDismiss ()
                    },
                    colors = ButtonDefaults.buttonColors ( containerColor = PrimaryBlue, contentColor = Color.White ),
                    enabled = isFormValid
                ) {
                    Text ( confirmButtonText )
                }
            },
            dismissButton = {
                Button (
                    onClick = {
                        clearForm()
                        onDismiss()
                    } ,
                    colors = ButtonDefaults.buttonColors ( containerColor = PrimaryBlue, contentColor = Color.White )
                ) {
                    Text ( "Cancelar" )
                }
            },
            containerColor = SecondaryBlue
        )
    }
}