package mobappdev.example.nback_cimpl.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import mobappdev.example.nback_cimpl.NavigationController
import mobappdev.example.nback_cimpl.ui.viewmodels.GameViewModel

@Composable
fun SettingsScreen (
    vm: GameViewModel
){
    var nValue by vm.nBack
    var size by vm.arrayLength
    var gridSize by vm.gridSize

    Scaffold(
        topBar = {
            Text(
                text = "Settings",
                textAlign = TextAlign.Center
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    NavigationController.navigate("HomeScreen")
                },
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .padding(4.dp)
            ) {
                Text(
                    text = "Main menu",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                )
            }
            Text(text = "n-value: $nValue")
            Slider(
                value = nValue.toFloat(),
                onValueChange = { nValue = it.toInt() },
                valueRange = 1f..10f,
                steps = 8
            )

            Text(text = "Size: $size")
            Slider(
                value = size.toFloat(),
                onValueChange = { size = it.toInt() },
                valueRange = 10f..50f,
                steps = 39
            )

            Text(text = "Grid Size: $gridSize")
            Slider(
                value = gridSize.toFloat(),
                onValueChange = { gridSize = it.toInt() },
                valueRange = 3f..5f,
                steps = 1
            )
        }
    }
}