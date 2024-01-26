package viewmodel

import androidx.compose.runtime.mutableStateOf
import data.manager.PreferencesManager
import data.manager.RadioPlayerManager
import data.model.RadioItem
import data.model.RadioSubItem
import data.repository.RadioRepository
import enums.RadioCategoryType
import moe.tlaster.precompose.viewmodel.ViewModel

class RadioViewModel(
    mainViewModel: MainViewModel,
    private val radioRepository: RadioRepository,
    private val preferencesManager: PreferencesManager
) : ViewModel() {

    private val radioPlayerManager = RadioPlayerManager(mainViewModel)

    private var _isSelected = mutableStateOf(getRadioItems().first())
    val isSelected = _isSelected


    private var _isSubItemSelected = mutableStateOf(_isSelected.value.stations.first())
    var isSubItemSelected = _isSubItemSelected

    private var _volume = mutableStateOf(50)
    val volume = _volume

    fun getRadioItems(): List<RadioItem> {
        return if (preferencesManager.showNewsStations()) radioRepository.getRadioItems() else {
            radioRepository.getRadioItems().filter { it.categoryType != RadioCategoryType.NEWS }
        }
    }

    fun getSubItems() = _isSelected.value.stations

    fun setSelected(item: RadioItem) {
        _isSelected.value = item
        setSubItemSelected(item.stations.first())
        updateMediaList(item.stations)
    }

    fun setSubItemSelected(item: RadioSubItem) {
        _isSubItemSelected.value = item
        radioPlayerManager.stop()
        radioPlayerManager.play(item.streamUrl)
    }

    private fun updateMediaList(stations: List<RadioSubItem>) {
        radioPlayerManager.clearMediaList()
        stations.forEach { station ->
            radioPlayerManager.addMedia(station.streamUrl)
        }
    }

    fun getRadioIcon() = _isSelected.value.icon
    fun hasSubItems() = _isSelected.value.stations.size > 1
    fun getActualRadio() = _isSubItemSelected.value.name

    private fun getActualIndex() = getRadioItems().indexOf(isSelected.value)

    private val nextStation: RadioItem
        get() {
            val currentIndex = getActualIndex()
            return if (currentIndex < getRadioItems().size - 1) getRadioItems()[currentIndex + 1] else getRadioItems().first()
        }

    private val previousStation: RadioItem
        get() {
            val currentIndex = getActualIndex()
            return if (currentIndex > 0) getRadioItems()[currentIndex - 1] else getRadioItems().last()
        }

    fun next() {
        setSelected(nextStation)
    }

    fun previous() {
        setSelected(previousStation)
    }


    val isPlaying = radioPlayerManager.isPlaying


    fun togglePlayStop() {
        radioPlayerManager.togglePlayStop(isSubItemSelected.value.streamUrl)
    }

    fun setVolume(volume: Int) {
        _volume.value = volume
        radioPlayerManager.setVolume(volume)
    }


    init {
        updateMediaList(getRadioItems().first().stations)
    }
}
