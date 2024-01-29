package ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircle
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.StopCircle
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import moe.tlaster.precompose.navigation.Navigator
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import ui.components.IconPointerModifier
import ui.components.PointerModifier
import viewmodel.MainViewModel
import viewmodel.RadioViewModel


@OptIn(ExperimentalResourceApi::class)
@Composable
fun Home(mainViewModel: MainViewModel, navigator: Navigator, vm: RadioViewModel) {
    Column(Modifier.padding(8.dp)) {
        val radioItems = vm.getRadioItems()
        PermanentNavigationDrawer(
            modifier = Modifier,
            drawerContent = {
                LazyColumn(
                    modifier = Modifier.width(250.dp),
                    verticalArrangement = Arrangement.spacedBy(2.dp)
                ) {
                    items(radioItems) { item ->
                        NavigationDrawerItem(
                            label = {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Image(
                                        painter = painterResource(item.icon),
                                        contentDescription = item.stations[0].name,
                                        modifier = Modifier.size(40.dp).clip(shape = CircleShape)
                                    )
                                    Text(item.stations[0].name)
                                }
                            },
                            onClick = {
                                vm.setSelected(item)
                            },
                            selected = vm.isSelected.value == item,
                            modifier = PointerModifier,
                        )
                    }
                }
            }
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ActualRadioElement(vm)
                Divider()
                PlayerElement(mainViewModel, vm)
            }
        }

    }
}

@OptIn(ExperimentalResourceApi::class)
@Composable
fun ActualRadioElement(vm: RadioViewModel) {
    Column(
        modifier = Modifier.fillMaxHeight(0.8f).padding(16.dp),
        verticalArrangement = Arrangement.SpaceEvenly,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            vm.getActualRadio(),
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )
        Box(Modifier.height(35.dp)) {
            if (vm.hasSubItems()) {
                var isExpanded by remember { mutableStateOf(false) }
                Box {
                    OutlinedButton(
                        modifier = PointerModifier,
                        onClick = {
                            isExpanded = !isExpanded
                        }) {
                        Text(vm.isSubItemSelected.value.name)
                    }
                    if (isExpanded) {
                        DropdownMenu(
                            expanded = isExpanded,
                            onDismissRequest = {
                                isExpanded = false
                            }
                        ) {
                            vm.getSubItems().forEach { subItem ->
                                DropdownMenuItem(
                                    modifier = PointerModifier,
                                    onClick = {
                                        vm.setSubItemSelected(subItem)
                                        isExpanded = false
                                    },
                                    text = {
                                        Text(
                                            subItem.name, textAlign = TextAlign.Center,
                                            fontWeight = if (subItem == vm.isSubItemSelected.value) FontWeight.Bold else FontWeight.Normal,
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                    }
                                )
                            }
                        }
                    }
                }
            }
        }
        Image(
            painterResource(vm.getRadioIcon()),
            null,
            Modifier.size(300.dp).clip(shape = CircleShape)
        )
    }
}

@Composable
fun PlayerElement(mainViewModel: MainViewModel, vm: RadioViewModel) {
    Column(
        modifier = Modifier.fillMaxHeight(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Row(
                modifier = Modifier.fillMaxHeight(),
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Default.SkipPrevious,
                    "Previous",
                    IconPointerModifier(mainViewModel).size(70.dp).clip(CircleShape).clickable {
                        vm.previous()
                    }
                )
                val isPlaying = vm.isPlaying.collectAsState()
                Icon(
                    (if (isPlaying.value) Icons.Default.StopCircle else Icons.Default.PlayCircle),
                    "Play",
                    IconPointerModifier(mainViewModel).size(100.dp).clip(CircleShape).clickable(
                        onClick = {
                            vm.togglePlayStop()
                        }
                    )
                )
                Icon(
                    Icons.Default.SkipNext,
                    "Next",
                    IconPointerModifier(mainViewModel).size(70.dp).clip(CircleShape).clickable {
                        vm.next()
                    })
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxHeight()
            ) {
                Slider(
                    value = vm.volume.collectAsState().value.toFloat(),
                    onValueChange = {
                        vm.setVolume(it.toInt())
                    },
                    valueRange = 0f..100f,
                    modifier = PointerModifier.width(250.dp)
                )
                val isMute = vm.isMuted.collectAsState()
                Icon(
                    (if (isMute.value) Icons.Default.VolumeOff else Icons.Default.VolumeUp),
                    "Mute",
                    IconPointerModifier(mainViewModel).size(35.dp).clip(CircleShape).clickable {
                        vm.toggleMute()
                    }
                )
            }
        }
    }
}