package mobappdev.example.nback_cimpl.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
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
    val gameState by vm.gameState.collectAsState()
    val scope = rememberCoroutineScope()

    val nBack by vm.nBack

    Scaffold {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(
                onClick = {
                    NavigationController.navigate("SettingsScreen")
                },
                shape = RoundedCornerShape(4.dp),
                modifier = Modifier
                    .padding(4.dp)
            ) {
                Text(
                    text = "Settings",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                )
            }
            Text(
                modifier = Modifier.padding(8.dp),
                text = "N = $nBack",
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
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Game mode: ${gameState.gameType}",
                        textAlign = TextAlign.Center
                    )

                    Button(onClick = {
                        scope.launch {
                        }
                        NavigationController.navigate("GameScreen")
                        vm.startGame()
                    }) {
                        Text(text = "Start game")
                    }
                }
            }
            Text(
                modifier = Modifier.padding(16.dp),
                text = "Start Game".uppercase(),
                style = MaterialTheme.typography.displaySmall
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(onClick = {
                    vm.setGameType(GameType.Audio)
                }) {
                    Icon(
                        painter = painterResource(id = R.drawable.sound_on),
                        contentDescription = "Sound",
                        modifier = Modifier
                            .height(32.dp)
                            .aspectRatio(3f / 2f)
                    )
                }

                Button(
                    onClick = {
                        vm.setGameType(GameType.AudioVisual)
                    },
                    modifier = Modifier.padding(3.dp)) {
                    Icon(
                        painter = painterResource(id = R.drawable.sound_on),
                        contentDescription = "Visual+Audio",
                        modifier = Modifier
                            .height(32.dp)
                            .aspectRatio(3f / 2f)
                    )
                    Icon(
                        painter = painterResource(id = R.drawable.visual),
                        contentDescription = "Visual+Audio",
                        modifier = Modifier
                            .height(32.dp)
                            .aspectRatio(3f / 2f)
                    )
                }

                Button(
                    onClick = {
                        vm.setGameType(GameType.Visual)
                    }) {
                    Icon(
                        painter = painterResource(id = R.drawable.visual),
                        contentDescription = "Visual",
                        modifier = Modifier
                            .height(32.dp)
                            .aspectRatio(3f / 2f)
                    )
                }
            }
        }
    }
}

/*@Preview
@Composable
fun HomeScreenPreview() {
    // Since I am injecting a VM into my homescreen that depends on Application context, the preview doesn't work.
    Surface(){
        HomeScreen(FakeVM())
    }
}*/