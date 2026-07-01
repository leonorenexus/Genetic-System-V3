package com.genetic.system.v3.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController) {
    var isChaosMode by remember { mutableStateOf(false) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("⚡ GENETIC SYSTEM V3", color = Color.White, fontWeight = FontWeight.Bold, fontSize = 16.sp) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1A1A2E)),
                actions = {
                    IconButton(onClick = { isChaosMode = !isChaosMode }) {
                        Icon(if (isChaosMode) Icons.Default.LockOpen else Icons.Default.Lock, 
                             contentDescription = null, 
                             tint = if (isChaosMode) Color.Red else Color.Green)
                    }
                }
            )
        },
        containerColor = Color(0xFF0A0A0F)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            StatusHeader(isChaosMode)
            Spacer(modifier = Modifier.height(24.dp))
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(menuItems) { item ->
                    MenuCard(item = item, onClick = {
                        if (isChaosMode || item.requiresChaos) {
                            navController.navigate(item.route)
                        }
                    }, isChaosMode = isChaosMode)
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            DeviceStatus()
        }
    }
}

@Composable
fun StatusHeader(isChaosMode: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = if (isChaosMode) Color(0xFF2D1B1B) else Color(0xFF1A1A2E)),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(text = if (isChaosMode) "🔓 CHAOS MODE" else "🛡️ SYSTEM READY",
                     color = if (isChaosMode) Color.Red else Color.Green,
                     fontWeight = FontWeight.Bold, fontSize = 14.sp)
                Text(text = if (isChaosMode) "All restrictions disabled" else "Running in safe mode",
                     color = Color.Gray, fontSize = 12.sp)
            }
            Box(modifier = Modifier.size(48.dp).clip(RoundedCornerShape(24.dp))
                .background(if (isChaosMode) Color.Red.copy(alpha = 0.2f) else Color.Green.copy(alpha = 0.2f)),
                contentAlignment = Alignment.Center) {
                Text(text = if (isChaosMode) "💀" else "✓", fontSize = 24.sp)
            }
        }
    }
}

@Composable
fun MenuCard(item: MenuItem, onClick: () -> Unit, isChaosMode: Boolean) {
    Card(
        modifier = Modifier.fillMaxWidth().height(120.dp),
        colors = CardDefaults.cardColors(containerColor = if (item.dangerLevel > 2) Color(0xFF1A0A0A) else Color(0xFF12121E)),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick,
        elevation = CardDefaults.cardElevation(defaultElevation = if (item.dangerLevel > 2) 8.dp else 4.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(imageVector = item.icon, contentDescription = null,
                 tint = if (item.dangerLevel > 2 && !isChaosMode) Color.Gray else item.color,
                 modifier = Modifier.size(32.dp))
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = item.title, color = if (item.dangerLevel > 2 && !isChaosMode) Color.Gray else Color.White,
                 fontWeight = FontWeight.Medium, fontSize = 13.sp, maxLines = 1)
            Text(text = if (item.dangerLevel > 2 && !isChaosMode) "🔒 Locked" else item.subtitle,
                 color = Color.Gray, fontSize = 10.sp, maxLines = 1)
        }
    }
}

@Composable
fun DeviceStatus() {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xFF0D0D18)),
        shape = RoundedCornerShape(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            StatusChip("📱 Device", "Samsung S24")
            StatusChip("🔋 Battery", "87%")
            StatusChip("📶 Signal", "Strong")
            StatusChip("🛡️ Shield", "Active")
        }
    }
}

@Composable
fun StatusChip(label: String, value: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = label, color = Color.Gray, fontSize = 10.sp)
        Text(text = value, color = Color.White, fontWeight = FontWeight.Bold, fontSize = 12.sp)
    }
}

data class MenuItem(
    val title: String,
    val subtitle: String,
    val icon: ImageVector,
    val route: String,
    val color: Color,
    val dangerLevel: Int = 1,
    val requiresChaos: Boolean = false
)

val menuItems = listOf(
    MenuItem("Ransomware", "AES-256 GCM", Icons.Default.Lock, "ransomware", Color.Red, 3, true),
    MenuItem("Spy Engine", "SMS • Contacts • Location", Icons.Default.Visibility, "spy", Color(0xFFFF6B6B), 3, true),
    MenuItem("C2 Control", "SMS • Telegram", Icons.Default.SettingsRemote, "c2", Color(0xFF4ECDC4), 2, false),
    MenuItem("Settings", "Config • Status", Icons.Default.Settings, "settings", Color(0xFF95A5A6), 1, false)
)
