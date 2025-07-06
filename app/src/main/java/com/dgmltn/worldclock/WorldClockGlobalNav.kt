package com.dgmltn.worldclock

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.dgmltn.worldclock.BottomTab.ClockTab
import com.dgmltn.worldclock.BottomTab.ListTab
import com.dgmltn.worldclock.BottomTab.SettingsTab
import com.dgmltn.worldclock.ui.screen.citylist.CityListScreen
import com.dgmltn.worldclock.ui.screen.clock.ClockScreen
import com.dgmltn.worldclock.ui.screen.settings.SettingsScreen
import com.dgmltn.worldclock.ui.theme.WcPreview


sealed class BottomTab(
    val labelResId: Int,
    val iconResId: Int
) {
    object ClockTab : BottomTab(R.string.Clock, R.drawable.ic_tab_clock)
    object ListTab : BottomTab(R.string.List, R.drawable.ic_tab_list)
    object SettingsTab : BottomTab(R.string.Settings, R.drawable.ic_tab_settings)
}

@Composable
fun WorldClockGlobalNav() {
    var selectedTab by remember { mutableStateOf<BottomTab>(ClockTab) }

    Scaffold(
        bottomBar = {
            BottomAppBar(
                containerColor = Color.Transparent,
            ) {
                listOf(ClockTab, ListTab, SettingsTab).forEach { tab ->
                    val isSelected = selectedTab == tab
                    NavigationBarItem(
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = Color.Transparent,
                            selectedIconColor = MaterialTheme.colorScheme.onBackground,
                            unselectedIconColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.25f),
                            selectedTextColor = MaterialTheme.colorScheme.onBackground,
                            unselectedTextColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.25f),
                        ),
                        icon = {
                            Icon(
                                painter = painterResource(tab.iconResId),
                                contentDescription = stringResource(tab.labelResId),
                                modifier = Modifier.size(40.dp),
                            )
                        },
                        label = {
                            Text(
                                text = stringResource(tab.labelResId).uppercase(),
                                style = MaterialTheme.typography.labelLarge,
                                fontWeight = FontWeight.Bold,
                            )
                        },
                        selected = isSelected,
                        onClick = { selectedTab = tab }
                    )
                }
            }
        }
    ) { contentPadding ->
        when (selectedTab) {
            ListTab -> CityListScreen(
                contentPadding = contentPadding,
            )
            SettingsTab -> SettingsScreen(
                modifier = Modifier
                    .padding(contentPadding)
            )
            else -> ClockScreen(
                modifier = Modifier
                    .padding(contentPadding)
            )
        }
    }
}

@Preview
@Composable
private fun Preview_WorldClockApp() {
    WcPreview {
        WorldClockGlobalNav()
    }
}