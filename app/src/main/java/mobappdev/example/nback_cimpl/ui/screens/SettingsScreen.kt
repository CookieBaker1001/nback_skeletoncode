package mobappdev.example.nback_cimpl.ui.screens

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mobappdev.example.nback_cimpl.NavigationController
import mobappdev.example.nback_cimpl.ui.viewmodels.FakeVM
import mobappdev.example.nback_cimpl.ui.viewmodels.GameViewModel

@Composable
fun SettingsScreen (
    vm: GameViewModel
){
    var nValue by vm.nBack
    var size by vm.arrayLength
    var gridSize by vm.gridSize

    Scaffold(
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF8855BB),
                            Color(0xFF22AA88)
                        )
                    )
                ),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    NavigationController.navigate("HomeScreen")
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = "Main menu",
                    color = Color.Black,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(18.dp)
                    .background(color = Color(0x88AA66EE),
                        shape = RoundedCornerShape(8.dp))
                    .padding(16.dp)
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(text = "n-value: $nValue")
                    Slider(
                        value = nValue.toFloat(),
                        onValueChange = { nValue = it.toInt() },
                        valueRange = 1f..10f,
                        steps = 8,
                        colors = SliderDefaults.colors(
                            activeTrackColor = Color(0xFF22AA88),
                            inactiveTrackColor = Color(0xFF22AA88),
                        )
                    )
                    Text(text = "Size: $size")
                    Slider(
                        value = size.toFloat(),
                        onValueChange = { size = it.toInt() },
                        valueRange = 10f..50f,
                        steps = 39,
                        colors = SliderDefaults.colors(
                            activeTrackColor = Color(0xFF22AA88),
                            inactiveTrackColor = Color(0xFF22AA88),
                        )
                    )
                    Text(text = "Grid Size: $gridSize")
                    Slider(
                        value = gridSize.toFloat(),
                        onValueChange = { gridSize = it.toInt() },
                        valueRange = 3f..5f,
                        steps = 1,
                        colors = SliderDefaults.colors(
                            activeTrackColor = Color(0xFF22AA88),
                            inactiveTrackColor = Color(0xFF22AA88),
                        )
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    Surface(){
        SettingsScreen(FakeVM())
    }
}