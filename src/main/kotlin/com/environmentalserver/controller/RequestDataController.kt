package com.environmentalserver.controller




import com.environmentalserver.models.JsonFileManagement
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.File




@RestController
@RequestMapping("/request")
class RequestDataController (private val jsonFileManagement: JsonFileManagement) {
    @GetMapping("/request-all-data")
    fun requestAllData(): List<String> {
        return jsonFileManagement.getAll().map {
            it.readText()
        }
    }



    @GetMapping("/request-latest-data")
    fun requestLatestData(): List<String> {
        return jsonFileManagement.getLatestData().map { it.readText() }
    }




    @GetMapping("/request-latest-from/{id}")
    fun requestLatestFrom(@PathVariable("id") controllerId: String): List<String> {
        return jsonFileManagement.getDataFrom(controllerId).map {
            it.readText()
        }
    }
}