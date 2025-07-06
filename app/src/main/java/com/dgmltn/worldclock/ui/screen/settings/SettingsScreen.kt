package com.dgmltn.worldclock.ui.screen.settings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("User: John Doe", fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(16.dp))
        Text("Clock Type")
        // Dropdown or radio buttons
        Text("Date Format")
        // Dropdown
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("24 Hour Time")
            Spacer(Modifier.weight(1f))
            Switch(checked = true, onCheckedChange = { /* toggle */ })
        }
        Spacer(Modifier.height(32.dp))
        Button(onClick = { /* redeem coupon */ }) {
            Text("Remove Ads – Special Offer!")
        }
    }
}