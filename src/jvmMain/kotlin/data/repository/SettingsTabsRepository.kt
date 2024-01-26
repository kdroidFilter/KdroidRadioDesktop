package data.repository

import utils.stringResource

object SettingsTabsRepository {
    fun getTabs(): List<String> {
        return listOf((stringResource("app_settings")), (stringResource("behavior_settings")))
    }
}