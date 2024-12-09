package com.example.oceanmed.boardingViews

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.oceanmed.dataStore.StoreBoarding
import com.example.oceanmed.ui.theme.PrimaryBlue

@Composable
fun EnterButton ( currentPage: Int, navController: NavController, store: StoreBoarding ) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding ( bottom = 20.dp ),
        horizontalArrangement = if ( currentPage != 3 ) Arrangement.SpaceBetween else Arrangement.Center
    ) {
        if ( currentPage == 3 ) {
            Button (
                onClick = { navController.navigate( "registry" ) { popUpTo( 0 ) } },
                colors = ButtonDefaults.buttonColors ( containerColor = PrimaryBlue, contentColor = Color.White ),
                modifier = Modifier.scale ( 1.5f )
            ) {
                Text (
                    text = "Entrar",
                    modifier = Modifier.padding ( horizontal = 10.dp )
                )
            }
        }
    }
}