package com.example.oceanmed.views

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.oceanmed.R
import com.example.oceanmed.boardingViews.BoardingPager
import com.example.oceanmed.data.PageData
import com.example.oceanmed.dataStore.StoreBoarding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.rememberPagerState
import java.util.ArrayList

@OptIn(ExperimentalPagerApi::class)
@Composable
fun MainViewBoarding ( navController: NavController, store: StoreBoarding ) {
    val items = ArrayList<PageData>()

    items.add (
        PageData ( R.raw.welcomepage, "¡Bienvenido a OceanMed", "Descubre nuestras herramientas para un mejor cuidado de la salud" )
    )

    items.add (
        PageData ( R.raw.patientregistrypage, "Registro de Pacientes", "Organiza y accede a toda la información de manera sencilla" )
    )

    items.add (
        PageData ( R.raw.dailymonitoringpage, "Monitoreo Diario", "Lleva un registro diario de la salud de tus pacientes" )
    )

    items.add (
        PageData ( R.raw.startpage, "¡Todo en un solo lugar!", "¿Listo para usar OceanMed? Haz clic en el botón de abajo para ingresar a la aplicación" )
    )

    val pagerState = rememberPagerState (
        pageCount = items.size,
        initialOffscreenLimit = 2,
        infiniteLoop = false,
        initialPage = 0
    )

    BoardingPager (
        item = items,
        pagerState = pagerState,
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background ( Color.White ),
        navController,
        store
    )
}