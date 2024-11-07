package mobappdev.example.nback_cimpl.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import mobappdev.example.nback_cimpl.NavigationController
import mobappdev.example.nback_cimpl.R
import mobappdev.example.nback_cimpl.ui.viewmodels.FakeVM
import mobappdev.example.nback_cimpl.ui.viewmodels.GameType
import mobappdev.example.nback_cimpl.ui.viewmodels.GameViewModel

@Composable
fun HomeScreen(
    vm: GameViewModel
) {
    val highscore by vm.highscore.collectAsState()
    val nBack by vm.nBack

    Scaffold () {
        Column(
            Modifier
                .fillMaxSize()
                .padding(it)
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF8855BB),
                            Color(0xFF22AA88)
                        )
                    )
                ),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    NavigationController.navigate("SettingsScreen")
                },
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .padding(4.dp)
            ) {
                Text(
                    text = "Settings",
                    color = Color.Black,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                )
            }
            Text(
                modifier = Modifier.padding(8.dp),
                text = "N-value = $nBack",
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                modifier = Modifier.padding(16.dp),
                text = "High-Score = $highscore",
                style = MaterialTheme.typography.headlineLarge
            )
            Box(
                modifier = Modifier.weight(1f),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = {
                        vm.setGameType(GameType.Audio)
                        NavigationController.navigate("GameScreen")
                        vm.startGame()
                    },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                    ) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = "Audio",
                            color = Color.Black,
                            style = MaterialTheme.typography.displaySmall,
                            fontSize = 20.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            painter = painterResource(id = R.drawable.sound_on),
                            contentDescription = "Sound",
                            tint = Color.Black,
                            modifier = Modifier
                                .height(32.dp)
                                .aspectRatio(3f / 2f)
                        )
                    }
                    Spacer(modifier = Modifier.padding(6.dp))
                    Button(
                        onClick = {
                            vm.setGameType(GameType.AudioVisual)
                            NavigationController.navigate("GameScreen")
                            vm.startGame()
                        },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                    ) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = "Audio + Visual",
                            color = Color.Black,
                            style = MaterialTheme.typography.displaySmall,
                            fontSize = 20.sp
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.sound_on),
                            contentDescription = "Visual+Audio",
                            tint = Color.Black,
                            modifier = Modifier
                                .height(32.dp)
                                .aspectRatio(3f / 2f)
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.visual),
                            contentDescription = "Visual+Audio",
                            tint = Color.Black,
                            modifier = Modifier
                                .height(32.dp)
                                .aspectRatio(3f / 2f)
                        )
                    }
                    Spacer(modifier = Modifier.padding(6.dp))
                    Button(
                        onClick = {
                            vm.setGameType(GameType.Visual)
                            NavigationController.navigate("GameScreen")
                            vm.startGame()
                        },
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                    ) {
                        Text(
                            modifier = Modifier.padding(16.dp),
                            text = "Visual",
                            color = Color.Black,
                            style = MaterialTheme.typography.displaySmall,
                            fontSize = 20.sp
                        )
                        Icon(
                            painter = painterResource(id = R.drawable.visual),
                            contentDescription = "Visual",
                            tint = Color.Black,
                            modifier = Modifier
                                .height(32.dp)
                                .aspectRatio(3f / 2f)
                        )
                    }
                }
            }
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Labb B.1",
                style = MaterialTheme.typography.displaySmall,
                fontSize = 28.sp
            )
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Simon Springer HT2024",
                style = MaterialTheme.typography.displaySmall,
                fontSize = 20.sp
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    Surface(){
        HomeScreen(FakeVM())
    }
}