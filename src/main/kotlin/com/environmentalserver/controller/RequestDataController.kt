package com.environmentalserver.controller




import com.environmentalserver.models.JsonFileManagement
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.io.File




@RestController
class RequestDataController (private val jsonFileManagement: JsonFileManagement) {
    @GetMapping("/request-all-data")
    fun requestAllData(): List<Map<String, Any>> {
        return jsonFileManagement.getAll().map { (fileName, fileContent) ->
            mapOf("fileName" to fileName, "content" to String(fileContent))
        }
    }



    @GetMapping("/request-latest-data")
    fun requestLatestData(): List<String> {
        return jsonFileManagement.getLatestData().map { it.readText() }
    }
}