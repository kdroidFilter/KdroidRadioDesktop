package data.manager

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import uk.co.caprica.vlcj.player.base.MediaPlayer
import uk.co.caprica.vlcj.player.base.MediaPlayerEventAdapter
import uk.co.caprica.vlcj.player.component.AudioPlayerComponent
import utils.stringResource
import viewmodel.MainViewModel

class RadioPlayerManager(
    val mainViewModel: MainViewModel
) {

    private val audioPlayerComponent = AudioPlayerComponent()
    private var _isPlaying = MutableStateFlow(false)
    val isPlaying = _isPlaying.asStateFlow()

    // Liste des flux médias (URLs).
    private val mediaList = mutableListOf<String>()


    init {
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
        synchronized(this) {
            try {
                audioPlayerComponent.mediaPlayer().media().play(streamUrl)
                _isPlaying.value = true
            } catch (e: Exception) {
                println("Erreur lors de la lecture: ${e.localizedMessage}")
            }
        }
    }

    fun stop() {
        synchronized(this) {
            try {
                audioPlayerComponent.mediaPlayer().controls().stop()
                _isPlaying.value = false
            } catch (e: Exception) {
                println("Erreur lors de l'arrêt: ${e.localizedMessage}")
            }
        }
    }

    fun togglePlayStop(streamUrl: String) {
        synchronized(this) {
            if (_isPlaying.value) {
                stop()
            } else {
                play(streamUrl)
            }
        }
    }

    fun setVolume(volume: Int) {
        synchronized(this) {
            val adjustedVolume = volume.coerceIn(0, 100)
            audioPlayerComponent.mediaPlayer().audio().setVolume(adjustedVolume)
        }
    }

    private val _isMuted = MutableStateFlow(audioPlayerComponent.mediaPlayer().audio().isMute)
    val isMuted = _isMuted.asStateFlow()

    fun toggleMute() {
        synchronized(this) {
            val mediaPlayer = audioPlayerComponent.mediaPlayer()
            mediaPlayer.audio().mute()
            _isMuted.value = !mediaPlayer.audio().isMute
        }
    }


    fun addMedia(url: String) {
        synchronized(this) {
            mediaList.add(url)
        }
    }


}
