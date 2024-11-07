package mobappdev.example.nback_cimpl

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import mobappdev.example.nback_cimpl.ui.screens.*
import mobappdev.example.nback_cimpl.ui.theme.NBack_CImplTheme
import mobappdev.example.nback_cimpl.ui.viewmodels.GameVM

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        SoundManager.initialize(this)
        SoundManager.loadSound(this, "1", R.raw.grillmaster)
        SoundManager.loadSound(this, "2", R.raw.perfektkorv)
        SoundManager.loadSound(this, "3", R.raw.hallomi)
        SoundManager.loadSound(this, "4", R.raw.nuskavigrilla)
        SoundManager.loadSound(this, "5", R.raw.majs)
        SoundManager.loadSound(this, "6", R.raw.hungrig)
        SoundManager.loadSound(this, "7", R.raw.denniskorv)
        SoundManager.loadSound(this, "8", R.raw.huraterdu)
        SoundManager.loadSound(this, "9", R.raw.brandkorv)

        setContent {
            NBack_CImplTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val gameViewModel: GameVM = viewModel(
                        factory = GameVM.Factory
                    )
                    val navController = rememberNavController()
                    NavigationController.setNavController(navController)

                    NavHost(navController = navController, startDestination = "HomeScreen") {
                        composable("HomeScreen") {
                            HomeScreen(vm = gameViewModel)
                        }
                        composable("SettingsScreen") {
                            SettingsScreen(vm = gameViewModel)
                        }
                        composable("GameScreen") {
                            GameScreen(vm = gameViewModel)
                        }
                        composable("GameOverScreen") {
                            GameOverScreen(vm = gameViewModel)
                        }
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        SoundManager.release()
    }
}