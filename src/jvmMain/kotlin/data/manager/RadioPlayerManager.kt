package data.manager

import com.sun.jna.NativeLibrary
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import uk.co.caprica.vlcj.binding.support.runtime.RuntimeUtil
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent
import utils.OsDetector
import utils.getRessourcePath
import utils.stringResource
import viewmodel.MainViewModel

class RadioPlayerManager(
    val mainViewModel: MainViewModel
) {

    val resourcesDir = getRessourcePath(false)

    private val audioPlayerComponent = AudioPlayerComponent()
    private var _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    // Liste des flux médias (URLs).
    private val mediaList = mutableListOf<String>()

    init {
        if (OsDetector.isWindows()) NativeLibrary.addSearchPath(RuntimeUtil.getLibVlcLibraryName(), "$resourcesDir/vlc")
        setupMediaPlayerEvents()
    }

    fun clearMediaList() {
        mediaList.clear()
    }

    private fun setupMediaPlayerEvents() {
        audioPlayerComponent.mediaPlayer().events()
            .addMediaPlayerEventListener(object : MediaPlayerEventAdapter() {
                override fun playing(mediaPlayer: MediaPlayer) {
                    _isPlaying.value = true
                }

                override fun stopped(mediaPlayer: MediaPlayer) {
                    _isPlaying.value = false
                }

                override fun buffering(mediaPlayer: MediaPlayer, newCache: Float) {
                    mainViewModel.setLoading(newCache != 100f)
                }

                override fun error(mediaPlayer: MediaPlayer) {
                    // Cette méthode est appelée lorsqu'une erreur se produit dans VLC.
                    mainViewModel.setLoading(false)
                    mainViewModel.showSnackbar(stringResource("error_reading_stream"))
                    _isPlaying.value = false
                }
            })
    }

    fun play(streamUrl: String) {
        try {
            audioPlayerComponent.mediaPlayer().media().play(streamUrl)
            _isPlaying.value = true
        } catch (e: Exception) {
            println("Erreur lors de la lecture: ${e.localizedMessage}")
        }
    }

    fun stop() {
        try {
            audioPlayerComponent.mediaPlayer().controls().stop()
            _isPlaying.value = false
        } catch (e: Exception) {
            println("Erreur lors de l'arrêt: ${e.localizedMessage}")
        }
    }

    fun togglePlayStop(streamUrl: String) {
        if (_isPlaying.value) {
            stop()
        } else {
            play(streamUrl)
        }
    }

    fun setVolume(volume: Int) {
        val adjustedVolume = volume.coerceIn(0, 100)
        audioPlayerComponent.mediaPlayer().audio().setVolume(adjustedVolume)
    }

    fun addMedia(url: String) {
        mediaList.add(url)
    }

}
