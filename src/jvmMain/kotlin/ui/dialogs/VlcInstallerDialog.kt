package ui.dialogs

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.components.PointerModifier
import utils.isVLCInstalled
import utils.openUrlInBrowser
import utils.stringResource
import kotlin.system.exitProcess

@Composable
fun VlcInstallerDialog() {
    val isVLCNotInstalled = !isVLCInstalled()
    if (isVLCNotInstalled) {
        AlertDialog(
            onDismissRequest = { exitProcess(0) },
            title = { Text(stringResource("vlc_not_found")) },
            text = {
                Text(stringResource("vlc_not_found_instruction"))
            },
            confirmButton = {
                Spacer(Modifier.width(8.dp))
                Button(
                    modifier = PointerModifier,
                    onClick = { openUrlInBrowser("https://www.videolan.org/vlc/"); exitProcess(0)  }) {
                    Text(stringResource("download_vlc"))
                }
            },
            dismissButton = {
                OutlinedButton(modifier = PointerModifier, onClick = { exitProcess(0)  }) {
                    Text(stringResource("exit"))
                }
            }
        )
    }
}