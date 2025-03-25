package com.environmentalserver.models




import com.fasterxml.jackson.databind.ObjectMapper
import kotlinx.coroutines.*
import org.springframework.stereotype.Service
import java.io.File
import java.nio.file.Files
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.concurrent.TimeUnit




@Service
class JsonFileManagement(private val objectMapper: ObjectMapper) {
    private val directory             = File("${System.getProperty("user.dir")}/ReceivedFiles").apply {
        if (!exists()) mkdirs()}
    private var coroutineRunning      = false
    private val sleepingTime:    Long = 28800000 // 8 Stunden warten, bevor erneut geprüft wird



    fun getCoroutineRunning(): Boolean {
        return coroutineRunning
    }




    fun writeToJSONFile(controllerData: ControllerData): Boolean {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH-mm") // Ersetze ":" durch "-" für gültige Dateinamen
        val current = LocalDateTime.now().format(formatter)

        if (!directory.exists()) {
            directory.mkdirs() // Erstellt das Verzeichnis, falls es nicht existiert
        }

        val file = File(directory, "${controllerData.controllerID}_At_${current}.json")

        return try {
            var jsonString = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(controllerData)
            jsonString = jsonString.replace(",", ",\n")// Ersetze jedes Komma mit einem Komma + Zeilenumbruch für bessere Lesbarkeit

            file.writeText(jsonString) // Speichert die JSON-Datei im Zielverzeichnis

            println("Datei gespeichert: ${file.absolutePath}")
            true
        } catch (e: Exception) {
            println("Fehler beim Speichern der Datei: ${e.message}")
            false
        }

    }



    @OptIn(DelicateCoroutinesApi::class)
    fun deleteOldFiles() {
        if (coroutineRunning) {
            println("Löschprozess läuft bereits!")
            return
        }



        coroutineRunning = true
        GlobalScope.launch(Dispatchers.IO) {
            try {
                while (true) {
                    val currentTime = Instant.now()

                    directory.listFiles()?.forEach { file ->
                        val lastModified = Instant.ofEpochMilli(file.lastModified())
                        val hoursDifference = TimeUnit.MILLISECONDS.toHours(currentTime.toEpochMilli() - lastModified.toEpochMilli())

                        if (hoursDifference >= 24) {
                            if (file.delete()) {
                                println("Gelöscht: ${file.name}")
                            } else {
                                println("Fehler beim Löschen: ${file.name}")
                            }
                        }
                    }

                    delay(sleepingTime)
                }
            } catch (e: Exception) {
                println("Fehler beim Löschen alter Dateien: ${e.message}")
            } finally {
                coroutineRunning = false
            }
        }
    }




    fun getLatestData(): List<File> {
        val latestFilesMap = mutableMapOf<String, File>()



        directory.listFiles()?.forEach { file ->
            try {
                val controllerData = objectMapper.readValue(file, ControllerData::class.java)
                val controllerId = controllerData.controllerID // ID aus JSON extrahieren

                latestFilesMap[controllerId]?.let { existingFile ->
                    if (file.lastModified() > existingFile.lastModified()) {
                        latestFilesMap[controllerId] = file
                    }
                } ?: run {
                    latestFilesMap[controllerId] = file
                }

            } catch (e: Exception) {
                println("Fehler beim Lesen der Datei ${file.name}: ${e.message}")
            }
        }



        return latestFilesMap.values.toList()
    }




    fun getAll(): List<Pair<String, ByteArray>> =
        directory.listFiles()?.map { it.name to Files.readAllBytes(it.toPath()) } ?: emptyList()
}
