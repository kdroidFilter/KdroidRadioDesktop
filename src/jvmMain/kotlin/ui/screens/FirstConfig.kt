package ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import enums.NavigationDestination
import moe.tlaster.precompose.navigation.NavOptions
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import ui.components.InfoContainer
import ui.components.PointerModifier
import utils.stringResource
import viewmodel.MainViewModel

@OptIn(ExperimentalResourceApi::class)
@Composable
fun FirstConfig(vm: MainViewModel, navigator: Navigator) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center) {
        InfoContainer(stringResource("welcome_message"))
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MainAppSettings(vm)
            Spacer(Modifier.height(8.dp))
            Button(
                modifier = PointerModifier,
                onClick = {
                    vm.configDone()
                    navigator.navigate(
                        NavigationDestination.Home.route,
                        NavOptions(launchSingleTop = true)
                    )
                }
            ) {
                Text(stringResource("end_configuration"))
            }
        }
    }
}