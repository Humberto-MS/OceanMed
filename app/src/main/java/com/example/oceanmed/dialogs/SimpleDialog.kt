package com.example.oceanmed.dialogs

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import com.example.oceanmed.ui.theme.ContrastBLue
import com.example.oceanmed.ui.theme.DialogTextBlue
import com.example.oceanmed.ui.theme.PrimaryBlue
import com.example.oceanmed.ui.theme.SecondaryBlue

@Composable
fun SimpleDialog (
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onSave: () -> Unit,
    dialogTitle: String,
    dialogText: String,
    confirmButtonText: String
) {
    if ( showDialog ) {
        AlertDialog (
            onDismissRequest = onDismiss,
            title = { Text ( dialogTitle, style = TextStyle ( ContrastBLue ), fontSize = 24.sp ) },
            text = { Text ( dialogText, style = TextStyle ( DialogTextBlue ) ) },
            confirmButton = {
                Button (
                    onClick = {
                        onSave ()
                        onDismiss ()
                    },
                    colors = ButtonDefaults.buttonColors ( containerColor = PrimaryBlue, contentColor = Color.White )
                ) {
                    Text ( confirmButtonText )
                }
            },
            dismissButton = {
                Button (
                    onClick = {
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