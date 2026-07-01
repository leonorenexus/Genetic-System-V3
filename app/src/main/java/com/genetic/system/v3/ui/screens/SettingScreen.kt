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
import android.os.Build

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(navController: NavController) {
    var telegramBot by remember { mutableStateOf("") }
    var telegramChat by remember { mutableStateOf("") }
    var isAutoStart by remember { mutableStateOf(true) }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("⚙️ Settings", color = Color.White) },
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
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF12121E))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("🤖 Telegram Config", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = telegramBot,
                            onValueChange = { telegramBot = it },
                            label = { Text("Bot Token", color = Color.Gray) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF4ECDC4),
                                unfocusedBorderColor = Color.Gray
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = telegramChat,
                            onValueChange = { telegramChat = it },
                            label = { Text("Chat ID", color = Color.Gray) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF4ECDC4),
                                unfocusedBorderColor = Color.Gray
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { /* Save config */ },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4ECDC4))
                        ) { Text("💾 Save Config") }
                    }
                }
            }
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF12121E))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text("🚀 Auto Start on Boot", color = Color.White, fontSize = 14.sp)
                            Switch(
                                checked = isAutoStart,
                                onCheckedChange = { isAutoStart = it },
                                colors = SwitchDefaults.colors(checkedThumbColor = Color(0xFF4ECDC4))
                            )
                        }
                        Text("Start service automatically when device boots", color = Color.Gray, fontSize = 12.sp)
                    }
                }
            }
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF12121E))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("📱 Device Info", color = Color.White, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text("Model:", color = Color.Gray, fontSize = 12.sp)
                            Text(Build.MODEL, color = Color.White, fontSize = 12.sp)
                        }
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text("Android:", color = Color.Gray, fontSize = 12.sp)
                            Text(Build.VERSION.RELEASE, color = Color.White, fontSize = 12.sp)
                        }
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text("API:", color = Color.Gray, fontSize = 12.sp)
                            Text("${Build.VERSION.SDK_INT}", color = Color.White, fontSize = 12.sp)
                        }
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text("App Version:", color = Color.Gray, fontSize = 12.sp)
                            Text("3.0.0", color = Color(0xFF4ECDC4), fontSize = 12.sp)
                        }
                    }
                }
            }
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1A0A0A))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Button(
                            onClick = { /* Clear all data */ },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Red)
                        ) { Text("🗑️ Clear All Data") }
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { /* Self destruct */ },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF0000))
                        ) { Text("💀 Self Destruct") }
                    }
                }
            }
        }
    }
}
