package mobappdev.example.nback_cimpl.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import mobappdev.example.nback_cimpl.ui.viewmodels.FakeVM
import mobappdev.example.nback_cimpl.ui.viewmodels.GameViewModel

@Composable
fun GameScreen (
    vm: GameViewModel
) {
    val gameState by vm.gameState.collectAsState()
    val score by vm.score.collectAsState()
    val feedBackText by vm.feedBackText

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF8855BB),
                        Color(0xFF22AA88)
                    )
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Gamemode: ${gameState.gameType}",
            fontSize = 24.sp,
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = "N = ${vm.nBack.value}, score = $score",
            fontSize = 20.sp,
            style = MaterialTheme.typography.headlineLarge
        )
        Text(
            text = feedBackText,
            fontSize = 48.sp,
            style = MaterialTheme.typography.headlineLarge
        )

        val size = LocalConfiguration.current.screenWidthDp.dp / (vm.gridSize.value) - 28.dp
        LazyVerticalGrid(
            columns = GridCells.Fixed(vm.gridSize.value),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(vm.gridSize.value * vm.gridSize.value) { index ->
                val id = index + 1
                Box(
                    modifier = Modifier
                        .size(size)
                        .background(
                            if (vm.isNotAudio() && id == vm.gameState.value.eventValueVisual) Color(0xFF2AF563)
                            else Color(0xFF7733AA)
                        )
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(
                onClick = { vm.checkMatchAudio() },
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
                    .aspectRatio(1f / 1f)
            ) {
                Text(
                    text = "Audio",
                    color = Color.Black,
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
            Button(
                onClick = { vm.checkMatchVisual() },
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
                    .aspectRatio(1f / 1f)
            ) {
                Text(
                    text = "Visual",
                    color = Color.Black,
                    fontSize = 32.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun GameScreenPreview() {
    Surface(){
        GameScreen(FakeVM())
    }
}