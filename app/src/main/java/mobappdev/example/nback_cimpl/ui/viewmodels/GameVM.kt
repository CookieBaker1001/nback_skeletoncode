package mobappdev.example.nback_cimpl.ui.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import mobappdev.example.nback_cimpl.GameApplication
import mobappdev.example.nback_cimpl.NBackHelper
import mobappdev.example.nback_cimpl.NavigationController
import mobappdev.example.nback_cimpl.SoundManager
import mobappdev.example.nback_cimpl.data.UserPreferencesRepository
import kotlin.math.absoluteValue

interface GameViewModel {
    val gameState: StateFlow<GameState>
    val score: StateFlow<Int>
    val highscore: StateFlow<Int>

    var nBack: MutableState<Int>
    var arrayLength: MutableState<Int>
    var gridSize: MutableState<Int>
    var probabillity: MutableState<Int>

    var currentStep: Int
    var isGameOver: Boolean
    var justClickedVisual: Boolean
    var justClickedAudio: Boolean

    var feedBackText: MutableState<String>

    fun setGameType(gameType: GameType)
    fun getGameType(): String
    fun startGame()

    fun checkMatchVisual()
    fun checkMatchAudio()
    fun isNotAudio(): Boolean

    fun resetGame()
    fun getEventValue(): Int
}

class GameVM(
    private val userPreferencesRepository: UserPreferencesRepository
): GameViewModel, ViewModel() {
    private val _gameState = MutableStateFlow(GameState())
    override val gameState: StateFlow<GameState>
        get() = _gameState.asStateFlow()

    private val _score = MutableStateFlow(0)
    override val score: StateFlow<Int>
        get() = _score

    private val _highscore = MutableStateFlow(0)
    override val highscore: StateFlow<Int>
        get() = _highscore

    override var nBack = mutableStateOf(2)
    override var arrayLength = mutableStateOf(10)
    override var gridSize = mutableStateOf(3)
    override var probabillity = mutableStateOf(30)

    override var currentStep: Int = 0
    override var isGameOver: Boolean = false
    override var justClickedVisual: Boolean = false
    override var justClickedAudio: Boolean = false

    override var feedBackText = mutableStateOf("")

    private var job: Job? = null
    private val eventInterval: Long = 2000L

    private val nBackHelper = NBackHelper()
    private var visualEvents = emptyArray<Int>()
    private var audioEvents = emptyArray<Int>()

    override fun setGameType(gameType: GameType) {
        _gameState.value = _gameState.value.copy(gameType = gameType)
    }

    override fun getGameType(): String {
        return _gameState.value.gameType.toString()
    }

    override fun startGame() {
        job?.cancel()
        isGameOver = false
        job = viewModelScope.launch {
            countDown()
        }
    }

    private suspend fun countDown() {
        _gameState.value = _gameState.value.copy(eventValueAudio = -1)
        feedBackText.value = "Ready"
        delay(1000)
        feedBackText.value = "Set"
        delay(1000)
        feedBackText.value = "Go!"
        delay(750)
        feedBackText.value = ""
        launchGame()
    }

    private fun launchGame() {
        job?.cancel()
        job = viewModelScope.launch {
            when (gameState.value.gameType) {
                GameType.Audio -> {
                    audioEvents = nBackHelper.generateNBackString(arrayLength.value, gridSize.value * gridSize.value, probabillity.value, nBack.value).toList().toTypedArray()
                    runAudioGame(audioEvents)
                }
                GameType.AudioVisual -> {
                    visualEvents = nBackHelper.generateNBackString(arrayLength.value, gridSize.value * gridSize.value, probabillity.value, nBack.value).toList().toTypedArray()
                    delay(1037)
                    audioEvents = nBackHelper.generateNBackString(arrayLength.value, gridSize.value * gridSize.value, probabillity.value, nBack.value).toList().toTypedArray()
                    runAudioVisualGame(visualEvents, audioEvents)
                }
                GameType.Visual -> {
                    visualEvents = nBackHelper.generateNBackString(arrayLength.value, gridSize.value * gridSize.value, probabillity.value, nBack.value).toList().toTypedArray()
                    runVisualGame(visualEvents)
                }
            }
            endGame()
        }
    }

    private suspend fun endGame() {
        delay(2000)
        feedBackText.value = "Game is over"
        delay(1500)
        NavigationController.navigate("GameOverScreen")
    }

    override fun checkMatchVisual() {
        if (_gameState.value.gameType == GameType.Audio || justClickedVisual || currentStep - nBack.value < 0) return
        val currentStepValue = _gameState.value.eventValueVisual
        val nBackPosition = visualEvents[if (currentStep - nBack.value > 0) currentStep - nBack.value else 0]

        if (currentStepValue == nBackPosition) {
            justClickedVisual = true
            scored()
        }
        else {
            if (isGameOver) return
            isGameOver = true
            job?.cancel()
            job = viewModelScope.launch {
                endGame()
            }
        }
    }

    override fun checkMatchAudio() {
        if (_gameState.value.gameType == GameType.Visual || justClickedAudio || currentStep - nBack.value < 0) return
        val currentStepValue = _gameState.value.eventValueAudio
        val nBackPosition = audioEvents[if (currentStep - nBack.value > 0) currentStep - nBack.value else 0]

        if (currentStepValue == nBackPosition){
            justClickedAudio = true
            scored()
        }
        else {
            if (isGameOver) return
            isGameOver = true
            job?.cancel()
            job = viewModelScope.launch {
                endGame()
            }
        }
    }

    override fun isNotAudio(): Boolean {
        return _gameState.value.gameType != GameType.Audio
    }

    private fun scored() {
        _score.value++
    }

    private suspend fun runAudioGame(events: Array<Int>) {
        delay(eventInterval/2)
        for (value in events) {
            _gameState.value = _gameState.value.copy(eventValueAudio = value)
            SoundManager.speakText((_gameState.value.eventValueAudio+64).toChar().toString())
            delay(eventInterval)
            currentStep++
            justClickedAudio = false
        }
    }

    private suspend fun runVisualGame(events: Array<Int>){
        delay(eventInterval/2)
        for (value in events) {
            _gameState.value = _gameState.value.copy(eventValueVisual = value)
            delay(eventInterval)
            currentStep++
            justClickedVisual = false
        }
    }

    private suspend fun runAudioVisualGame(vEvents: Array<Int>, aEvents: Array<Int>){
        delay(eventInterval/2)
        var i: Int = 0
        for (value in vEvents) {
            _gameState.value = _gameState.value.copy(eventValueVisual = value)
            _gameState.value = _gameState.value.copy(eventValueAudio = aEvents[i].absoluteValue)
            SoundManager.speakText((_gameState.value.eventValueAudio+64).toChar().toString())

            delay(eventInterval)
            i++
            currentStep++
            justClickedVisual = false
            justClickedAudio = false
        }
    }

    private fun resetValues() {
        job?.cancel()
        _score.value = 0
        currentStep = 0
        isGameOver = false
        justClickedVisual = false
        justClickedAudio = false
        feedBackText.value = ""
        _gameState.value = _gameState.value.copy(eventValueVisual = -1)
        _gameState.value = _gameState.value.copy(eventValueAudio = -1)
    }

    override fun resetGame() {
        job?.cancel()
        job = viewModelScope.launch {
            userPreferencesRepository.highscore.collect { highScore ->
                if (_score.value > highScore) {
                    userPreferencesRepository.saveHighScore(_score.value)
                }
            }
        }
        resetValues()
    }

    override fun getEventValue(): Int {
        return _gameState.value.eventValueVisual.absoluteValue
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as GameApplication)
                GameVM(application.userPreferencesRespository)
            }
        }
    }

    init {
        viewModelScope.launch {
            userPreferencesRepository.highscore.collect {
                _highscore.value = it
            }
        }
    }
}

