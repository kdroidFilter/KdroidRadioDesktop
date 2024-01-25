package data.model

import enums.RadioCategoryType

data class RadioItem(
    val name: String? = null,
    val streamUrl: String? = null,
    var icon: String,
    val categoryType: RadioCategoryType = RadioCategoryType.MUSIC,
    val stations: List<RadioSubItem> = listOf(RadioSubItem(name!!, streamUrl!!))
)

data class RadioSubItem(
    val name: String,
    val streamUrl: String
)