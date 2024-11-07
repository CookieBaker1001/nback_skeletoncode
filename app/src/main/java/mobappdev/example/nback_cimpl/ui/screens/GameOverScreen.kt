package mobappdev.example.nback_cimpl.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import mobappdev.example.nback_cimpl.NavigationController
import mobappdev.example.nback_cimpl.ui.viewmodels.GameViewModel

@Composable
fun GameOverScreen(
    vm: GameViewModel
) {
    val score by vm.score.collectAsState()
    val highscore by vm.highscore.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Spacer for centering the text in the middle
        Spacer(modifier = Modifier.weight(1f))

        // Display Score and High Score
        Text(
            text = "Score: $score",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )
        Text(
            text = "High Score: $highscore",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        // Spacer for centering the text in the middle
        Spacer(modifier = Modifier.weight(1f))

        // Button to return to Home
        Button(
            onClick = {
                vm.resetGame()
                NavigationController.navigate("HomeScreen")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Return to Home")
        }
    }
}