enum class GameType{
    Audio,
    Visual,
    AudioVisual
}

data class GameState(
    val gameType: GameType = GameType.Visual,
    val eventValueVisual: Int = -1,
    val eventValueAudio: Int = -1,
)

class FakeVM: GameViewModel{
    override val gameState: StateFlow<GameState>
        get() = MutableStateFlow(GameState()).asStateFlow()
    override val score: StateFlow<Int>
        get() = MutableStateFlow(2).asStateFlow()
    override val highscore: StateFlow<Int>
        get() = MutableStateFlow(42).asStateFlow()

    override var nBack = mutableStateOf(2)
    override var arrayLength = mutableStateOf(10)
    override var gridSize = mutableStateOf(3)
    override var probabillity = mutableStateOf(30)
    override var currentStep: Int = 0
    override var isGameOver: Boolean = false
    override var justClickedVisual: Boolean = false
    override var justClickedAudio: Boolean = false

    override var feedBackText = mutableStateOf("Ready")

    override fun setGameType(gameType: GameType) {
    }

    override fun getGameType(): String {
        return ""
    }

    override fun startGame() {
    }

    override fun checkMatchVisual() {
    }

    override fun checkMatchAudio() {
    }

    override fun isNotAudio(): Boolean {
        return true
    }

    override fun resetGame() {
    }

    override fun getEventValue(): Int {
        return 0
    }
}