package mobappdev.example.nback_cimpl

import android.content.Context
import android.media.AudioAttributes
import android.media.SoundPool

object SoundManager {

    private lateinit var soundPool: SoundPool
    private val soundMap = mutableMapOf<String, Int>()

    fun initialize(context: Context) {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(2)
            .setAudioAttributes(audioAttributes)
            .build()
    }

    fun loadSound(context: Context, soundName: String, resId: Int) {
        val soundId = soundPool.load(context, resId, 1)
        soundMap[soundName] = soundId
    }

    fun playSound(soundName: String) {
        soundMap[soundName]?.let {
            soundPool.play(it, 1f, 1f, 1, 0, 1.0f)
        }
    }

    fun release() {
        soundPool.release()
    }
}