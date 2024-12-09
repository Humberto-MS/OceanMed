package com.example.oceanmed.dialogs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
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
fun AddRegistryDialog (
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onSave: ( Double, Int, Int ) -> Unit
) {
    var isFormValid by remember { mutableStateOf ( false ) }
    var glucosa by rememberSaveable { mutableStateOf ( "" ) }
    var presionS by rememberSaveable { mutableStateOf ( "" ) }
    var presionD by rememberSaveable { mutableStateOf ( "" ) }

    fun validateForm () {
        isFormValid = glucosa.isNotBlank() && presionS.isNotBlank() && presionD.isNotBlank()
    }

    fun clearForm () {
        glucosa = ""
        presionS = ""
        presionD = ""
        isFormValid = false
    }

    if ( showDialog ) {
        AlertDialog (
            onDismissRequest = onDismiss,
            title = { Text ( "Registro Diario", style = TextStyle ( ContrastBLue ), fontSize = 24.sp ) },
            text = {
                Column {
                    Text ( "Ingrese los datos vitales del paciente", style = TextStyle ( DialogTextBlue ) )

                    Spacer ( modifier = Modifier.height ( 16.dp ) )

                    OutlinedTextField (
                        value = glucosa,
                        onValueChange = {
                            if ( it.toDoubleOrNull() != null ) {
                                glucosa = it
                                validateForm()
                            }
                        },
                        label = { Text ( "Glucosa" ) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors ( focusedBorderColor = ContrastBLue, unfocusedBorderColor = DialogTextBlue, containerColor = Color.White ),
                        suffix = { Text ( "mg/dL" ) },
                        keyboardOptions = KeyboardOptions.Default.copy ( keyboardType = KeyboardType.Number )
                    )

                    Spacer ( modifier = Modifier.height ( 8.dp ) )

                    OutlinedTextField (
                        value = presionS,
                        onValueChange = {
                            if ( it.isDigitsOnly() ) {
                                presionS = it
                                validateForm()
                            }
                        },
                        label = { Text ( "Presión Sistólica" ) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors ( focusedBorderColor = ContrastBLue, unfocusedBorderColor = DialogTextBlue, containerColor = Color.White ),
                        suffix = { Text ( "mm Hg" ) },
                        keyboardOptions = KeyboardOptions.Default.copy ( keyboardType = KeyboardType.Number )
                    )

                    Spacer ( modifier = Modifier.height ( 8.dp ) )

                    OutlinedTextField (
                        value = presionD,
                        onValueChange = {
                            if ( it.isDigitsOnly() ) {
                                presionD = it
                                validateForm()
                            }
                        },
                        label = { Text ( "Presión Diastólica" ) },
                        modifier = Modifier.fillMaxWidth(),
                        colors = TextFieldDefaults.outlinedTextFieldColors ( focusedBorderColor = ContrastBLue, unfocusedBorderColor = DialogTextBlue, containerColor = Color.White ),
                        suffix = { Text ( "mm Hg" ) },
                        keyboardOptions = KeyboardOptions.Default.copy ( keyboardType = KeyboardType.Number )
                    )
                }
            },
            confirmButton = {
                Button (
                    onClick = {
                        onSave ( glucosa.toDouble(), presionS.toInt(), presionD.toInt() )
                        clearForm()
                        onDismiss ()
                    },
                    colors = ButtonDefaults.buttonColors ( containerColor = PrimaryBlue, contentColor = Color.White ),
                    enabled = isFormValid
                ) {
                    Text ( "Registrar" )
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