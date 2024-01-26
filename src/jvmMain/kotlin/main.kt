
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.example.compose.App
import data.manager.PreferencesManager
import data.repository.ColorRepository
import data.repository.RadioRepository
import data.repository.SettingsTabsRepository
import data.repository.TextRepository
import data.repository.ThemeModeRepository
import data.repository.VersionRepository
import data.repository.WindowsPlacementRepository
import moe.tlaster.precompose.navigation.rememberNavigator
import navigation.NavGraph
import ui.components.KofiButton
import ui.components.TopBarElements
import ui.components.loadAppIcon
import ui.dialogs.UpdaterDialog
import ui.dialogs.VlcInstallerDialog
import utils.Localization
import utils.SnackBarDisplayer
import utils.isVLCInstalled
import utils.stringResource
import viewmodel.MainViewModel
import viewmodel.RadioViewModel
import java.awt.Dimension
import java.util.Locale

fun main() = application {
    val appIcon = loadAppIcon()
    val preferenceManager = PreferencesManager
    val vm = MainViewModel(
        localizationRepository = Localization,
        preferencesManager = preferenceManager,
        colorRepository = ColorRepository,
        versionReposition = VersionRepository,
        windowsPlacementRepository = WindowsPlacementRepository,
        themeModeRepository = ThemeModeRepository,
        textRepository = TextRepository,
        settingsTabsRepository = SettingsTabsRepository,
    )
    Locale.setDefault(Locale(vm.getCurrentLanguage()))
    Window(
        onCloseRequest = ::exitApplication,
        title = stringResource("app_name"),
        state = vm.windowsState,
        icon = appIcon,

    ) {
        window.minimumSize = Dimension(960,720)
        App(vm) {
            val snackbarHostState = remember { SnackbarHostState() }
            val navigator = rememberNavigator()
            SnackBarDisplayer(vm, snackbarHostState)
            Scaffold(
                topBar = { TopBarElements(vm, navigator).TopBar() },
                floatingActionButton = { KofiButton(vm) },
                snackbarHost = { SnackbarHost(snackbarHostState) },
            ) { paddingValues ->
                Surface(
                    Modifier.padding(paddingValues).padding(16.dp).fillMaxSize()
                ) {
                    VlcInstallerDialog()
                    if (!isVLCInstalled()) return@Surface
                    NavGraph(vm, navigator, RadioViewModel(vm, RadioRepository, preferenceManager) )
                    UpdaterDialog(vm)
                }
            }
        }
    }
}
